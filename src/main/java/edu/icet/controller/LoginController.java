package edu.icet.controller;

import edu.icet.dto.UserRequestDto;
import edu.icet.dto.UserResponseDto;
import edu.icet.entity.UserEntity;
import edu.icet.service.AuthService;
import edu.icet.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    final JWTService service;
    final AuthService userService;

    @GetMapping("/test-token")
    public String getAll(){
        return null;
    }

    @GetMapping("/user")
    public String getUser(@RequestParam String token){
        return service.getUsername(token);
    }

    @GetMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto){
        if(userRequestDto==null||userRequestDto.getEmail()==null||userRequestDto.getPassword()==null){
            return new UserResponseDto(null,null,"Fill Data is Mandatory");
        }

        return  userService.create(userRequestDto);

    }

}
