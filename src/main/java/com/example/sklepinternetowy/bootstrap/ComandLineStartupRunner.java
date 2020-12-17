package com.example.sklepinternetowy.bootstrap;

import com.example.sklepinternetowy.models.Address;
import com.example.sklepinternetowy.models.Category;
import com.example.sklepinternetowy.models.user.Role;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.repositories.AddressRepository;
import com.example.sklepinternetowy.repositories.CategoryRepository;
import com.example.sklepinternetowy.repositories.ProductRepository;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Component
public class ComandLineStartupRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserApplicationRepository userRepo;
    private final AddressRepository addressRepository;

    public ComandLineStartupRunner(RoleRepository roleRepository, CategoryRepository categoryRepository, ProductRepository productRepository, UserApplicationRepository userRepo, AddressRepository addressRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepo = userRepo;
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findFirstByName("User").isEmpty()) {
            Role roleUser = new Role("User",new ArrayList<>());
            roleRepository.save(roleUser);
        }
        if (roleRepository.findFirstByName("Admin").isEmpty()) {
            Role roleAdmin = new Role("Admin",new ArrayList<>());
            roleRepository.save(roleAdmin);
        }
        if (userRepo.findByUsername("robertcj92").isEmpty()) {
            UserApplication user = new UserApplication();
            user.getAddress().add(addressRepository.save(new Address()));
            user.setEmail("cj92robert@gmail.com");
            user.setPassword("$2y$10$.yfa.nMV0MF.w9Pk5IEYeeNgTIjkuP3l9JCbPpidowgoWc.9vWa12");
            user.getRoleList().add(roleRepository.findFirstByName("User").get());
            user.getRoleList().add(roleRepository.findFirstByName("Admin").get());
            user.setUsername("robertcj92");
            user.setActivate(true);
            userRepo.save(user);

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
