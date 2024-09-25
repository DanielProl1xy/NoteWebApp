package web.springproject.model.storages.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import web.springproject.model.User;
import web.springproject.model.interfaces.ITokensStorage;
import web.springproject.model.interfaces.IUsersStorage;

@Repository("SQLTokensStorage")
@PropertySource("classpath:db.properties")
public class SQLTokensStorage implements ITokensStorage {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")    
    private String usrname;
    @Value("${db.password}")    
    private String pass;

    private static Connection connection;

    @Autowired
    @Qualifier("SQLUsersStorage")
    private IUsersStorage usersStorage;

    @PostConstruct
    private void init() {
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
    public void StoreToken(User user, String token) {

        try {
            // TODO: check for repeating
            String q = "INSERT INTO tokens VALUES(\'" + user.login + "\',\'" + token + "\')";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteToken(String token) {
        try {
            String q = "DELETE FROM tokens WHERE token=\'" + token + "\'";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean ContainsToken(User user, String token) {
        
        try {
            String q = "SELECT * FROM tokens WHERE userLogin=\'" + user.login + "\' AND token=\'" + token + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);            
            return res.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User GetUserByToken(String token) {
        try {
            String q = "SELECT * FROM tokens WHERE token=\'" + token + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);         
            if(res.next())
            {
                return usersStorage.GetUserByLogin(res.getString("userLogin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> GetTokensForUser(User user) {
        List<String> tokens = new ArrayList<>();
        try {
            String q = "SELECT token FROM tokens WHERE userLogin=\'" + user.login + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);         
            while(res.next())
            {
                tokens.add(res.getString("token"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }    
}
