package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.ContactNote;
import com.example.sklepinternetowy.services.ContactNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/contact/")
public class ContactNoteController {
    private final ContactNoteService contactNoteService;

    public ContactNoteController(ContactNoteService contactNoteService) {
        this.contactNoteService = contactNoteService;
    }

    @GetMapping
    public List<ContactNote> getAllContactNotes(){
        return contactNoteService.getAll();
    }
    @GetMapping("{id}")
    public ResponseEntity<ContactNote> getById(@PathVariable Long id){
        Optional<ContactNote> optionalContactNote = contactNoteService.getById(id);
        if(optionalContactNote.isPresent())
            return new ResponseEntity<>(optionalContactNote.get(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<ContactNote> save(@RequestBody ContactNote contactNote){
        Optional<ContactNote> optionalContactNote = contactNoteService.save(contactNote);
        if(optionalContactNote.isPresent())
            return new ResponseEntity<>(optionalContactNote.get(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ContactNote> delete(@PathVariable Long id){
        Optional<ContactNote> optionalContactNote = contactNoteService.delete(id);
        if(optionalContactNote.isPresent())
            return new ResponseEntity<>(optionalContactNote.get(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
