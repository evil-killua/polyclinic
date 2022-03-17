package by.grsu.backend.service;

import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<String> getRoleNameByUser(User user);
    List<UserRole> getUserRoleByUser(User user);
//    UserRole findByUserAndRole(User user, Role role);
    void removeUserRole(UserRole userRole);
    void addNewUserRole(UserRole userRole);
}
