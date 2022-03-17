package by.grsu.backend.service.impl;

import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import by.grsu.backend.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Autowired
    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private User user1;
    private Role role;
    private UserRole userRole;
    List<UserRole> userRoleList;

    @BeforeEach
    void setUp() {

        userRoleList = new ArrayList<>();
        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);

        user1 = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        role = Role.builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .build();
        userRole = UserRole.builder()
                .id(1)
                .user(user1)
                .role(role)
                .build();
        userRoleList.add(userRole);
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        userRoleList = null;
        role = null;
        userRole = null;
    }

    @Test
    void getRoleNameByUser() {
        List<String> roleNameList = new ArrayList<>();
        String roleAdmin = "ROLE_ADMIN";
        roleNameList.add(roleAdmin);

        when(userRoleRepository.findByUser(user1)).thenReturn(userRoleList);
        List<String> findRoleNameByUser = userRoleService.getRoleNameByUser(user1);
        assertEquals(roleNameList,findRoleNameByUser);
        verify(userRoleRepository).findByUser(user1);

    }

    @Test
//    @Disabled
    void getUserRoleByUser() {
        when(userRoleRepository.findByUser(user1)).thenReturn(userRoleList);
        assertThat(userRoleService.getUserRoleByUser(user1)).isEqualTo(userRoleList);
    }

    @Test
//    @Disabled
    void removeUserRole() {

        userRoleService.removeUserRole(userRole);
        verify(userRoleRepository).delete(userRole);
    }

    @Test
//    @Disabled
    void addNewUserRole() {
        userRoleService.addNewUserRole(userRole);
        verify(userRoleRepository).save(userRole);
    }
}