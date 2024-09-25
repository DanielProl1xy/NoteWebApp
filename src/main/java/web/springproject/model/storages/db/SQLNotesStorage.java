package web.springproject.model.storages.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import web.springproject.model.BaseNote;
import web.springproject.model.User;
import web.springproject.model.interfaces.INotesStorage;

@Repository("SQLNotesStorage")
@PropertySource("classpath:db.properties")
public class SQLNotesStorage implements INotesStorage {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")    
    private String usrname;
    @Value("${db.password}")    
    private String pass;

    private static Connection connection;

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
    public List<BaseNote> GetAllNotes(final User user) {

        if(user == null) return null;
        
        List<BaseNote> notes = new ArrayList<>();


        try {
            String q = "SELECT * FROM notes WHERE userid=\'" + user.login + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);
            while(res.next())
            {
                BaseNote note = new BaseNote(res.getString("txt"), res.getString("id"));
                notes.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public final BaseNote GetNoteWithID(final User user, final String id) {

        if(id.isEmpty()) return null;
        if(user == null) return null;
        
        try {
            String q = "SELECT * FROM notes WHERE id=\'" + id + "\' AND userid=\'" + user.login + "\'";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(q);
            if(res.next())
            {
                BaseNote note = new BaseNote(res.getString("txt"), id);
                return note;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return null;
    }

    @Override
    public void UpdateNote(final User user, final String id, String text) {

        if(user == null) return;

        try {
            String q = "UPDATE notes SET txt=\'" + text +  "\'"
            + "WHERE id=\'" + id + "\' AND userid=\'" + user.login + "\'";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AddNote(final User user, final BaseNote note) {
        
        if(user == null) return;

        try {
            String q = "INSERT INTO notes VALUES(\'" + user.login + "\',\'" + note.uniqueID +  "\',\'" + note.getText() +  "\')";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void RemoveNote(final User user, final String id) {

        if(user == null) return;

        try {
            String q = "DELETE FROM notes WHERE id=\'" + id + "\' AND userid=\'" + user.login + "\'";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
