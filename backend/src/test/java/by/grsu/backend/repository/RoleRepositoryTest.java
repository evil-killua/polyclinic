package by.grsu.backend.repository;

import by.grsu.backend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findRoleByRoleName() {

        String roleName = "ROLE_ADMIN";

        Role findRole = roleRepository.findRoleByRoleName(roleName);

        Role role = Role.builder()
                .id(1L)
                .roleName(roleName)
                .build();

        assertEquals(role,findRole);
    }

    @Test
    void findRoleByRoleNameWithWrongRoleName() {

        String roleName = "qwerty";

        Role findRole = roleRepository.findRoleByRoleName(roleName);

        assertNull(findRole);
    }
}