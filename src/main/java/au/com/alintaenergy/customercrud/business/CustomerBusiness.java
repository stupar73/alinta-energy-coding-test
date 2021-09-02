package au.com.alintaenergy.customercrud.business;

import au.com.alintaenergy.customercrud.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerBusiness {
    Customer findById(Long id);
    List<Customer> findAllByAttributes(Optional<String> firstName, Optional<String> lastName);
    Customer create(Customer newCustomer);
    Customer updateOrCreate(Long id, Customer updatedCustomer);
    void delete(Long id);
}
