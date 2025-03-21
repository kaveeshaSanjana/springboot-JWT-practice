package edu.icet.controller;

import edu.icet.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    final JWTService service;

    @GetMapping("/login")
    public String getAll(){
        return service.getToken();
    }

    @GetMapping("/user")
    public String getUser(@RequestParam String token){
        return service.getUsername(token);
    }

}
