package edu.icet.controller;

import edu.icet.dto.UserRequestDto;
import edu.icet.entity.UserEntity;
import edu.icet.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/save")
    public UserEntity save(@RequestBody UserRequestDto user ){
        return authService.save(user);
    }

    @GetMapping
    public List<UserEntity> getAll(){
        return authService.getAll();
    }
}
