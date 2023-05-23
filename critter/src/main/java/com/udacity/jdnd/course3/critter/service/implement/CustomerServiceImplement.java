package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImplement implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImplement.class);

    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    public CustomerServiceImplement(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.info("save customer {}", customerDTO.toString());

        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customerRepository.save(customer);


        return mapToDTO(customerRepository.save(customer));
    }

    private CustomerDTO mapToDTO(Customer customer) {
       List<Pet> pets = petRepository.findByCustomerId(customer.getId());
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPetIds(pets != null ? pets.stream().map(Pet::getId).collect(Collectors.toList()) : null);
        dto.setNotes(customer.getNotes());

        return dto;

    }

    @Override
    public List<CustomerDTO> getAll() {
        log.info("get call customer");
        return customerRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findByPetId(long petId) {

        log.info("find by pet id {}", petId);


        return mapToDTO(customerRepository.findByPetId(petId));

    }

}
