package by.grsu.backend.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class Request {

    private long id;

//    @NotNull
//    @Min(1)
//    @Max(255)
    private String userName;

//    @NotNull
//    @Min(1)
//    @Max(255)
    private String userPwd;
    private List<String> roles;
}
