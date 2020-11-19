package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.ContactNote;
import com.example.sklepinternetowy.repositories.ContactNoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ContactNoteServiceImpl implements ContactNoteService{

    private final ContactNoteRepository contactNoteRepository;

    public ContactNoteServiceImpl(ContactNoteRepository contactNoteRepository) {
        this.contactNoteRepository = contactNoteRepository;
    }


    @Override
    public Optional<ContactNote> save(ContactNote contactNote) {
        return Optional.of(contactNoteRepository.save(contactNote));
    }

    @Override
    public Optional<ContactNote> delete(Long id) {
        Optional<ContactNote> optionalContactNote = contactNoteRepository.findById(id);
        if(optionalContactNote.isPresent()){
            contactNoteRepository.deleteById(id);
            return optionalContactNote;
        }

        return Optional.empty();
    }

    @Override
    public List<ContactNote> getAll() {
        return contactNoteRepository.findAll();
    }

    @Override
    public Optional<ContactNote> getById(Long id) {
        return contactNoteRepository.findById(id);
    }
}
