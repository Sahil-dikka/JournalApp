package net.sd.journalApp.service;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;


    public JournalEntity saveEntry(JournalEntity entry) {
        return journalEntryRepository.save(entry); // expects JournalEntity, not Optional
    }


    public List<JournalEntity> getEntry(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getByIDEntry(String id){
        return  journalEntryRepository.findById(id);
    }

    public JournalEntity deleteEntry(String id){
         journalEntryRepository.deleteById(id);
        return null;
    }

    public void updateEntry(String id){

    }
}
