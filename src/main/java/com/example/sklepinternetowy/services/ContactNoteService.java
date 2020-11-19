package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.ContactNote;

import java.util.List;
import java.util.Optional;

public interface ContactNoteService {

    Optional<ContactNote> save(ContactNote contactNote);
    Optional<ContactNote> delete(Long id);
    List<ContactNote> getAll();
    Optional<ContactNote> getById(Long id);
}
