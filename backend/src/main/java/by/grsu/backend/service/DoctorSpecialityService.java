package by.grsu.backend.service;

import by.grsu.backend.entity.User;

import java.util.List;

public interface DoctorSpecialityService {
    List<User> getDoctorBySpeciality(String speciality);
}
