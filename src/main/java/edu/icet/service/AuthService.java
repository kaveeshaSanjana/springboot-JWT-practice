package edu.icet.service;

import edu.icet.dto.UserRequestDto;
import edu.icet.dto.UserResponseDto;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserEntity save(UserRequestDto user){
        return userDao.save(new UserEntity(
                user.getId(),
                user.getEmail(),
                passwordEncoder.encode(
                        user.getPassword()))
        );
    }

    public List<UserEntity> getAll(){
        return userDao.findAll();
    }

    public UserResponseDto create(UserRequestDto userRequestDto){
        if(userDao.findUserByEmail(userRequestDto.getEmail())!=null){
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(),userRequestDto.getPassword()));
            Map<String, Object> claims = new HashMap<>();
            claims.put("role","user");
            claims.put("ip","108.663.25.8");

            String token = jwtService.getToken(userRequestDto.getEmail(), claims);
            return new UserResponseDto(token,null,"Already Exists");

        }
        return new UserResponseDto(null,null,"Already nit Exists");
    }
}
