package by.grsu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private long id;

//    @Min(1)
//    @Max(255)
//    @NotNull
    private String userName;

//    @Min(1)
//    @Max(255)
//    @NotNull
    private String userPwd;

//    @Min(1)
//    @Max(255)
//    @NotNull
    private String firstName;

//    @Min(1)
//    @Max(255)
//    @NotNull
    private String lastName;

    private String birthday;
    private String address;

//    @NotNull
    private long medicalCardNumber;

//    @Email
//    @NotNull
    private String email;

//    @NotNull
//    @Min(9)
//    @Max(9)
    private String phone;

//    @NotNull
    private List<String> roles;
}
