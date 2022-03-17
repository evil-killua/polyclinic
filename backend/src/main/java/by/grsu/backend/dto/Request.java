package by.grsu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
