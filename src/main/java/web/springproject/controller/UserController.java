package web.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import web.springproject.model.UserLoginService;
import web.springproject.model.UserLoginService.LoginResponseStructure;
import web.springproject.model.UserLoginService.LoginResult;

@Controller
@RequestMapping("/")
@SessionAttributes({"loginService"})
public class UserController {

    @Autowired
    private UserLoginService loginService;

    @GetMapping("login")
    private String loginPage(Model model) {
        return "login_page";
    }

    @PostMapping("login")
    private String loginAction(@RequestParam("login") String login,
                                @RequestParam("password") String password,
                                RedirectAttributes redir) {
        final LoginResponseStructure res = loginService.LoginUser(login, password);
        if(res.result ==  LoginResult.LOGIN_SUCCESS)
        {
            redir.addFlashAttribute("login", res);
            return "redirect:/notes";
        }
        else
        {
            return "redirect:/login";
        }
    }
    

    @GetMapping("register")
    private String registerPage(Model model) {
        return "register_page";
    }

    @PostMapping("register")
    private String registerAction(@RequestParam("login") String login,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                RedirectAttributes redir) {
        final LoginResponseStructure res = loginService.RegisterUser(login, name, password);
        if(res.result == LoginResult.REGISTER_SUCCESS)
        {
            redir.addFlashAttribute("login", res);
            return "redirect:/notes";
        }
        else
        {
            return "redirect:/register";
        }
    }

    @PostMapping("logout")
    public String logoutAction(@ModelAttribute LoginResponseStructure login, RedirectAttributes redir) {
        loginService.InvalidateToken(login.token);
        return "redirect:/login";
    }
    
    
}