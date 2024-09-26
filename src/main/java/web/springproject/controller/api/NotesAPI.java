package web.springproject.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import web.springproject.model.BaseNote;
import web.springproject.model.NotesService;
import web.springproject.model.User;
import web.springproject.model.UserLoginService;
import web.springproject.model.exceptions.InvalidNoteException;
import web.springproject.model.exceptions.UserLoginException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/notes-api")
public class NotesAPI {
    
    @Autowired
    private UserLoginService loginService;

    @Autowired
    private NotesService notesService;

    @PostMapping("/{noteid}/get")
    public String getNoteText(@PathVariable("noteid") String id, @RequestBody String token)
    throws InvalidNoteException, UserLoginException {
        
        final User user = loginService.CheckToken(token);
        BaseNote note = notesService.GetNote(user, id);
        return note.toString();
    }

    @PostMapping("/{noteid}/delete")
    public String deleteNote(@PathVariable("noteid") String id, @RequestBody String token)
    throws InvalidNoteException, UserLoginException {
                                
        final User user = loginService.CheckToken(token);

        notesService.DeleteNote(user, id);
        return "{ \"result\" : \"success\"}";
    }
    
    @PostMapping("/{noteid}/save")
    public String saveNote(@PathVariable("noteid") String id,
                        @RequestBody String body) 
                        throws InvalidNoteException, UserLoginException {

        // final User user = loginService.CheckToken(token);

        // notesService.UpdateNote(user, new BaseNote(text, id));
        return "{ \"result\" : \"success\"}";
    }

    @PostMapping("/create")
    public String createNote(@RequestBody String token) 
    throws UserLoginException {

        final User user = loginService.CheckToken(token);
        final BaseNote note = notesService.CreateNote(user);
        return "{\"noteID\" : \"" + note.uniqueID + "\" }";
    }
}
