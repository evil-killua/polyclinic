package by.grsu.backend.repository;

import by.grsu.backend.entity.DoctorWorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorWorkingHourRepository extends JpaRepository<DoctorWorkingHour,Long> {
}
