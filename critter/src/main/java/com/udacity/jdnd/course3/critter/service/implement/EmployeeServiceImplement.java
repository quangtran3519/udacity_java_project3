package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImplement implements EmployeeService {


    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImplement.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImplement(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.info("save employee {}", employeeDTO.toString());
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());


        return mapToDTO(employeeRepository.save(employee));
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());

        return dto;
    }

    @Override
    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeDTO) {
        log.info("find customer for service {}", employeeDTO.toString());


        return employeeRepository.
                findByDaysAvailable(employeeDTO.getDate().getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills().containsAll(employeeDTO.getSkills()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {

        log.info("set Availability  {} weeks {}", employeeId, daysAvailable);

        Employee employee = employeeRepository.getOne(employeeId);

        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);

    }

    @Override
    public EmployeeDTO getCustomerById(long id) {
        log.info("get CustomerById {}", id);

        return mapToDTO(employeeRepository.getOne(id));
    }
}
