package by.grsu.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

import javax.persistence.*;

@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name",length = 50)
    private String userName;

    @Column(name = "user_pass", length = 128)
    private String userPass;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "birthday", columnDefinition = "TIMESTAMP")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate birthday;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "medical_card_number")
    private long medicalCardNumber;

    @Column(name = "email",length = 50)
    private String email;

    @Column(name = "phone",length = 9)
    private String phone;

}

