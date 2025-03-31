package pl.piomin.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @Value("${message:Hello!}")
    String message;

    @GetMapping
    public String message() {
        return message;
    }

}
