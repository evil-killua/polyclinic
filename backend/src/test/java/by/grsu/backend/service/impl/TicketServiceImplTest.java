package by.grsu.backend.service.impl;

import by.grsu.backend.dto.BookTicket;
import by.grsu.backend.entity.Ticket;
import by.grsu.backend.entity.User;
import by.grsu.backend.exception.ResourceNotFoundException;
import by.grsu.backend.repository.TicketRepository;
import by.grsu.backend.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private TicketServiceImpl ticketService;

    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private User doc;
    private User user;
    private Ticket ticket;
    private Ticket ticket2;
    List<Ticket> ticketList;

    @BeforeEach
    void setUp() throws ParseException {
        ticketList = new ArrayList<>();

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        LocalDate parseDate2 = LocalDate.parse("1990-03-03", dateFormatter);
        LocalDate ticketDate = LocalDate.parse("2022-04-04", dateFormatter);
//        LocalDate ticketDate2 = LocalDate.parse("2022-04-04", dateFormatter);
        Time ticketTime = new Time(timeFormat.parse("15:50:00").getTime());
        Time ticketTime2 = new Time(timeFormat.parse("16:30:00").getTime());

        user = User.builder()
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

        doc = User.builder()
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

        ticket = Ticket.builder()
//                .user(user)
                .id(1L)
                .time(ticketTime)
                .doctor(doc)
                .date(ticketDate)
                .build();

        ticket2 = Ticket.builder()
                .user(user)
                .id(2L)
                .time(ticketTime2)
                .doctor(doc)
                .date(ticketDate)
                .build();


        ticketList.add(ticket);
        ticketList.add(ticket2);
    }

    @AfterEach
    void tearDown() {
//        user1 = user2 = null;
        doc = user = null;
        ticket = ticket2 = null;
        ticketList = null;
    }

    @Test
    void getTicket() {

        String doctorName="Madiha Monroe";
        List<BookTicket> tickets = new ArrayList<>();

        BookTicket bookTicket = BookTicket.builder()
                .id(1L)
                .date("2022-04-04")
                .doctorName(/*doctorName*/ doc.getUserName())
                .time("15:50:00")
//                .userName(user.getUserName())
                .build();
        tickets.add(bookTicket);

        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        List<BookTicket> findTickets = ticketService.getTicket(doctorName, this.ticket.getDate());
        assertEquals(tickets,findTickets);

    }

    @Test
//    @Disabled
    void getBookedTicketByDoctorNameAndDate() {

        String docUserName = "doc1";
        List<BookTicket> tickets = new ArrayList<>();

        BookTicket bookTicket = BookTicket.builder()
                .id(2L)
                .date("2022-04-04")
                .doctorName(/*doctorName*/ doc.getUserName())
                .time("16:30:00")
                .userName(user.getFirstName() + " " + user.getLastName())
                .build();
        tickets.add(bookTicket);

        when(userRepository.findByUserName(docUserName)).thenReturn(Optional.ofNullable(doc));
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        List<BookTicket> findTickets = ticketService.getBookedTicketByDoctorNameAndDate(docUserName, this.ticket.getDate());
        assertEquals(tickets,findTickets);
    }

    @Test
//    @Disabled
    void bookTicket() {

        BookTicket bookTicket = BookTicket.builder()
                .id(1L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName())
                .time("15:50:00")
                .userName(user.getUserName())
                .build();

        when(userRepository.findByUserName(bookTicket.getUserName())).thenReturn(Optional.ofNullable(user));
        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        ticketService.bookTicket(bookTicket);
        verify(ticketRepository).save(ticket);

    }

    @Test
//    @Disabled
    void bookTicketWithBookedTicket() {

        BookTicket bookTicket = BookTicket.builder()
                .id(2L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName())
                .time("16:30:00")
                .userName(user.getUserName())
                .build();

        when(userRepository.findByUserName(bookTicket.getUserName())).thenReturn(Optional.ofNullable(user));
        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,()->{
            ticketService.bookTicket(bookTicket);
        });

        assertEquals("This ticket is already taken",thrown.getMessage());

    }

    @Test
//    @Disabled
    void findTicketByUserName() {

        List<BookTicket> userBookTickets = new ArrayList<>();
        List<Ticket> userTickets = new ArrayList<>();
        userTickets.add(ticket2);

        BookTicket bookTicket = BookTicket.builder()
                .id(2L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName() )
                .time("16:30:00")
                .userName(user.getUserName())
                .build();
        userBookTickets.add(bookTicket);

        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.ofNullable(user));
        when(ticketRepository.getAllByUser(user)).thenReturn(userTickets/*ticketList*/);

        List<BookTicket> findTicketByUserName = ticketService.findTicketByUserName(user.getUserName());
        assertEquals(userBookTickets,findTicketByUserName);

    }

    @Test
//    @Disabled
    void refuseTicket() throws ParseException {

        BookTicket bookTicket = BookTicket.builder()
                .id(2L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName())
                .time("16:30:00")
                .userName(user.getUserName())
                .build();

        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        ticketService.refuseTicket(bookTicket);
        verify(ticketRepository).save(ticket2);

    }

    @Test
//    @Disabled
    void refuseTicketWithNoSuchTicketExists() {

        List<Ticket> tickets = new ArrayList<>();
        BookTicket bookTicket = BookTicket.builder()
                .id(2L)
                .date("2022-04-04")
                .doctorName(user.getFirstName() + " " + user.getLastName())
                .time("16:30:00")
                .userName(doc.getUserName())
                .build();

        when(userRepository.getByFirstNameAndLastName("Timur","Braun")).thenReturn(user);
        when(ticketRepository.getAllByDoctor(user)).thenReturn(tickets);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,()->{
            ticketService.refuseTicket(bookTicket);
        });

        assertEquals("no such ticket exists",thrown.getMessage());

    }

    @Test
//    @Disabled
    void addNewTicket() throws ParseException {

        LocalDate ticketDate3 = LocalDate.parse("2022-04-04", dateFormatter);
        Time ticketTime3 = new Time(timeFormat.parse("18:30:00").getTime());

        BookTicket bookTicket = BookTicket.builder()
                .id(3L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName())
                .time("18:30")
//                .userName(user.getUserName())
                .build();

        Ticket ticket3 = Ticket.builder()
//                .user(user)
//                .id(3L)
                .time(ticketTime3)
                .doctor(doc)
                .date(ticketDate3)
                .build();

        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        ticketService.addNewTicket(bookTicket);
        verify(ticketRepository).save(ticket3);

    }

    @Test
//    @Disabled
    void addNewTicketWithWrongData() throws ParseException {

        BookTicket bookTicket = BookTicket.builder()
                .id(3L)
                .date("2022-04-04")
                .doctorName(doc.getFirstName() + " " + doc.getLastName())
                .time("16:30")
//                .userName(user.getUserName())
                .build();

        when(userRepository.getByFirstNameAndLastName("Madiha","Monroe")).thenReturn(doc);
        when(ticketRepository.getAllByDoctor(doc)).thenReturn(ticketList);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,()->{
            ticketService.addNewTicket(bookTicket);
        });

        assertEquals("such a ticket already exists",thrown.getMessage());
    }

    @Test
//    @Disabled
    void deleteTicket() {

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.ofNullable(ticket));
        ticketService.deleteTicket(ticket.getId());
        verify(ticketRepository).findById(ticket.getId());
    }

    @Test
//    @Disabled
    void deleteTicketWithWrongId() {

        given(ticketRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,()->{
            ticketService.deleteTicket(12L);
        });

        assertEquals("no such ticket exists",thrown.getMessage());

    }

}