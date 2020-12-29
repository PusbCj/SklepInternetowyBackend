package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.ContactNote;
import com.example.sklepinternetowy.services.ContactNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("{id}")
    public ResponseEntity<ContactNote> getById(@PathVariable Long id){
        Optional<ContactNote> optionalContactNote = contactNoteService.getById(id);
        return optionalContactNote.map(contactNote -> new ResponseEntity<>(contactNote, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<ContactNote> save(@RequestBody ContactNote contactNote){
        Optional<ContactNote> optionalContactNote = contactNoteService.save(contactNote);
        return optionalContactNote.map(note -> new ResponseEntity<>(note, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("{id}")
    public ResponseEntity<ContactNote> delete(@PathVariable Long id){
        Optional<ContactNote> optionalContactNote = contactNoteService.delete(id);
        return optionalContactNote.map(contactNote -> new ResponseEntity<>(contactNote, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
