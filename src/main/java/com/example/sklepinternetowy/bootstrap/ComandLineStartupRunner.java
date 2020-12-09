package com.example.sklepinternetowy.bootstrap;

import com.example.sklepinternetowy.models.Category;
import com.example.sklepinternetowy.models.user.Role;
import com.example.sklepinternetowy.repositories.CategoryRepository;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Component
public class ComandLineStartupRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    public ComandLineStartupRunner(RoleRepository roleRepository, CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.findFirstByName("User").isPresent()) {
            Role rolaUser = new Role("User",new ArrayList<>());
            roleRepository.save(rolaUser);
        }
        if(categoryRepository.count() <4L){
            Category category1= new Category();
            category1.setName("Gry planszowe");
            Category category2= new Category();
            category2.setName("Klocki");
            Category category3= new Category();
            category3.setName("Zabawki edukacyjne");
            Category category4= new Category();
            category4.setName("Maskotki i figurki");
            Category category5= new Category();
            category5.setName("Lalki i akcesoria");
            Category category6= new Category();
            category6.setName("Samochody i pojazdy");
            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
            categoryRepository.save(category4);
            categoryRepository.save(category5);
            categoryRepository.save(category6);

        }
        Files.createDirectories(Paths.get("./photo"));

    }
}
