package by.grsu.backend.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.entity.Role;
import by.grsu.backend.entity.User;
import by.grsu.backend.entity.UserRole;
import by.grsu.backend.repository.RoleRepository;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userAuthService")
@Slf4j
public class UserAuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).get();

        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getRole().getRoleName());
        }).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getUserPass(), grantedAuthorities);
    }

    public void saveUser(UserRequest request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
//        System.out.println("req: " + request);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parseDate = LocalDate.parse(request.getBirthday(), dateFormatter);

        User user = User.builder()
                .userName(request.getUserName())
                .userPass(passwordEncoder.encode(request.getUserPwd()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .birthday(parseDate)
                .email(request.getEmail())
                .medicalCardNumber(request.getMedicalCardNumber())
                .phone(request.getPhone())
                .build();



        List<UserRole> roles = request.getRoles().stream().map(r -> {
//            System.out.println("r: " + r);
            Role role = roleRepository.findRoleByRoleName(r);
//            System.out.println("role: " + role);
            return UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();
        }).collect(Collectors.toList());


        userRepository.save(user);
        roles.forEach(r->userRoleRepository.save(r));


    }

}

