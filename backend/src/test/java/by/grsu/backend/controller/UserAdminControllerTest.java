package by.grsu.backend.controller;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.User;
import by.grsu.backend.service.UserRoleService;
import by.grsu.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "admin",roles = {"ADMIN"})
class UserAdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRoleService userRoleService;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private User user1;
    private User user2;
    List<User> userList;

    @InjectMocks
    private UserAdminController userAdminController;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userList = new ArrayList<>();
        mockMvc = MockMvcBuilders.standaloneSetup(userAdminController).build();

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
    void getAllUsers() throws Exception {

        List<UserRequest> userRequestList = new ArrayList<>();

        UserRequest userRequest1 = UserRequest.builder()
                .id(user1.getId())
                .roles(Collections.singletonList("ROLE_USER"))
                .userName(user1.getUserName())
                .userPwd(user1.getUserPass())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .address(user1.getAddress())
                .birthday(user1.getBirthday().toString())
                .email(user1.getEmail())
                .phone(user1.getPhone())
                .medicalCardNumber(user1.getMedicalCardNumber())
                .build();

        UserRequest userRequest2 = UserRequest.builder()
                .id(user2.getId())
                .roles(Collections.singletonList("ROLE_USER"))
                .userName(user2.getUserName())
                .userPwd(user2.getUserPass())
                .firstName(user2.getFirstName())
                .lastName(user2.getLastName())
                .address(user2.getAddress())
                .birthday(user2.getBirthday().toString())
                .email(user2.getEmail())
                .phone(user2.getPhone())
                .medicalCardNumber(user2.getMedicalCardNumber())
                .build();

        userRequestList.add(userRequest1);
        userRequestList.add(userRequest2);

        when(userService.findAll()).thenReturn(userList);
        when(userRoleService.getRoleNameByUser(user1)).thenReturn(Collections.singletonList("ROLE_USER"));
//        when(userRoleService.getRoleNameByUser(user2)).thenReturn(Collections.singletonList("ROLE_USER"));

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestList)))
                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userName").value("doc1"));
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect((ResultMatcher) jsonPath("$[0].userName", is(user1.getUserName())));

        verify(userService).findAll();
        verify(userRoleService,times(2)).getRoleNameByUser(any());
    }

    @Test
//    @Disabled
    void getUserById() throws Exception {

        UserRequest userRequest1 = UserRequest.builder()
                .id(user1.getId())
                .roles(Collections.singletonList("ROLE_USER"))
                .userName(user1.getUserName())
                .userPwd(user1.getUserPass())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .address(user1.getAddress())
                .birthday(user1.getBirthday().toString())
                .email(user1.getEmail())
                .phone(user1.getPhone())
                .medicalCardNumber(user1.getMedicalCardNumber())
                .build();

        when(userService.findUserById(user1.getId())).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", /*hasSize(1)*/Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id()").value(Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("admin"));

        verify(userService).findUserById(user1.getId());
    }

    @Test
//    @Disabled
    void getUserByUserName() throws Exception {

        UserRequest userRequest1 = UserRequest.builder()
                .id(user1.getId())
                .roles(Collections.singletonList("ROLE_USER"))
                .userName(user1.getUserName())
                .userPwd(user1.getUserPass())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .address(user1.getAddress())
                .birthday(user1.getBirthday().toString())
                .email(user1.getEmail())
                .phone(user1.getPhone())
                .medicalCardNumber(user1.getMedicalCardNumber())
                .build();

        when(userService.findUserByUserName(user1.getUserName())).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/view/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", /*hasSize(1)*/Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id()").value(Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("admin"));

        verify(userService).findUserByUserName(user1.getUserName());
    }

    @Test
//    @Disabled
    void deleteUser() throws Exception {

        doNothing().when(userService).deleteUser(user1.getId());

        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("successfully delete user"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService).deleteUser(user1.getId());
    }

    @Test
//    @Disabled
    void updateUser() throws Exception {

        UserRequest userRequest1 = UserRequest.builder()
                .id(user1.getId())
                .roles(Collections.singletonList("ROLE_USER"))
                .userName(user1.getUserName())
                .userPwd(user1.getUserPass())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .address(user1.getAddress())
                .birthday(user1.getBirthday().toString())
                .email(user1.getEmail())
                .phone(user1.getPhone())
                .medicalCardNumber(user1.getMedicalCardNumber())
                .build();

        when(userService.findUserById(user1.getId())).thenReturn(user1);
        when(userService.updateUser(user1,userRequest1)).thenReturn(userRequest1);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest1)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService).updateUser(user1,userRequest1);
    }
}