package by.grsu.backend.repository;

import by.grsu.backend.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
class UserRepositoryTest {

//    @Mock
    @Autowired
    private UserRepository userRepository;
//    private AutoCloseable autoCloseable;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /*@BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
//    @Ignore
    void findByUserName() {

        String username="admin";

        Optional<User> findUserName = userRepository.findByUserName(username);
//        verify(userRepository).findByUserName(username);

        boolean present = findUserName.isPresent();

        assertThat(present).isTrue();
    }

    @Test
    void findByUserNameWithWrongUserName(){

        String username="qwerty";

        Optional<User> findUserName = userRepository.findByUserName(username);
//        verify(userRepository).findByUserName(username);

        boolean present = findUserName.isPresent();

        assertThat(present).isFalse();

    }

    @Test
//    @Ignore
    void getByFirstNameAndLastName() {

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        String firstName = "Timur";
        String lastName = "Braun";

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName(firstName)
                .lastName(lastName)
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        User findUser = userRepository.getByFirstNameAndLastName(firstName, lastName);

//        verify(userRepository).getByFirstNameAndLastName(firstName, lastName);
        assertEquals(admin,findUser);
    }

    @Test
    void getByFirstNameAndLastNameWithWrongFirstNameAndLastName() {

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        String firstName = "Timur";
        String lastName = "qwerty";

        User findUser = userRepository.getByFirstNameAndLastName(firstName, lastName);

//        verify(userRepository).getByFirstNameAndLastName(firstName, lastName);
        assertNull(findUser);
    }

    /*@Test
    @Ignore
    void delete() {

        LocalDate parseDate = LocalDate.parse("1995-04-03", dateFormatter);
        String firstName = "Timur";
        String lastName = "Braun";

        User admin = User.builder()
                .id(1L)
                .address("address")
                .birthday(parseDate)
                .email("arabchik.alex@gmail.com")
                .firstName(firstName)
                .lastName(lastName)
                .medicalCardNumber(1)
                .userName("admin")
                .phone("123123123")
                .userPass("$2a$12$wxVkr/Q9In/YnJjBVedat.SfuynHNsiWQAoxzzyHNgchQ1p1Nl7Oq")
                .build();

        userRepository.delete(admin);

        verify(userRepository).delete(admin);
    }*/
}