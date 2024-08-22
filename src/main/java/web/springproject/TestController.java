package web.springproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {
    
    @GetMapping("test")
    @ResponseBody
    public String test() {
        return "test succeed";
    }
    
    @GetMapping("/hello")
    public String sayHello() {
        return "hello_page";
    }

    @GetMapping("/testparam")
    @ResponseBody
    public String getMethodName(@RequestParam String name) {
        return "hello, " + name;
    }
}
