package by.grsu.backend.service.impl;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import by.grsu.backend.exception.ResourceNotFoundException;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.repository.UserRoleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private User user1;
    private User user2;
    List<User> userList;

    @BeforeEach
    void setUp() {
        userList = new ArrayList<>();

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        LocalDate parseDate2 = LocalDate.parse("1990-03-03", dateFormatter);

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

        user2 = User.builder()
                .id(2L)
                .address("address")
                .birthday(parseDate2)
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPass("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();

        userList.add(user1);
        userList.add(user2);
    }

    @AfterEach
    void tearDown() {
        user1 = user2 = null;
        userList = null;
    }

    @Test
    void findAll() {

        when(userRepository.findAll()).thenReturn(userList);
        List<User> findUsers = userService.findAll();
        assertEquals(userList,findUsers);
        verify(userRepository).findAll();

    }

    @Test
//    @Disabled
    void findUserById() {

        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        assertThat(userService.findUserById(user1.getId())).isEqualTo(user1);
    }

    @Test()
//    @Disabled
    void findUserByIdWithWrongUserId() {

        User user = User.builder()
                .id(12L)
                .build();

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class,()->{
            userService.findUserById(user.getId());
        });

        assertEquals("user not exit with id: 12",thrown.getMessage());
    }

    @Test
//    @Disabled
    void findUserByUserName() {

        when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.ofNullable(user1));
        assertThat(userService.findUserByUserName(user1.getUserName())).isEqualTo(user1);
    }

    @Test
//    @Disabled
    void deleteUser() {

        Role role = Role.builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .build();
        UserRole userRole = UserRole.builder()
                .id(1)
                .user(user1)
                .role(role)
                .build();

        /*Role role2 = Role.builder()
                .id(1L)
                .roleName("ROLE_1")
                .build();
        UserRole userRole2 = UserRole.builder()
                .id(2)
                .user(user1)
                .role(role2)
                .build();*/

        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);
//        userRoleList.add(userRole2);

        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        when(userRoleRepository.findByUser(user1)).thenReturn(userRoleList);

        userService.deleteUser(user1.getId());
        verify(userRoleRepository,times(userRoleList.size())).delete(any());
        verify(userRepository).deleteById(user1.getId());
    }

    @Test()
//    @Disabled
    void deleteUserWithWrongUserId() {

        User user = User.builder()
                .id(12L)
                .build();

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class,()->{
            userService.deleteUser(user.getId());
        });

        assertEquals("user not exit with id: 12",thrown.getMessage());
    }

    @Test
//    @Disabled
    void saveUser() {

        when(userRepository.save(any())).thenReturn(user1);
        userService.saveUser(user1);
        verify(userRepository).save(any());
    }

    @Test
//    @Disabled
    void updateUser() {
        UserRequest userRequest = UserRequest.builder()
                .id(2L)
                .address("address")
                .birthday("1990-03-03")
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPwd("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();

        when(userRepository.save(any())).thenReturn(user2);
//        userService.saveUser(user2);
        assertThat(userService.updateUser(user2,userRequest)).isEqualTo(userRequest);
        verify(userRepository).save(any());

    }
}