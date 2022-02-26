package by.grsu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTicket {
    private long id;

//    @NotNull
//    @Min(1)
//    @Max(255)
    private String userName;

//    @NotNull
    private String /*LocalDate*/ date;

//    @NotNull
    private String /*Time*/ time;

//    @NotNull
    private String doctorName;

}
