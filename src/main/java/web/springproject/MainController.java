package web.springproject;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import javax.annotation.PostConstruct;

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
import web.springproject.model.UserDAO;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notes")
@SessionAttributes("user")
public class MainController {

    @ModelAttribute("user")
    private UserDAO getUser() {
        return new UserDAO();
    } 

    @ModelAttribute
    public void checkAccess(Model model) throws AccessDeniedException {
        // TODO: check if access granted
    }

    @RequestMapping("")
    public String viewNotes() {
        return "viewnotes_page";
    }

    @GetMapping("/create")
    public String createNote(Model model) {
        UserDAO user = (UserDAO)model.getAttribute("user");
        String id = UUID.randomUUID().toString();
        BaseNote note = new BaseNote("this is an emtpy note", id);
        user.addNote(note);
        model.addAttribute("noteid", id);
        model.addAttribute("action", "edit");
        return "redirect:/notes/edit";
    }
    
    
    @GetMapping("/edit")
    public String edinNote(@RequestParam("noteid") String id, @RequestParam("action") String action, 
                            Model model, RedirectAttributes redirectAttributes) {
        UserDAO user = (UserDAO)model.getAttribute("user");
        BaseNote note = user.getNoteWithID(id);

        if(action.equals("delete"))
        {
            user.removeNote(id);
            return "redirect:/notes";
        }

        model.addAttribute("note", note);
        model.addAttribute("noteid", id);        
        return "edit_page";
    }
    
    @PostMapping("/save")
    public String saveNote(@ModelAttribute("note") BaseNote note, @RequestParam("noteid") String id, 
                            Model model, RedirectAttributes redirectAttributes) {
        UserDAO user = (UserDAO)model.getAttribute("user");
        user.updateNote(id, note);
        return "redirect:/notes";
    }
}
