package net.sd.journalApp.controller;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.service.JournalEntryService;
import net.sd.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {

   @Autowired
   private UserService userService;


   @GetMapping
   public List<User> getAll(){
       return userService.getEntry();
   }

   @PostMapping
    public boolean createUser(@RequestBody  User user){
       userService.saveEntry(user);
       return true;
   }


   @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){

       User userPresent = userService.findByUserName(userName);

       if(userPresent!=null){
           userPresent.setUserName(user.getUserName());
           userPresent.setPassword(user.getPassword());

           userService.saveEntry(userPresent);
       }

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }


}
