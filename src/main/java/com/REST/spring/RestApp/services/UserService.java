package com.REST.spring.RestApp.services;

import com.REST.spring.RestApp.models.User;
import com.REST.spring.RestApp.repository.UserRepository;
import com.REST.spring.RestApp.util.UserNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id).orElseThrow(UserNotfoundException::new);

    }

    @Transactional
    public void save(User user) {
       enrichUser(user);
        userRepository.save(user);
    }

    private void enrichUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        user.setCreatedWho("ADMIN");    //здесь можно использовать спринг секьюрити и брать данные аутификации кто работает с системой
    }  // в контроллере минимальное количетсво логики
}