package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImplement implements ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImplement.class);

    private final ScheduleRepository scheduleRepository;

    private final PetRepository petRepository;

    private final EmployeeRepository employeeRepository;

    public ScheduleServiceImplement(ScheduleRepository scheduleRepository,
                                    PetRepository petRepository,
                                    EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

        log.info("create schedule {}", scheduleDTO.toString());

        Schedule schedule = new Schedule();

        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(petRepository.findAllById(scheduleDTO.getPetIds()));
        schedule.setEmployees(employeeRepository.findAllById(scheduleDTO.getEmployeeIds()));

        return mapToDTO( scheduleRepository.save(schedule));
    }

    private ScheduleDTO mapToDTO(Schedule schedule) {

        ScheduleDTO dto = new ScheduleDTO();
        dto.setActivities(schedule.getActivities());
        dto.setId(schedule.getId());
        dto.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        dto.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        dto.setDate(schedule.getDate());

        return dto;

    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {

        log.info("get All Schedules ");

        return scheduleRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleByPet(long petId) {

        log.info("getScheduleForPet ");

        return scheduleRepository.getAllSchedulesByPetID(petId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleByEmployee(long employeeId) {
        log.info("getScheduleForPet ");

        return scheduleRepository.getAllSchedulesByEmployeeID(employeeId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleByCustomer(long customerId) {
        log.info("getScheduleByCustomer ");

        return scheduleRepository.getAllSchedulesByCustomerID(customerId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
