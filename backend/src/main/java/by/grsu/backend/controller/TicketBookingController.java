package by.grsu.backend.controller;

import by.grsu.backend.aop.LogInfo;
import by.grsu.backend.dto.BookTicket;
import by.grsu.backend.entity.User;
import by.grsu.backend.service.DoctorSpecialityService;
import by.grsu.backend.service.SpecialityService;
import by.grsu.backend.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Api("controller for work with tickets")
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("/book/")
public class TicketBookingController {

    @Autowired
    @Qualifier("specialityService")
    private SpecialityService specialityService;

    @Autowired
    @Qualifier("doctorSpecialityService")
    private DoctorSpecialityService doctorSpecialityService;

    @Autowired
    @Qualifier("ticketService")
    private TicketService ticketService;

    @LogInfo
    @ApiOperation("query for get all doctor speciality")
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

    @LogInfo
    @ApiOperation("query for get list doctor by speciality")
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

                names.add(concat);
            });
        }

        return names;
    }

    @LogInfo
    @ApiOperation("query for get doctor work time")
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

    @LogInfo
    @ApiOperation("query for get booked tickets to doctor")
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

    @LogInfo
    @ApiOperation("query for book ticket")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/ticket")
    public /*ResponseEntity<String>*/ BookTicket bookingTicket( @RequestBody BookTicket bookTicket){
        log.info("start booking ticket request");
        log.info("book: " + bookTicket);

        return ticketService.bookTicket(bookTicket);

//        return new ResponseEntity<>("successfully booked",HttpStatus.OK);
    }

    @LogInfo
    @ApiOperation("query for get tickets by username")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/view-ticket/{userName}")
    public List<BookTicket> getAllTicketByUserName(@PathVariable String userName){
        log.info("get all ticket by userName request start");

        return ticketService.findTicketByUserName(userName);
    }

    @LogInfo
    @ApiOperation("query for refuse ticket")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/refuse-ticket")
    public /*ResponseEntity<String>*/ BookTicket refuseTicket( @RequestBody BookTicket bookTicket){
        log.info("start refuse ticket request");
        log.info("refuse ticket: " + bookTicket);
        return ticketService.refuseTicket(bookTicket);
//        log.info("req: " + bookTicket);

//        Map<String,Boolean> response = new HashMap<>();
//        response.put("refuse",Boolean.TRUE);

//        return new ResponseEntity<>("successfully refused",HttpStatus.OK);
    }

    @LogInfo
    @ApiOperation("query for create ticket")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ticket")
    public /*ResponseEntity<String>*/ BookTicket createTicket( @RequestBody BookTicket bookTicket){
        log.info("start add new ticket request");

        log.info("book: " + bookTicket);
        return ticketService.addNewTicket(bookTicket);

/*        return new ResponseEntity<>("successfully created",HttpStatus.OK);*/
    }

    @LogInfo
    @ApiOperation("query for delete ticket")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable long id){
        log.info("start delete ticket request");

        ticketService.deleteTicket(id);

        return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
    }


}

