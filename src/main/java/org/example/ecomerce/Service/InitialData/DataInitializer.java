package org.example.ecomerce.Service.InitialData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecomerce.Entity.Role;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Repository.RolesRepo;
import org.example.ecomerce.Repository.UserRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defauiltRoles=Set.of("ROLE_ADMIN","ROLE_USER");
        createDefauiltRoleIfNotExist(defauiltRoles);
        createDefauiltUserIfNotExist();
        createDefauiltAdminIfNotExist();

    }

    public void createDefauiltUserIfNotExist() {
       Role userRole=rolesRepo.findByName("ROLE_USER").get();
        String defauiltMail = "DefaultUser@gmail.com";
        if (!userRepo.existsByemail(defauiltMail)) {

            User user = new User();
            user.setFirstName("Default User");
            user.setLastName("starter");
            user.setEmail(defauiltMail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepo.save(user);
        }
    }

    public void createDefauiltAdminIfNotExist() {
        Role adminRole=rolesRepo.findByName("ROLE_ADMIN").get();
        String defauiltMail = "DefaultAdmin@gmail.com";
        if (!userRepo.existsByemail(defauiltMail)) {

            User admin = new User();
            admin.setFirstName("Default Admin");
            admin.setLastName("starter");
            admin.setEmail(defauiltMail);
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setRoles(Set.of(adminRole));
            userRepo.save(admin);
        }
    }

    public void createDefauiltRoleIfNotExist(Set<String> roles){
        roles.stream()
                .filter(role-> rolesRepo.findByName(role).isEmpty())
                .map(Role::new).forEach(rolesRepo::save);
    }
}