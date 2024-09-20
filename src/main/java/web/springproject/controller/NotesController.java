package web.springproject.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
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
import web.springproject.model.UserLoginService.LoginResponseStructure;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notes")
@SessionAttributes({"login"})
public class NotesController {

    @Autowired
    private UserLoginService loginService;

    @Autowired
    private NotesService notesService;

    @ModelAttribute
    private void checkAccess(Model model) throws AccessDeniedException {
        LoginResponseStructure res = (LoginResponseStructure)model.getAttribute("login");
        if(res == null) throw new AccessDeniedException("not logged in");
        User user = loginService.CheckToken(res.token);
        if(!res.user.equals(user) || user == null) throw new AccessDeniedException("invalid login data");
    }

    @RequestMapping("")
    private String viewNotes(Model model) {
        final LoginResponseStructure res = (LoginResponseStructure)model.getAttribute("login");
        model.addAttribute("notes", notesService.GetAllNotes(res.user));
        return "viewnotes_page";
    }

    @GetMapping("/create")
    private String createNote(Model model) {

        LoginResponseStructure res = (LoginResponseStructure)model.getAttribute("login");
        BaseNote newNote = notesService.CreateNote(res.user);
        model.addAttribute("noteid", newNote.uniqueID);
        model.addAttribute("action", "edit");
        return "redirect:/notes/edit";
    }    
    
    @GetMapping("/edit")
    private String edinNote(@RequestParam("noteid") String id, @RequestParam("action") String action, 
                            Model model, RedirectAttributes redirectAttributes) {
        final LoginResponseStructure res = (LoginResponseStructure)model.getAttribute("login");


        if(action.equals("delete"))
        {
            notesService.DeleteNote(res.user, id);
            return "redirect:/notes";
        }

        BaseNote note = notesService.GetNote(res.user, id);

        model.addAttribute("notetext", note.getText());
        model.addAttribute("noteid", id);        
        return "edit_page";
    }
    
    @PostMapping("/save")
    private String saveNote(@ModelAttribute("notetext") String text, @RequestParam("noteid") String id, 
                            Model model, RedirectAttributes redirectAttributes) {
        final LoginResponseStructure res = (LoginResponseStructure)model.getAttribute("login");
        notesService.UpdateNote(res.user, new BaseNote(text, id));
        return "redirect:/notes";
    }
}
