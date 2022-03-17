package by.grsu.backend.repository;

import by.grsu.backend.entity.Speciality;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SpecialityRepositoryTest {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Test
    void getAllBySpecialityName() {

        String specialityName="Инфекционист";

        Speciality speciality = Speciality.builder()
                .specialityName(specialityName)
                .id(1L)
                .build();

        Speciality findSpeciality = specialityRepository.getAllBySpecialityName(specialityName);

        assertEquals(speciality,findSpeciality);
    }

    @Test
    void getAllBySpecialityNameWithWrongSpecialityName() {

        String specialityName="qwerty";

        /*Speciality speciality = Speciality.builder()
                .specialityName(specialityName)
                .id(1L)
                .build();*/

        Speciality findSpeciality = specialityRepository.getAllBySpecialityName(specialityName);

        assertNull(findSpeciality);
    }
}