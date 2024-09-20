package web.springproject.model.interfaces;

import web.springproject.model.BaseNote;
import web.springproject.model.User;

import java.util.List;

import org.springframework.lang.Nullable;

public interface INotesStorage {

    @Nullable
    public List<BaseNote> GetAllNotes(final User user);

    @Nullable
    public BaseNote GetNoteWithID(final User user, final String uid);

    public void UpdateNote(final User user, final String id, String text);
    public void AddNote(final User user, final BaseNote note);
    public void RemoveNote(final User user, final String id);
}
