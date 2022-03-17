package by.grsu.backend.service.impl;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import by.grsu.backend.exception.ResourceNotFoundException;
import by.grsu.backend.repository.RoleRepository;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.repository.UserRoleRepository;
import by.grsu.backend.service.UserRoleService;
import by.grsu.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not exit with id: " + id));
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
    }

    @Override
    public void /*Map<String, Boolean>*/ deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not exit with id: " + id));

//        List<UserRole> userRoles = userRoleService.getUserRoleByUser(user);
        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        userRoles.forEach(r -> {
//            userRoleService.removeUserRole(r);
            userRoleRepository.delete(r);
        });


//        userRepository.delete(user);
        userRepository.deleteById(id);

        /*Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);*/

//        return response;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserRequest updateUser(User user, UserRequest request) {

//        if(request.getUserPwd().length()<25){
//            user.setUserPass(passwordEncoder.encode(request.getUserPwd()));
//        }

        if(!request.getUserPwd().equals(user.getUserPass())){
            user.setUserPass(passwordEncoder.encode(request.getUserPwd()));
        }

        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setMedicalCardNumber(request.getMedicalCardNumber());

//        saveUser(user);
        userRepository.save(user);

        return request;
    }

    /*@Override
    public UserRequest updateUser(User user, UserRequest request) {

        List<String> userRole = userRoleService.getRoleNameByUser(user);
        List<String> reqRole = request.getRoles();

        log.info("role in request: " + reqRole);
        log.info("role in db: " + userRole);

        user.setUserName(request.getUserName());
        user.setUserPass(passwordEncoder.encode(request.getUserPwd()));
        User updateUser = saveUser(user);

        List<String> similar = new ArrayList<>(userRole);
        List<String> different = new ArrayList<>();
        different.addAll(userRole);
        different.addAll(reqRole);

        similar.retainAll(reqRole);
        different.removeAll(similar);
        log.info("совпадения: " + similar);
        log.info("различия: " + different);

        List<String> newRole = new ArrayList<>();
        List<String> removeRole = new ArrayList<>();


        if (!different.isEmpty()) {
            for (String dr : different) {
                for (String ur : userRole) {

                    if (dr.equals(ur)) {
                        removeRole.add(dr);
                    } else if (ur.equals(userRole.get(userRole.size() - 1))) {
                        newRole.add(dr);
                    }
                }
            }
        }

        log.info("удалить: " + removeRole);
        log.info("добавить: " + newRole);

        System.out.println("/////////////////////////////\ncheck all size");
        System.out.println(removeRole.size() + newRole.size() == similar.size());
        System.out.println("/////////////////////////////");

        for (String roleName : removeRole) {
            Role role = roleRepository.findRoleByRoleName(roleName);

            UserRole userRole1 = userRoleService.findByUserAndAndRole(user, role);
            userRoleService.removeUserRole(userRole1);
        }

        for (String roleName : newRole) {
            Role role = roleRepository.findRoleByRoleName(roleName);
            UserRole userRole1 = UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();
            userRoleService.addNewUserRole(userRole1);

            similar.add(role.getRoleName());
        }


        return UserRequest.builder()
                .id(updateUser.getId())
                .userName(updateUser.getUserName())
                .userPwd(updateUser.getUserPass())
                .roles(similar)
                .build();

    }*/

}


