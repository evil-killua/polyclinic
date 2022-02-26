package by.grsu.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor_working_hour")
public class DoctorWorkingHour {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Doctor_Id")
    private User doctor;

    @Column(name = "Day_Of_Week")
    private int dayOfWeek;

    @Column(name = "Beginning_Of_Work")
    private Time beginningOfWork;

    @Column(name = "End_Of_Work")
    private Time endOfWork;
}
