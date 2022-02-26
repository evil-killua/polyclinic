package by.grsu.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "speciality")
public class Speciality {

    @Id
    @GeneratedValue
    @Column(name = "speciality_id")
    private Long id;

    @Column(name = "speciality_name")
    private String specialityName;

}