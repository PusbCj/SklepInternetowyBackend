package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.ContactNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactNoteRepository extends JpaRepository<ContactNote,Long> {
}
