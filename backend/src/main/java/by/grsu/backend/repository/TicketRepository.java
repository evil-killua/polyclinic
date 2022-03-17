package by.grsu.backend.repository;

import by.grsu.backend.entity.Ticket;
import by.grsu.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
//    List<Ticket> getAllByDateAndUser(User doctor, LocalDate data);
//    List<Ticket> getAllByDateAndDoctor(User doctor, LocalDate date);
    List<Ticket> getAllByDoctor(User doctor);
    List<Ticket> getAllByUser(User user);
//    Ticket getByDateAndTimeAndDoctor(LocalDate date, Time time,User doctor);
}
