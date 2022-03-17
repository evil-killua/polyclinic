package by.grsu.backend.service.impl;

import by.grsu.backend.entity.Speciality;
import by.grsu.backend.entity.User;
import by.grsu.backend.repository.SpecialityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceImplTest {

    @Mock
    private SpecialityRepository specialityRepository;

    @Autowired
    @InjectMocks
    private SpecialityServiceImpl specialityService;

    private Speciality speciality1;
    private Speciality speciality2;
    List<Speciality> specialityList;

    @BeforeEach
    void setUp() {
        specialityList = new ArrayList<>();

        speciality1 = Speciality.builder()
                .id(1L)
                .specialityName("Инфекционист")
                .build();

        speciality2 = Speciality.builder()
                .id(2L)
                .specialityName("Онколог")
                .build();

        specialityList.add(speciality1);
        specialityList.add(speciality2);
    }

    @AfterEach
    void tearDown() {
        speciality1 = speciality2 = null;
        specialityList = null;
    }

    @Test
    void findAll() {

        when(specialityRepository.findAll()).thenReturn(specialityList);
        List<Speciality> findSpeciality = specialityService.findAll();
        assertEquals(specialityList,findSpeciality);
        verify(specialityRepository).findAll();
    }
}