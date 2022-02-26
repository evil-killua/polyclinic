package by.grsu.backend.service;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findUserById(Long id);
    User findUserByUserName(String userName);
    void  /*Map<String,Boolean>*/ deleteUser(Long id);
    User saveUser(User user);
    UserRequest updateUser(User user, UserRequest request);
}
