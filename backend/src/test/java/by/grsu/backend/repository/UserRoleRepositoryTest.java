package by.grsu.backend.repository;

import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void findByUser() {

        Role role = Role.builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .build();

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        String firstName = "Timur";
        String lastName = "Braun";

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName(firstName)
                .lastName(lastName)
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        UserRole userRole = UserRole.builder()
                .role(role)
                .id(1)
                .user(admin)
                .build();

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);

        List<UserRole> findRoles = userRoleRepository.findByUser(admin);

        assertEquals(roles,findRoles);
    }

    @Test
    void findByUserWithWrongUser() {

        /*Role role = Role.builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .build();*/

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        String firstName = "Timur";
        String lastName = "Braun";

        User admin = User.builder()
                .id(5L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName(firstName)
                .lastName(lastName)
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        /*UserRole userRole = UserRole.builder()
                .role(role)
                .id(1)
                .user(admin)
                .build();*/

        List<UserRole> roles = new ArrayList<>();
//        roles.add(userRole);

        List<UserRole> findRoles = userRoleRepository.findByUser(admin);

        assertEquals(roles,findRoles);

    }

}