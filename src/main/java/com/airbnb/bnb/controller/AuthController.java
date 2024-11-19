package com.airbnb.bnb.controller;

import com.airbnb.bnb.Exception.UserExists;
import com.airbnb.bnb.entity.AppUser;
import com.airbnb.bnb.payload.LoginDto;
import com.airbnb.bnb.repository.AppUserRepository;
import com.airbnb.bnb.service.AppUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private AppUserRepository appUserRepository;

    private AppUserServiceImpl appUserService;



    public AuthController(AppUserRepository appUserRepository, AppUserServiceImpl appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }


    @PostMapping
    public ResponseEntity<AppUser> createUser(
            @RequestBody AppUser user

    ){

        Optional<AppUser>opEmail =appUserRepository.findByEmail(user.getEmail());

        if(opEmail .isPresent()){

            throw new UserExists("Email Id Exists");
        }



        Optional<AppUser>opUsername =appUserRepository.findByUsername(user.getUsername());

        if(opUsername .isPresent()){

            throw new UserExists("username  Exists");
        }

        AppUser savedUser = appUserService.createUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @PostMapping("/login")
public ResponseEntity<String> signIn(
        @RequestBody LoginDto loginDto
){

        String token= appUserService.verifyLogin(loginDto);

        if(token!=null){
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else{

            return new ResponseEntity<>("Invalid username /password ",HttpStatus.UNAUTHORIZED);


        }
}



}
