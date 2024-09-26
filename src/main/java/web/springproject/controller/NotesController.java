package web.springproject.controller;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import web.springproject.model.BaseNote;
import web.springproject.model.NotesService;
import web.springproject.model.User;
import web.springproject.model.UserLoginService;
import web.springproject.model.exceptions.InvalidNoteException;
import web.springproject.model.exceptions.UserLoginException;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notes")
@SessionAttributes({"token"})
public class NotesController {

    @Autowired
    @Qualifier("userLoginServiceBean")
    private UserLoginService loginService;

    @Autowired
    private NotesService notesService;

    @ModelAttribute
    public void checkAccess(Model model) throws LoginException {
        final String token = (String) model.getAttribute("token");
        if(token == null) throw new LoginException("Token is null");
        if(token.isEmpty()) throw new LoginException("Token is emtpy");
        final User user = loginService.CheckToken(token);
        if(user == null) throw new LoginException("Ivnalid token");

        model.addAttribute("user", user);

    }

    @RequestMapping("")
    public String viewNotes(Model model) throws UserLoginException {
        final User user = (User) model.getAttribute("user");
        model.addAttribute("notes", notesService.GetAllNotes(user));
        return "viewnotes_page";
    }

    @GetMapping("/create")
    public String createNote(Model model) throws UserLoginException {

        final User user = (User) model.getAttribute("user");
        BaseNote newNote = notesService.CreateNote(user);
        model.addAttribute("noteid", newNote.uniqueID);
        model.addAttribute("action", "edit");
        return "redirect:/notes/edit";
    }    
    
    @GetMapping("/{noteid}/edit")
    public String edinNote(@PathVariable("noteid") String id,
                            Model model, RedirectAttributes redirectAttributes) throws InvalidNoteException, UserLoginException {
        final User user = (User) model.getAttribute("user");

        BaseNote note = notesService.GetNote(user, id);

        model.addAttribute("notetext", note.getText());
        model.addAttribute("noteid", id);        
        return "edit_page";
    }

    @GetMapping("/{noteid}/delete")
    public String deleteNote(@PathVariable("noteid") String id,
                            Model model,
                            RedirectAttributes redirectAttributes) throws InvalidNoteException, UserLoginException {
                                
        final User user = (User) model.getAttribute("user");

        notesService.DeleteNote(user, id);
        return "redirect:/notes";
    }
    
    @PostMapping("/save")
    public String saveNote(@ModelAttribute("notetext") String text, @RequestParam("noteid") String id, 
                            Model model, RedirectAttributes redirectAttributes) throws InvalidNoteException, UserLoginException {
        final User user = (User) model.getAttribute("user");
        notesService.UpdateNote(user, new BaseNote(text, id));
        return "redirect:/notes";
    }
}
