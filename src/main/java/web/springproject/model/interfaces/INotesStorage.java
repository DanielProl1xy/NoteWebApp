package web.springproject.model.interfaces;

import web.springproject.model.BaseNote;
import java.util.List;


public interface INotesStorage
{
    public List<BaseNote> GetAllNotes();
    public BaseNote GetNoteWithID(final String uid);
    public void UpdateNote(final String id, BaseNote note);
    public void AddNote(final BaseNote note);
    public void RemoveNote(final String id);
}
