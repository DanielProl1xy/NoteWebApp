package web.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import web.springproject.model.User;
import web.springproject.model.interfaces.IUsersStorage;
import web.springproject.model.storages.DataBaseUsersStorage;


@Controller
@RequestMapping("/")
@SessionAttributes({"usersStorage"})
public class UserController {

    @ModelAttribute("usersStorage")
    private IUsersStorage getUsersStorage()
    {
        return new DataBaseUsersStorage();
    }

    @GetMapping("login")
    public String LoginPage(Model model) {
        return "login_page";
    }

    @PostMapping("login")
    public String LoginAction(@RequestParam("login") String login,
                                @RequestParam("password") String password,
                                Model model,
                                RedirectAttributes redir) {

        IUsersStorage users = (IUsersStorage)model.getAttribute("usersStorage");
        User user = users.LoginUser(login, password);
        if(user !=  null)
        {
            redir.addFlashAttribute("user", user);
            return "redirect:/notes";
        }
        else
        {
            return "redirect:/login";
        }
    }
    

    @GetMapping("register")
    public String RegisterPage(Model model) {
        return "register_page";
    }

    @PostMapping("register")
    public String RegisterAction(@RequestParam("login") String login,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                Model model,
                                RedirectAttributes redir) {

        IUsersStorage users = (IUsersStorage)model.getAttribute("usersStorage");

        if(users.IsLoginTaken(login))
        {
            return "redirect:/register";
        }
        
        users.RegisterUser(login, name, password);

        redir.addFlashAttribute("user", new User(login, password));
        return "redirect:/notes";
    }
    
}