package by.grsu.backend.repository;


import by.grsu.backend.entity.DoctorSpeciality;
import by.grsu.backend.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpeciality,Long> {
    List<DoctorSpeciality> getAllBySpeciality(Speciality speciality);
}
