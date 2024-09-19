package web.springproject.controller;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import web.springproject.model.BaseNote;
import web.springproject.model.User;
import web.springproject.model.interfaces.INotesStorage;
import web.springproject.model.storages.DataBaseNotesStorage;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notes")
@SessionAttributes({"user", "notesStorage"})
public class NotesController {


    @ModelAttribute("notesStorage")
    private INotesStorage getStorage()
    {
        return new DataBaseNotesStorage();
    }

    @ModelAttribute
    public void checkAccess(Model model) throws AccessDeniedException {
        User user = (User)model.getAttribute("user");
        if(user == null) throw new AccessDeniedException("Not logged in!");
    }

    @RequestMapping("")
    public String viewNotes(Model model) {
        INotesStorage storage = (INotesStorage)model.getAttribute("notesStorage");
        User user = (User)model.getAttribute("user");
        model.addAttribute("notes", storage.GetAllNotes(user));
        return "viewnotes_page";
    }

    @GetMapping("/create")
    public String createNote(Model model) {
        String id = UUID.randomUUID().toString();
        BaseNote note = new BaseNote("this is an emtpy note", id);
        INotesStorage storage = (INotesStorage)model.getAttribute("notesStorage");
        User user = (User)model.getAttribute("user");
        storage.AddNote(user, note);
        model.addAttribute("noteid", id);
        model.addAttribute("action", "edit");
        return "redirect:/notes/edit";
    }    
    
    @GetMapping("/edit")
    public String edinNote(@RequestParam("noteid") String id, @RequestParam("action") String action, 
                            Model model, RedirectAttributes redirectAttributes) {
        INotesStorage storage = (INotesStorage)model.getAttribute("notesStorage");
        User user = (User)model.getAttribute("user");
        BaseNote note = storage.GetNoteWithID(user, id);

        if(action.equals("delete"))
        {
            storage.RemoveNote(user, id);
            return "redirect:/notes";
        }

        model.addAttribute("notetext", note.getText());
        model.addAttribute("noteid", id);        
        return "edit_page";
    }
    
    @PostMapping("/save")
    public String saveNote(@ModelAttribute("notetext") String text, @RequestParam("noteid") String id, 
                            Model model, RedirectAttributes redirectAttributes) {
        INotesStorage storage = (INotesStorage)model.getAttribute("notesStorage");
        User user = (User)model.getAttribute("user");
        storage.UpdateNote(user, id, text);
        return "redirect:/notes";
    }
}
