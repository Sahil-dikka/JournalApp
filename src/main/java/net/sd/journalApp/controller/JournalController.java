package net.sd.journalApp.controller;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")

public class JournalController {

    private Map<Long, JournalEntity> journalEntires = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAll(){
        return journalEntryService.getEntry();
    }


    @PostMapping
    public boolean createEntry(@RequestBody JournalEntity myEntry){
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("id/{myid}")
    public Optional<JournalEntity> GetById(@PathVariable String myid){
        return Optional.ofNullable(journalEntryService.getByIDEntry(myid).orElse(null));
    }

    @DeleteMapping("id/{myid}")
    public JournalEntity DeleteById(@PathVariable String myid){
        return journalEntryService.deleteEntry(myid);
    }


    @PutMapping("id/{myid}")
    public Optional<JournalEntity> updateById(@PathVariable String myid, @RequestBody JournalEntity newEntry){

        Optional<JournalEntity> optionalOld = journalEntryService.getByIDEntry(myid);

        if (optionalOld.isPresent()) {
            JournalEntity oldEntry = optionalOld.get();
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            // Set other fields if needed
            return Optional.ofNullable(journalEntryService.saveEntry(oldEntry)); // ✅ pass actual entity
        } else {
            throw new RuntimeException("Entry not found for ID: " + myid);
        }
    }
}
