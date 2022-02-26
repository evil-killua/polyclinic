package by.grsu.backend.controller;

import java.util.Set;
import java.util.stream.Collectors;

import by.grsu.backend.dto.Request;
import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.dto.Response;
import by.grsu.backend.exception.DisabledUserException;
import by.grsu.backend.exception.InvalidUserCredentialsException;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.service.UserAuthService;
import by.grsu.backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:4200")
public class JwtRestApi {

//	private Logger logger = LoggerFactory.getLogger(JwtRestApi.class);

    private final JwtUtil jwtUtil;

    private final UserAuthService userAuthService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @Autowired
    public JwtRestApi(JwtUtil jwtUtil, UserAuthService userAuthService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userAuthService = userAuthService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<Response> generateJwtToken( @RequestBody Request request) {
//		logger.info("start signin");
        log.info("start signin");
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPwd()));
        } catch (DisabledException e) {
            log.error("User Inactive");
//			logger.error("User Inactive");
            throw new DisabledUserException("User Inactive");
        } catch (BadCredentialsException e) {
            log.error("Invalid Credentials");
//			logger.error("Invalid Credentials");
            throw new InvalidUserCredentialsException("Invalid Credentials");
        }

        User user = (User) authentication.getPrincipal();
        Set<String> roles = user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());

        String token = jwtUtil.generateToken(authentication);

        by.grsu.backend.entity.User user1 = userRepository.findByUserName(user.getUsername()).get();
        Response response = new Response();
        response.setToken(token);
        response.setRoles(roles.stream().collect(Collectors.toList()));
        response.setId(user1.getId());

//		logger.info("finish signin");
        log.info("finish signin");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup( @RequestBody UserRequest request) {
//		logger.info("start signup");
        log.info("start signup");
        userAuthService.saveUser(request);
//		logger.info("finish signup");
        log.info("finish signup");
        return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
    }

}

