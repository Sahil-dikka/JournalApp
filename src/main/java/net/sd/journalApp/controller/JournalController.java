package net.sd.journalApp.controller;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.service.JournalEntryService;
import net.sd.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")

public class JournalController {



    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
         User userInDB = userService.findByUserName(userName);
         List<JournalEntity> all = userInDB.getJournalEntries();

         if(all!=null && !all.isEmpty()){
             return new ResponseEntity<>(all,HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry,@PathVariable String userName){
        try{

            journalEntryService.saveEntry(myEntry,userName);

            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntity> GetById(@PathVariable String myid){
        Optional<JournalEntity> JE =  journalEntryService.getByIDEntry(myid);

        if(JE.isPresent()){
            return new ResponseEntity<JournalEntity>(JE.get(), HttpStatus.OK);
        }

        return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> DeleteById(@PathVariable String myid,@PathVariable String userName){
         journalEntryService.deleteEntry(myid,userName);
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("id/{userName}/{myid}")
    public ResponseEntity<?> updateById(@PathVariable String myid, @RequestBody JournalEntity newEntry,@PathVariable String userName){

        Optional<JournalEntity> optionalOld = journalEntryService.getByIDEntry(myid);

        if (optionalOld.isPresent()) {
            JournalEntity oldEntry = optionalOld.get();
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            // Set other fields if needed
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(HttpStatus.OK);// ✅ pass actual entity
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//     @PutMapping("id/{userName}/{myid}")
//    public void updateJournalEntryByName(@RequestBody JournalEntity newEntry,@PathVariable String myid,@PathVariable String userName){
//
//        User userinId = userService.findByUserName(userName);
//        userinId.getJournalEntries().
//     }
}
