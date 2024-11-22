package ma.mundia.patients_mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";  // Affiche la vue notAuthorized.html
    }


    @GetMapping("/login")
    public String login() {
        return "login.html";  // Affiche la vue login.html
    }
}
