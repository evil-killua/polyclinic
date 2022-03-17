package by.grsu.backend.repository;

import java.util.Optional;

import by.grsu.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* User */Optional<User> findByUserName(String userName);
    User getByFirstNameAndLastName(String firstName,String lastName);
//    void delete(User user);
//	void deleteById(Long id);

}
