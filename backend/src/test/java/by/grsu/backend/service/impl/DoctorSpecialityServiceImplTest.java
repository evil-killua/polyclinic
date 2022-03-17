package by.grsu.backend.service.impl;

import by.grsu.backend.entity.DoctorSpeciality;
import by.grsu.backend.entity.Speciality;
import by.grsu.backend.entity.User;
import by.grsu.backend.repository.DoctorSpecialityRepository;
import by.grsu.backend.repository.SpecialityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorSpecialityServiceImplTest {

    @Mock
    private DoctorSpecialityRepository doctorSpecialityRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @Autowired
    @InjectMocks
    private DoctorSpecialityServiceImpl doctorSpecialityService;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Speciality speciality1;
    private User doc1;
    private List<DoctorSpeciality> doctorSpecialityList;
    private List<User> doctorList;

    @BeforeEach
    void setUp() {
        doctorSpecialityList = new ArrayList<>();
        doctorList = new ArrayList<>();


        speciality1 = Speciality.builder()
                .id(1L)
                .specialityName("Инфекционист")
                .build();

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);

        doc1 = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName("Timur")
                .lastName("Braun")
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        DoctorSpeciality doctorSpeciality = DoctorSpeciality.builder()
                .id(1L)
                .doctor(doc1)
                .speciality(speciality1)
                .build();

        doctorSpecialityList.add(doctorSpeciality);
        doctorList.add(doc1);
    }

    @AfterEach
    void tearDown() {
        doc1= null;
        speciality1 = null;
        doctorList = null;
    }

    @Test
    void getDoctorBySpeciality() {

        when(specialityRepository.getAllBySpecialityName(speciality1.getSpecialityName())).thenReturn(speciality1);
        when(doctorSpecialityRepository.getAllBySpeciality(speciality1)).thenReturn(doctorSpecialityList);

        List<User> findDoctorBySpeciality = doctorSpecialityService.getDoctorBySpeciality(speciality1.getSpecialityName());

        assertEquals(doctorList,findDoctorBySpeciality);
    }
}