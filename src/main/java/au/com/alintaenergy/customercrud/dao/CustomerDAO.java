package au.com.alintaenergy.customercrud.dao;

import au.com.alintaenergy.customercrud.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerDAO extends CrudRepository<Customer, Long> {
    List<Customer> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Customer> findAllByFirstName(String firstName);
    List<Customer> findAllByLastName(String lastName);
}
