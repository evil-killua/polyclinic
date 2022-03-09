package by.grsu.backend.controller;

import java.util.Set;
import java.util.stream.Collectors;

import by.grsu.backend.aop.LogInfo;
import by.grsu.backend.dto.Request;
import by.grsu.backend.dto.UserRequest;
import by.grsu.backend.dto.Response;
import by.grsu.backend.exception.DisabledUserException;
import by.grsu.backend.exception.InvalidUserCredentialsException;
import by.grsu.backend.repository.UserRepository;
import by.grsu.backend.service.UserAuthService;
import by.grsu.backend.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Api("authentication controller")
@RestController
@CrossOrigin(value = "http://localhost:4200")
public class JwtRestApi {

//	private Logger logger = LoggerFactory.getLogger(JwtRestApi.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("userAuthService")
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @LogInfo
    @ApiOperation("query for sign in")
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

    @LogInfo
    @ApiOperation("query for create account")
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

