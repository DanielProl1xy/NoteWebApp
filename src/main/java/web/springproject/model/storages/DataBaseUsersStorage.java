package web.springproject.model.storages;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import web.springproject.model.interfaces.IUsersStorage;
import web.springproject.model.User;

@Component
@PropertySource("classpath:db.properties")
public class DataBaseUsersStorage implements IUsersStorage {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")    
    private String usrname;
    @Value("${db.password}")    
    private String pass;

    private static Connection connection;

    @PostConstruct
    private void init()
    {
        System.out.println("DataBase URL: " + url);
        System.out.println("DataBase Username: " + usrname);
        System.out.println("DataBase Password: " + pass);

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usrname, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean IsLoginTaken(String login) {
        try {
            String q = "SELECT * FROM users WHERE userLogin=\'" + login + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);
            
            return res.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean RegisterUser(String login, String name, String password) {
        try {
            String q = "INSERT INTO users VALUES(\'" + login + "\',\'" + name +  "\',\'" + password.hashCode() +  "\')";
            Statement statement = connection.createStatement();
            return statement.execute(q);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User LoginUser(String login, String password) {
        try {
            String q = "SELECT * FROM users WHERE userLogin=\'" + login + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);
            
            if(res.next())
            {
                if(res.getInt("userPasswordHash") == password.hashCode())
                {
                    User user = new User(res.getString(1), res.getString(2));
                    return user;
                }
                return null;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
