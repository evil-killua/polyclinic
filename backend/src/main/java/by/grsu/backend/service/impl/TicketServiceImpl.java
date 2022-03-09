package by.grsu.backend.service.impl;

import by.grsu.backend.dto.BookTicket;
import by.grsu.backend.entity.Ticket;
import by.grsu.backend.entity.User;
import by.grsu.backend.repository.TicketRepository;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.service.TicketService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service("ticketService")
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<BookTicket> getTicket(String doctorName, LocalDate date) {
        String[] words = doctorName.split(" ");
        User doctor = userRepository.getByFirstNameAndLastName(words[0], words[1]);
//        User doctor = userRepository.findByUserName(doctorName).get();
//        return ticketRepository.getAllByDateAndUser(doctor, date);
//        return ticketRepository.getAllByDateAndDoctor(doctor,date);

        List<BookTicket> tickets = new ArrayList<>();
        ticketRepository.getAllByDoctor(doctor).forEach(t->{
            if (t.getDate().equals(date) && t.getUser() == null){

                BookTicket bookTicket = BookTicket.builder()
                        .doctorName(t.getDoctor().getUserName())
                        .time(t.getTime().toString())
                        .date(t.getDate().toString())
                        .id(t.getId())
                        .build();

                tickets.add(bookTicket);
            }
        });

        return tickets;
    }

    @Override
    public List<BookTicket> getBookedTicketByDoctorNameAndDate(String doctorName, LocalDate date) {
        /*String[] words = doctorName.split(" ");
        User doctor = userRepository.getByFirstNameAndLastName(words[0], words[1]);*/

        User doctor = userRepository.findByUserName(doctorName).get();

        List<BookTicket> tickets = new ArrayList<>();
        ticketRepository.getAllByDoctor(doctor).forEach(t->{
            if (t.getDate().equals(date) && t.getUser() != null){

                BookTicket bookTicket = BookTicket.builder()
                        .doctorName(t.getDoctor().getUserName())
                        .time(t.getTime().toString())
                        .date(t.getDate().toString())
                        .id(t.getId())
                        .userName(t.getUser().getFirstName() + " " + t.getUser().getLastName())
                        .build();

                tickets.add(bookTicket);
            }
        });


        return tickets;
    }

    @SneakyThrows
    @Override
    public /*Answer*/ void bookTicket(BookTicket bookTicket) {
//        Ticket ticket = ticketRepository.getById(id);

        LocalDate parseDate = LocalDate.parse(bookTicket.getDate(), dateFormatter);
        Time parseTime = new Time(timeFormat.parse(bookTicket.getTime()).getTime());

//        if(ticketRepository.getByDateAndTimeAndDoctor(parseDate,parseTime,boo))

        User user = userRepository.findByUserName(bookTicket.getUserName()).get();

        String[] words = bookTicket.getDoctorName().split(" ");
        User doctor = userRepository.getByFirstNameAndLastName(words[0], words[1]);
//        User doctor = userRepository.findByUserName(bookTicket.getDoctorName()).get();
        Ticket ticket =null;
        List<Ticket> byDoctor = ticketRepository.getAllByDoctor(doctor);
        for (Ticket t:byDoctor) {
            if (t.getDate().equals(parseDate/*bookTicket.getDate()*/) && t.getTime().equals(parseTime/*bookTicket.getTime()*/)){
                ticket = t;
                break;
            }
        }

        if (ticket.getUser()!=null){
            throw new RuntimeException("This ticket is already taken");
        }
        log.info("ticket before update: " + ticket);
        ticket.setUser(user);

        ticketRepository.save(ticket);

        /*return Answer.builder()
                .message("successfully booked")
                .build();*/
    }

    @Override
    public List<BookTicket> findTicketByUserName(String userName) {

        User user = userRepository.findByUserName(userName).get();
        List<BookTicket> ticketList = new ArrayList<>();
        ticketRepository.getAllByUser(user).forEach(t->{
            BookTicket bookTicket = BookTicket.builder()
                    .date(t.getDate().toString())
                    .time(t.getTime().toString())
                    .userName(user.getUserName())
//                    .doctorName(t.getDoctor().getUserName())
                    .doctorName(t.getDoctor().getFirstName() + " " + t.getDoctor().getLastName())
                    .id(t.getId())
                    .build();

            ticketList.add(bookTicket);
        });

        ticketList.sort(Comparator.comparing(BookTicket::getDate).reversed());

        return ticketList;
    }

    @Override
    @SneakyThrows
    public void refuseTicket(BookTicket bookTicket) {

//        User doctor = userRepository.findByUserName(bookTicket.getDoctorName()).get();

        String[] words = bookTicket.getDoctorName().split(" ");
        User doctor = userRepository.getByFirstNameAndLastName(words[0], words[1]);

        LocalDate parseDate = LocalDate.parse(bookTicket.getDate(), dateFormatter);
        Time parseTime = new Time(timeFormat.parse(bookTicket.getTime()).getTime());

//        Ticket ticket =null;
        List<Ticket> byDoctor = ticketRepository.getAllByDoctor(doctor);

        if (byDoctor.size()==0){
            throw new RuntimeException("no such ticket exists");
        }

        for (Ticket t:byDoctor) {
            if (t.getDate().equals(parseDate/*bookTicket.getDate()*/) && t.getTime().equals(parseTime/*bookTicket.getTime()*/)){
//                ticket = t;
//                System.out.println("find ticket: " + t);
                t.setUser(null);
//                System.out.println("find ticket2: " + t);
                ticketRepository.save(t);
                break;
            }
        }

        /*log.info("ticket before update: " + ticket);
        ticket.setUser(null);

        log.info("ticket before refuse: " + ticket);

        ticketRepository.save(ticket);*/
    }

    @SneakyThrows
    @Override
    public void /*Answer*/ addNewTicket(BookTicket bookTicket) {

        String[] words = bookTicket.getDoctorName().split(" ");
        User doctor = userRepository.getByFirstNameAndLastName(words[0], words[1]);

//        User doctor = userRepository.findByUserName(bookTicket.getDoctorName()).get();
        Ticket ticket =null;
        List<Ticket> byDoctor = ticketRepository.getAllByDoctor(doctor);
        for (Ticket t:byDoctor) {
            if (t.getDate().equals(/*parseDate*/bookTicket.getDate()) && t.getTime().equals(/*parseTime*/bookTicket.getTime())){
                ticket = t;
                break;
            }
        }
        if (ticket==null){
            String time = bookTicket.getTime() + ":00";
            LocalDate parseDate = LocalDate.parse(bookTicket.getDate(), dateFormatter);
            Time parseTime = new Time(timeFormat.parse(time /*bookTicket.getTime()*/).getTime());

            ticket = Ticket.builder()
                    .date(parseDate)
                    .time(parseTime)
                    .doctor(doctor)
                    .build();

            ticketRepository.save(ticket);

            /*return Answer.builder()
                    .message("successfully created")
                    .build();*/
        }else {

            throw new RuntimeException("such a ticket already exists");
            /*return Answer.builder()
                    .message("creation error")
                    .description("such a ticket already exists")
                    .build();*/
        }
    }

    @Override
    public void /*Map<String,Boolean>*/ deleteTicket(Long id) {

//        Ticket ticket = ticketRepository.getById(id);
        if(!ticketRepository.findById(id).isPresent()){
            throw new RuntimeException("no such ticket exists");
        }

        ticketRepository.deleteById(id);
    }


/*        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);*/

//        return response;

}

