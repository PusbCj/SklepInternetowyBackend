package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.PhotoUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoUrlRepository extends JpaRepository<PhotoUrl,Long> {
}
