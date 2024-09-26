package web.springproject.model.interfaces;

import web.springproject.model.BaseNote;
import web.springproject.model.User;

import java.util.List;

public interface INotesStorage {

    public List<BaseNote> GetAllNotes(final User user);

    public BaseNote GetNoteWithID(final User user, final String uid);

    public boolean UpdateNote(final User user, final String id, String text);

    public boolean AddNote(final User user, final BaseNote note);

    public boolean RemoveNote(final User user, final String id);
}
