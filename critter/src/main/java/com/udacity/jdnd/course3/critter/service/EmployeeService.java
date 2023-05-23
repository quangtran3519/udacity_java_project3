package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);

    List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeDTO);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    EmployeeDTO getCustomerById(long id);


}
