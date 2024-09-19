package web.springproject.model.interfaces;

import web.springproject.model.BaseNote;
import web.springproject.model.User;

import java.util.List;

public interface INotesStorage
{
    public List<BaseNote> GetAllNotes(final User user);
    public BaseNote GetNoteWithID(final User user, final String uid);
    public void UpdateNote(final User user, final String id, String text);
    public void AddNote(final User user, final BaseNote note);
    public void RemoveNote(final User user, final String id);
}
