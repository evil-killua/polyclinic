package by.grsu.backend.service;

import by.grsu.backend.dto.BookTicket;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    List<BookTicket> getTicket(String doctorName, LocalDate date);
    List<BookTicket> getBookedTicketByDoctorNameAndDate(String doctorName, LocalDate date);
    /*void*/ /*Answer*/ BookTicket bookTicket(BookTicket bookTicket);
    List<BookTicket> findTicketByUserName(String userName);
    /*void*/ BookTicket refuseTicket(BookTicket bookTicket);
    /*void*/ BookTicket /*Answer*/ addNewTicket(BookTicket bookTicket);
    void /*Map<String,Boolean>*/ deleteTicket(Long id);
}
