package by.grsu.backend.service.impl;

import by.grsu.backend.entity.Speciality;
import by.grsu.backend.entity.User;
import by.grsu.backend.repository.DoctorSpecialityRepository;
import by.grsu.backend.repository.SpecialityRepository;
import by.grsu.backend.service.DoctorSpecialityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DoctorSpecialityServiceImpl implements DoctorSpecialityService {

    @Autowired
    private DoctorSpecialityRepository doctorSpecialityRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public List<User> getDoctorBySpeciality(String specialityName) {

        Speciality speciality = specialityRepository.getAllBySpecialityName(specialityName);

        List<User> specialityList = new ArrayList<>();
        doctorSpecialityRepository.getAllBySpeciality(speciality).forEach(us->{
            specialityList.add(us.getDoctor());
        });


        return specialityList;
    }
}
