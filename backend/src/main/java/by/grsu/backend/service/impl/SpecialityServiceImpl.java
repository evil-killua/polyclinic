package by.grsu.backend.service.impl;

import by.grsu.backend.entity.Speciality;
import by.grsu.backend.repository.SpecialityRepository;
import by.grsu.backend.service.SpecialityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("specialityService")
@Slf4j
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    private SpecialityRepository specialityRepository;


    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }
}
