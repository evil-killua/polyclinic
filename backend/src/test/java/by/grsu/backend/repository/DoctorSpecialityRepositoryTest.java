package by.grsu.backend.repository;

import by.grsu.backend.entity.DoctorSpeciality;
import by.grsu.backend.entity.Speciality;
import by.grsu.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DoctorSpecialityRepositoryTest {

    @Autowired
    private DoctorSpecialityRepository doctorSpecialityRepository;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Test
    void getAllBySpeciality() {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        Speciality speciality = Speciality.builder()
                .id(1L)
                .specialityName("Инфекционист")
                .build();

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

        List<DoctorSpeciality> findDoctorSpeciality = doctorSpecialityRepository.getAllBySpeciality(speciality);

        DoctorSpeciality doctorSpeciality = DoctorSpeciality.builder()
                .id(1L)
                .speciality(speciality)
                .doctor(doc)
                .build();

        List<DoctorSpeciality> doctorSpecialities = new ArrayList<>();
        doctorSpecialities.add(doctorSpeciality);

        assertEquals(doctorSpecialities,findDoctorSpeciality);
    }

    @Test
    void getAllBySpecialityWithWrongSpeciality() {

        LocalDate parseDate = LocalDate.parse("1990-07-21", dateFormatter);
        Speciality speciality = Speciality.builder()
                .id(13L)
                .specialityName("qwerty")
                .build();

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

        List<DoctorSpeciality> findDoctorSpeciality = doctorSpecialityRepository.getAllBySpeciality(speciality);

        /*DoctorSpeciality doctorSpeciality = DoctorSpeciality.builder()
                .id(1L)
                .speciality(speciality)
                .doctor(doc)
                .build();*/

        List<DoctorSpeciality> doctorSpecialities = new ArrayList<>();
//        doctorSpecialities.add(doctorSpeciality);

        assertEquals(doctorSpecialities,findDoctorSpeciality);
    }
}