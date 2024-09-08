package web.springproject.model.storages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import web.springproject.model.BaseNote;
import web.springproject.model.interfaces.INotesStorage;

@Component
@PropertySource("classpath:db.properties")
public class DataBaseNotesStorage implements INotesStorage {

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
        System.out.println("URL: " + url);
        System.out.println("Username: " + usrname);
        System.out.println("Password: " + pass);

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usrname, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BaseNote> GetAllNotes()
    {
        List<BaseNote> notes = new ArrayList<>();

        try {
            String q = "SELECT * FROM notes";
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
    public final BaseNote GetNoteWithID(final String id)
    {
        if(id.isEmpty()) return null;
        
        try {
            String q = "SELECT * FROM notes WHERE id=\'" + id + "\'";
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
    public void UpdateNote(final String id, BaseNote note)
    {
        try {
            String q = "UPDATE notes SET userid='user0',id='" + id +  "',txt='" + note.getText() +  "'"
            + "WHERE id='" + id + "'";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AddNote(final BaseNote note)
    {
        try {
            String q = "INSERT INTO notes VALUES('user0','" + note.getUniqueID() +  "','" + note.getText() +  "')";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void RemoveNote(final String id)
    {
        try {
            String q = "DELETE FROM notes WHERE id='" + id + "'";
            Statement statement = connection.createStatement();
            statement.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
