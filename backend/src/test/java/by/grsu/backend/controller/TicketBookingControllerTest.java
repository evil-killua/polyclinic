package by.grsu.backend.controller;

import by.grsu.backend.dto.BookTicket;
import by.grsu.backend.entity.Speciality;
import by.grsu.backend.entity.User;
import by.grsu.backend.service.DoctorSpecialityService;
import by.grsu.backend.service.SpecialityService;
import by.grsu.backend.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "admin",roles = {"ADMIN","USER"})
class TicketBookingControllerTest {

    @Mock
    private SpecialityService specialityService;

    @Mock
    private DoctorSpecialityService doctorSpecialityService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketBookingController ticketBookingController;

    @Autowired
    private MockMvc mockMvc;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private User user1;
    private User user2;
    List<User> userList;

    private BookTicket bookTicket1;
    List<BookTicket> bookTicketList;

    private BookTicket bookTicket2;
    List<BookTicket> bookedTicketList;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketBookingController).build();

        userList = new ArrayList<>();
        bookTicketList = new ArrayList<>();
        bookedTicketList = new ArrayList<>();

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        LocalDate parseDate2 = LocalDate.parse("1990-03-03", dateFormatter);
//        LocalDate parseDateTicket = LocalDate.parse("2022-03-07", dateFormatter);

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

        bookTicket1 = BookTicket.builder()
                .id(1)
                .date("2022-03-07")
                .doctorName("Timur Braun")
                .time("15:00:00")
//                .userName("Madiha Monroe")
                .build();

        bookTicket2 = BookTicket.builder()
                .id(2)
                .date("2022-03-07")
                .doctorName("Timur Braun")
                .time("17:00:00")
                .userName("Madiha Monroe")
                .build();

        bookTicketList.add(bookTicket1);
        bookedTicketList.add(bookTicket2);

    }

    @AfterEach
    void tearDown() {
        user1 = user2 = null;
        bookTicket1 = bookTicket2 = null;
        userList = null;
        bookTicketList = bookedTicketList = null;
    }

    @Test
    void getAllSpeciality() throws Exception {
        List<Speciality> specialityList = new ArrayList<>();
        Speciality speciality = Speciality.builder()
                .id(1L)
                .specialityName("Инфекционист")
                .build();

        specialityList.add(speciality);

        when(specialityService.findAll()).thenReturn(specialityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/speciality")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(Collections.singletonList("Инфекционист"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(specialityService).findAll();
    }

    @Test
//    @Disabled
    void getDoctorsBySpeciality() throws Exception {

        when(doctorSpecialityService.getDoctorBySpeciality("qwe")).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/doctor/qwe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(Collections.singletonList("Timur Braun"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(doctorSpecialityService).getDoctorBySpeciality("qwe");
    }

    @Test
//    @Disabled
    void getDoctorWorkTime() throws Exception {
        LocalDate parseDateTicket = LocalDate.parse("2022-03-07", dateFormatter);

        when(ticketService.getTicket("Timur Braun",parseDateTicket)).thenReturn(bookTicketList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/ticket/Timur Braun/" + parseDateTicket)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookTicketList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(ticketService).getTicket("Timur Braun",parseDateTicket);
    }

    @Test
//    @Disabled
    void getDoctorBookedTickets() throws Exception {

        LocalDate parseDateTicket = LocalDate.parse("2022-03-07", dateFormatter);

        when(ticketService.getBookedTicketByDoctorNameAndDate("Timur Braun",parseDateTicket)).thenReturn(bookedTicketList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/ticket/Timur Braun/by/" + parseDateTicket)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookedTicketList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(ticketService).getBookedTicketByDoctorNameAndDate("Timur Braun",parseDateTicket);

    }

    @Test
//    @Disabled
    void bookingTicket() throws Exception {

        when(ticketService.bookTicket(bookTicket1)).thenReturn(bookTicket1);

        mockMvc.perform(MockMvcRequestBuilders.put("/book/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(/*"successfully booked"*/ new ObjectMapper().writeValueAsString(bookTicket1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ticketService).bookTicket(bookTicket1);
    }

    @Test
//    @Disabled
    void getAllTicketByUserName() throws Exception {

        when(ticketService.findTicketByUserName(user2.getUserName())).thenReturn(bookedTicketList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/view-ticket/" + user2.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookedTicketList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(ticketService).findTicketByUserName(user2.getUserName());

    }

    @Test
//    @Disabled
    void refuseTicket() throws Exception {

        when(ticketService.refuseTicket(bookTicket2)).thenReturn(bookTicket2);

        mockMvc.perform(MockMvcRequestBuilders.put("/book/refuse-ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(/*"successfully refused"*/new ObjectMapper().writeValueAsString(bookTicket2)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ticketService).refuseTicket(bookTicket2);

    }

    @Test
//    @Disabled
    void createTicket() throws Exception {

        when(ticketService.addNewTicket(bookTicket1)).thenReturn(bookTicket1);

        mockMvc.perform(MockMvcRequestBuilders.post("/book/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(/*"successfully created"*/ new ObjectMapper().writeValueAsString(bookTicket1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ticketService).addNewTicket(bookTicket1);
    }

    @Test
//    @Disabled
    void deleteTicket() throws Exception {

        doNothing().when(ticketService).deleteTicket(bookTicket1.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/ticket/" + bookTicket1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("successfully deleted"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ticketService).deleteTicket(bookTicket1.getId());


    }
}