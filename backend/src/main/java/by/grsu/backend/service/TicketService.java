package by.grsu.backend.service;

import by.grsu.backend.dto.Answer;
import by.grsu.backend.dto.BookTicket;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TicketService {
    List<BookTicket> getTicket(String doctorName, LocalDate date);
    List<BookTicket> getBookedTicketByDoctorNameAndDate(String doctorName, LocalDate date);
    void /*Answer*/ bookTicket(BookTicket bookTicket);
    List<BookTicket> findTicketByUserName(String userName);
    void refuseTicket(BookTicket bookTicket);
    void /*Answer*/ addNewTicket(BookTicket bookTicket);
    void /*Map<String,Boolean>*/ deleteTicket(Long id);
}
