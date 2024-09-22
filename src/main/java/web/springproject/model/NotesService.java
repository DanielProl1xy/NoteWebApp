package web.springproject.model;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import web.springproject.model.interfaces.INotesStorage;

@Component
public class NotesService {
    
    @Autowired
    @Qualifier("notesStorage")
    INotesStorage notesStorage;

    public BaseNote CreateNote(@NonNull final User user) {
        String id = UUID.randomUUID().toString();
        BaseNote note = new BaseNote("this is an emtpy note", id);
        notesStorage.AddNote(user, note);
        return note;
    }

    public void DeleteNote(@NonNull final User user, final @NonNull String noteid) {
        notesStorage.RemoveNote(user, noteid);
    }

    public void UpdateNote(@NonNull final User user, final @NonNull BaseNote note) {
        notesStorage.UpdateNote(user, note.uniqueID, note.getText());
    }

    public BaseNote GetNote(@NonNull final User user, final @NonNull String noteid) {
        return notesStorage.GetNoteWithID(user, noteid);
    }

    public List<BaseNote> GetAllNotes(@NonNull final User user) {
        return notesStorage.GetAllNotes(user);
    }

}
