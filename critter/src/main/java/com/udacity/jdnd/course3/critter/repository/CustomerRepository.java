package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(
            "SELECT c from Customer c" +
                    " INNER JOIN Pet p on c.id = p.customer.id" +
                    " WHERE p.id = :petId"
    )
    Customer findByPetId(@Param("petId")long petId);
}
