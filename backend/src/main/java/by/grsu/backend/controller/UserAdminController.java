package by.grsu.backend.controller;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.User;
import by.grsu.backend.service.UserRoleService;
import by.grsu.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/users")
public class UserAdminController {

//    private Logger logger = LoggerFactory.getLogger(UserAdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<UserRequest> getAllUsers(){
        log.info("get all users request start");
//        logger.info("get all users request start");
        List<User> users = userService.findAll();
        List<UserRequest> requests = new ArrayList<>();

        for (User user :users) {
            UserRequest request = getUserDTO(user);

            requests.add(request);
        }
        log.info("get all users request finish");
//        logger.info("get all users request finish");
        return requests;
    }



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable Long id){

        log.info("get user by id request start");
//        logger.info("get user by id request start");
        User user = userService.findUserById(id);

        UserRequest request = getUserDTO(user);

        log.info("get user by id request finish");
//        logger.info("get user by id request finish");
        return ResponseEntity.ok(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/view/{userName}")
    public ResponseEntity<UserRequest> getUserByUserName(@PathVariable String userName){
        log.info("get user by userName request start");

        User user = userService.findUserByUserName(userName);

        UserRequest request = getUserDTO(user);

        /*Request request = Request.builder()
                .userPwd(userByUserName.getUserPass())
                .userName(userByUserName.getUserName())
                .id(userByUserName.getId())
                .roles(userRoleByUser)
                .build();*/

        log.info("get user by userName request finish");

        return ResponseEntity.ok(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> /*ResponseEntity<Map<String,Boolean>>*/ deleteUser(@PathVariable Long id){
        log.info("delete user request start");
//        logger.info("delete user request start");
        userService.deleteUser(id);

        log.info("delete user request finish");
//        logger.info("delete user request finish");
        return new ResponseEntity<>("successfully delete user", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        log.info("update user request start");
//        logger.info("update user request start");
        User user = userService.findUserById(id);

        UserRequest requestAfterUpdate = userService.updateUser(user, request);

        log.info("update user request finish");
//        logger.info("update user request finish");
        return ResponseEntity.ok(requestAfterUpdate);
    }

    private UserRequest getUserDTO(User user) {
        List<String> role = userRoleService.getRoleNameByUser(user);

        return UserRequest.builder()
                .id(user.getId())
                .roles(role)
                .userName(user.getUserName())
                .userPwd(user.getUserPass())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .birthday(user.getBirthday().toString())
                .email(user.getEmail())
                .phone(user.getPhone())
                .medicalCardNumber(user.getMedicalCardNumber())
                .build();
    }

}

