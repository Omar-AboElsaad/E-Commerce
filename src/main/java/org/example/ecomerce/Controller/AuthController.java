package org.example.ecomerce.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.UserDto;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Requests.LoginRequest;
import org.example.ecomerce.Requests.User.createUserRequest;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Response.JwtResponse;
import org.example.ecomerce.Security.JWT.JwtUtiles;
import org.example.ecomerce.Security.User.ShopUserDetails;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtUtiles jwtUtiles;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse>Login(@RequestBody LoginRequest request){
        try {
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                            (request.getEmail(),request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtiles.generateTokenForUser(authentication);
            ShopUserDetails userDetails=(ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),jwt);

            return ResponseEntity.ok().body(new ApiResponse("Login Successfully",jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> Register(@RequestBody createUserRequest request) {
        try {
            User user=userService.createUser(request,"ROLE_USER");
            //Auto Generate Cart for New user
            user.setCart(cartService.createNewCart(user.getId()));
            //convert user to user DTO
            UserDto userDto=userService.ConvertUserToUserDto(user);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Registered successfully!",userDto));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }

    }
}
