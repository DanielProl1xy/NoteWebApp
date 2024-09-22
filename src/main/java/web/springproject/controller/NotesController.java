package web.springproject.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import web.springproject.model.BaseNote;
import web.springproject.model.NotesService;
import web.springproject.model.User;
import web.springproject.model.UserLoginService;

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
    private void checkAccess(Model model) throws AccessDeniedException {
        final String token = (String) model.getAttribute("token");
        if(token == null) throw new AccessDeniedException("Token is null");
        if(token.isEmpty()) throw new AccessDeniedException("Token is emtpy");
        final User user = loginService.CheckToken(token);
        if(user == null) throw new AccessDeniedException("Ivnalid token");

        model.addAttribute("user", user);

    }

    @RequestMapping("")
    private String viewNotes(Model model) {
        final User user = (User) model.getAttribute("user");
        model.addAttribute("notes", notesService.GetAllNotes(user));
        return "viewnotes_page";
    }

    @GetMapping("/create")
    private String createNote(Model model) {

        final User user = (User) model.getAttribute("user");
        BaseNote newNote = notesService.CreateNote(user);
        model.addAttribute("noteid", newNote.uniqueID);
        model.addAttribute("action", "edit");
        return "redirect:/notes/edit";
    }    
    
    @GetMapping("/edit")
    private String edinNote(@RequestParam("noteid") String id, @RequestParam("action") String action, 
                            Model model, RedirectAttributes redirectAttributes) {
        final User user = (User) model.getAttribute("user");


        if(action.equals("delete")) {
            notesService.DeleteNote(user, id);
            return "redirect:/notes";
        }

        BaseNote note = notesService.GetNote(user, id);

        model.addAttribute("notetext", note.getText());
        model.addAttribute("noteid", id);        
        return "edit_page";
    }
    
    @PostMapping("/save")
    private String saveNote(@ModelAttribute("notetext") String text, @RequestParam("noteid") String id, 
                            Model model, RedirectAttributes redirectAttributes) {
        final User user = (User) model.getAttribute("user");
        notesService.UpdateNote(user, new BaseNote(text, id));
        return "redirect:/notes";
    }
}
