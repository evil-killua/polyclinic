package by.grsu.backend.service.impl;

import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import by.grsu.backend.repository.UserRoleRepository;
import by.grsu.backend.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<String> getRoleNameByUser(User user) {

        List<String> roles = new ArrayList<>();
        userRoleRepository.findByUser(user).forEach(r->roles.add(r.getRole().getRoleName()));

        return roles;
    }

    @Override
    public List<UserRole> getUserRoleByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    @Override
    public UserRole findByUserAndAndRole(User user, Role role) {
        return userRoleRepository.findByUserAndAndRole(user,role);
    }

    @Override
    public void removeUserRole(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }

    @Override
    public void addNewUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }
}

