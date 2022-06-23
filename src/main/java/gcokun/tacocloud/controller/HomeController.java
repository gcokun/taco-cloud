package gcokun.tacocloud.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //For component scanning
public class HomeController {

    //Request for root path
    @GetMapping("/")
    public String home() {
        //Returns the view name
        return "home";
    }
}
