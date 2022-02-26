package by.grsu.backend.repository;


import by.grsu.backend.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,Long> {
    Speciality getAllBySpecialityName(String name);
}
