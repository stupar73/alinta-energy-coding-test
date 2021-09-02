package au.com.alintaenergy.customercrud.dao;

import au.com.alintaenergy.customercrud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer, Long> {
    List<Customer> findAllByOrderByIdAsc();
    List<Customer> findAllByFirstNameAndLastNameOrderByIdAsc(String firstName, String lastName);
    List<Customer> findAllByFirstNameOrderByIdAsc(String firstName);
    List<Customer> findAllByLastNameOrderByIdAsc(String lastName);
}
