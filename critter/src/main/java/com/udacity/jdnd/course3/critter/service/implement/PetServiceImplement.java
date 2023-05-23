package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImplement implements PetService {

    private static final Logger log = LoggerFactory.getLogger(PetServiceImplement.class);

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetServiceImplement(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        log.info("save pet {}", petDTO.toString());

        //check customer
        Customer customer = customerRepository.getOne(petDTO.getOwnerId());

        Pet pet = new Pet();

        pet.setBirthDate(petDTO.getBirthDate());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setCustomer(customer);
        pet.setType(petDTO.getType());


        return mapToDTO(petRepository.save(pet));


    }

    private PetDTO mapToDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setNotes(pet.getNotes());
        dto.setType(pet.getType());
        dto.setOwnerId(pet.getCustomer().getId());
        dto.setBirthDate(pet.getBirthDate());
        return dto;
    }

    @Override
    public PetDTO getPet(long petId) {
        log.info("get pet {}", petId);

        Pet pet = petRepository.getOne(petId);


        return mapToDTO(pet);
    }

    @Override
    public List<PetDTO> getPets() {
        log.info("get all pets ");

        List<Pet> pets = petRepository.findAll();

        return pets.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
        log.info("get  pets by owner id {}", ownerId);

        List<Pet> pets = petRepository.findByCustomerId(ownerId);

        return pets.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
