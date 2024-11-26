package org.example.ecomerce.Security.User;

import lombok.AllArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ShopUserDetailsService  implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user= Optional.ofNullable(userRepo.findByemail(email)).
             orElseThrow(()->new ResourceNotFoundException("User with email "+email+" Not Found"));

        return ShopUserDetails.buildUserDetails(user);
    }
}
