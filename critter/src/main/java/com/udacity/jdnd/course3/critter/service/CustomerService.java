package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO findByPetId(long petId);


}
