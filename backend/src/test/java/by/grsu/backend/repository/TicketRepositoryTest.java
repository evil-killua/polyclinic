package by.grsu.backend.repository;

import by.grsu.backend.entity.Ticket;
import by.grsu.backend.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    /*@BeforeEach
    void setUp() {
    }*/

    @Test
    void getAllByDoctor() throws ParseException {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        LocalDate parseDateTicket = LocalDate.parse("2022-03-21", dateFormatter);
        LocalDate parseDateAdminBirthday = LocalDate.parse("1995-04-03", dateFormatter);
        Time parseTime = new Time(timeFormat.parse("20:10:00").getTime());

        User doc = User.builder()
                .id(2L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPass("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDateAdminBirthday)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        Ticket ticket = Ticket.builder()
                .date(parseDateTicket)
                .doctor(doc)
                .time(parseTime)
                .id(13L)
                .user(admin)
                .build();

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        ticketRepository.save(ticket);

        List<Ticket> findTickets = ticketRepository.getAllByDoctor(doc);

        assertEquals(tickets,findTickets);
    }

    @Test
    void getAllByDoctorWithWrongData() throws ParseException {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        LocalDate parseDateTicket = LocalDate.parse("2022-03-21", dateFormatter);
        LocalDate parseDateAdminBirthday = LocalDate.parse("1995-04-03", dateFormatter);
        Time parseTime = new Time(timeFormat.parse("20:10:00").getTime());

        /*User doc = User.builder()
                .id(2L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPass("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();*/

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDateAdminBirthday)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        /*Ticket ticket = Ticket.builder()
                .date(parseDateTicket)
                .doctor(doc)
                .time(parseTime)
                .id(12L)
                .user(admin)
                .build();*/

        List<Ticket> tickets = new ArrayList<>();
//        tickets.add(ticket);

//        ticketRepository.save(ticket);

        List<Ticket> findTickets = ticketRepository.getAllByDoctor(admin);

        assertEquals(tickets,findTickets);
    }

    @Test
//    @Ignore
    void getAllByUser() throws ParseException {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        LocalDate parseDateTicket = LocalDate.parse("2022-03-21", dateFormatter);
        LocalDate parseDateAdminBirthday = LocalDate.parse("1995-04-03", dateFormatter);
        Time parseTime = new Time(timeFormat.parse("20:10:00").getTime());

        User doc = User.builder()
                .id(2L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPass("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDateAdminBirthday)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        Ticket ticket = Ticket.builder()
                .date(parseDateTicket)
                .doctor(doc)
                .time(parseTime)
                .id(12L)
                .user(admin)
                .build();

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        ticketRepository.save(ticket);

        List<Ticket> findTickets = ticketRepository.getAllByUser(admin);

        assertEquals(tickets,findTickets);
    }

    @Test
//    @Ignore
    void getAllByUserWithWrongData() throws ParseException {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        LocalDate parseDateTicket = LocalDate.parse("2022-03-21", dateFormatter);
        LocalDate parseDateAdminBirthday = LocalDate.parse("1995-04-03", dateFormatter);
        Time parseTime = new Time(timeFormat.parse("20:10:00").getTime());

        User doc = User.builder()
                .id(2L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Madiha")
                .lastName("Monroe")
                .medicalCardNumber(2)
                .userName("doc1")
                .phone("345345345")
                .userPass("$2a$12$9VeAfN4YZvgkZl5cdXAUWursOFnT48VGtbdwiSid12emux3r.Z8i6")
                .build();

        /*User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDateAdminBirthday)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        Ticket ticket = Ticket.builder()
                .date(parseDateTicket)
                .doctor(doc)
                .time(parseTime)
                .id(12L)
                .user(admin)
                .build();*/

        List<Ticket> tickets = new ArrayList<>();
//        tickets.add(ticket);

//        ticketRepository.save(ticket);

        List<Ticket> findTickets = ticketRepository.getAllByUser(doc);

        assertEquals(tickets,findTickets);
    }
}