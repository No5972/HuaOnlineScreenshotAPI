package tk.no5972.huascreenshot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexAction {
    @RequestMapping("/")
    public String index() {
        return "screenshot";
    }
}
