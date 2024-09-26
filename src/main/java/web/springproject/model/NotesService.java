package web.springproject.model;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import web.springproject.model.exceptions.InvalidNoteException;
import web.springproject.model.exceptions.UserLoginException;
import web.springproject.model.interfaces.INotesStorage;

@Service
public class NotesService {
    
    @Autowired
    @Qualifier("notesStorage")
    INotesStorage notesStorage;

    public BaseNote CreateNote(final User user) throws UserLoginException {
        if(user == null) throw new UserLoginException();

        String id = UUID.randomUUID().toString();
        BaseNote note = new BaseNote("this is an emtpy note", id);
        notesStorage.AddNote(user, note);
        return note;
    }

    public void DeleteNote(final User user, final String noteid) throws InvalidNoteException, UserLoginException {
        if(user == null) throw new UserLoginException();

        if(!notesStorage.RemoveNote(user, noteid)) {
            throw new InvalidNoteException();
        }
    }

    public void UpdateNote(final User user, final BaseNote note) throws InvalidNoteException, UserLoginException {
        if(user == null) throw new UserLoginException();

        if(!notesStorage.UpdateNote(user, note.uniqueID, note.getText())) {
            throw new InvalidNoteException();
        }
    }

    public BaseNote GetNote(final User user, final String noteid) throws InvalidNoteException, UserLoginException {
        if(user == null) throw new UserLoginException();

        BaseNote note = notesStorage.GetNoteWithID(user, noteid);
        if(note == null)
            throw new InvalidNoteException();

        return note;
    }

    public List<BaseNote> GetAllNotes(final User user) throws UserLoginException {
        if(user == null) throw new UserLoginException();

        return notesStorage.GetAllNotes(user);
    }

}
