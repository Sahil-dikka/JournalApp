package net.sd.journalApp.controller;

import net.sd.journalApp.entity.User;
import net.sd.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public")
@RestController
public class PublicController {


    @Autowired
    private UserService userService;



    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }


    @PostMapping("/create-user")
    public boolean createUser(@RequestBody User user){
        userService.saveEntry(user);
        return true;
    }
}
