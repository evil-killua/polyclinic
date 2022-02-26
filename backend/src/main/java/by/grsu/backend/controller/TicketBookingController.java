package by.grsu.backend.controller;

import by.grsu.backend.dto.Answer;
import by.grsu.backend.dto.BookTicket;
import by.grsu.backend.entity.User;
import by.grsu.backend.service.DoctorSpecialityService;
import by.grsu.backend.service.SpecialityService;
import by.grsu.backend.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/book/")
public class TicketBookingController {

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private DoctorSpecialityService doctorSpecialityService;

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/speciality")
    public List<String> getAllSpeciality(){
        log.info("get all speciality request start");
        List<String> specialityNameList = new ArrayList<>();

        specialityService.findAll().forEach(s->{
            specialityNameList.add(s.getSpecialityName());
        });

        log.info("get all speciality request finish");
        return specialityNameList;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/doctor/{spec}")
    public List<String> getDoctorsBySpeciality(@PathVariable String spec){
        log.info("get all doctor by speciality request");
        List<User> doctor = doctorSpecialityService.getDoctorBySpeciality(spec);
        List<String> names = new ArrayList<>();

        log.info("found " + doctor.size() + " doctors specialty " + spec);
//        System.out.println("count doc: " + doctor.size());
        if (doctor.size()>0){
            doctor.forEach(d->{
                String s = " ";
                s = s.concat(d.getLastName());
                String concat = d.getFirstName();
                concat = concat.concat(s);
                System.out.println(concat);
                names.add(concat);
            });
        }

        return names;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/ticket/{doc}/{date}")
    public List<BookTicket> getDoctorWorkTime(@PathVariable String doc, @PathVariable String date){
        log.info("get doctor work time request");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parse = LocalDate.parse(date, formatter);

        log.info("date after parse: " + parse);


        List<BookTicket> ticketList = ticketService.getTicket(doc,parse);
        /*List<String> timeList = new ArrayList<>();

        ticketList.forEach(t->{
            timeList.add(t.getTime().toString());
        });*/

        return ticketList;
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/ticket/{doc}/by/{date}")
    public List<BookTicket> getDoctorBookedTickets(@PathVariable String doc, @PathVariable String date) {

        log.info("get Doctor's booked tickets");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate parse = LocalDate.parse(date, formatter);

        log.info("date after parse: " + parse);

        List<BookTicket> tickets = ticketService.getBookedTicketByDoctorNameAndDate(doc, parse);

        return tickets;
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/ticket")
    public ResponseEntity<String> /*Answer*/ bookingTicket( @RequestBody BookTicket bookTicket){
        log.info("start booking ticket request");
        log.info("book: " + bookTicket);

        ticketService.bookTicket(bookTicket);

        return new ResponseEntity<>("successfully booked",HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/view-ticket/{userName}")
    public List<BookTicket> getAllTicketByUserName(@PathVariable String userName){
        log.info("get all ticket by userName request start");

        return ticketService.findTicketByUserName(userName);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/refuse-ticket")
    public ResponseEntity<String> refuseTicket( @RequestBody BookTicket bookTicket){
        log.info("start refuse ticket request");
        log.info("refuse ticket: " + bookTicket);
        ticketService.refuseTicket(bookTicket);
//        log.info("req: " + bookTicket);

//        Map<String,Boolean> response = new HashMap<>();
//        response.put("refuse",Boolean.TRUE);

        return new ResponseEntity<>("successfully refused",HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ticket")
    public ResponseEntity<String> /*Answer*/ createTicket( @RequestBody BookTicket bookTicket){
        log.info("start add new ticket request");

        log.info("book: " + bookTicket);
        ticketService.addNewTicket(bookTicket);

        return new ResponseEntity<>("successfully created",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable long id){
        log.info("start delete ticket request");

        ticketService.deleteTicket(id);

        return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
    }


}

