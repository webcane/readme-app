package cane.brothers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by cane
 */
@SpringBootApplication
//@Controller
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @RequestMapping("/")
//    public String home() {
//        return "redirect:/swagger-ui.html";
//    }
}
