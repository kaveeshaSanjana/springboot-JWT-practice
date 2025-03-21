package edu.icet.service;

import edu.icet.entity.UserEntity;
import edu.icet.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user){
        return userDao.save(new UserEntity(
                user.getUserId(),
                user.getEmail(),
                passwordEncoder.encode(
                        user.getPassword()))
        );
    }

    public List<UserEntity> getAll(){
        return userDao.findAll();
    }
}
