package au.com.alintaenergy.customercrud.business;

import au.com.alintaenergy.customercrud.dao.CustomerDAO;
import au.com.alintaenergy.customercrud.exception.CustomerNotFoundException;
import au.com.alintaenergy.customercrud.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerBusinessImpl implements CustomerBusiness {
    @Autowired
    private CustomerDAO customerDao;

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public List<Customer> findAllByAttributes(Optional<String> firstName, Optional<String> lastName) {
        if (firstName.isPresent() && lastName.isPresent()) {
            return customerDao.findAllByFirstNameAndLastNameOrderByIdAsc(firstName.get(), lastName.get());
        } else if (firstName.isPresent()) { // lastName.isEmpty()
            return customerDao.findAllByFirstNameOrderByIdAsc(firstName.get());
        } else if (lastName.isPresent()) { // firstName.isEmpty()
            return customerDao.findAllByLastNameOrderByIdAsc(lastName.get());
        } else { // List all
            return customerDao.findAllByOrderByIdAsc();
        }
    }

    @Override
    public Customer create(Customer newCustomer) {
        return customerDao.save(newCustomer);
    }

    @Override
    public Customer updateOrCreate(Long id, Customer updatedCustomer) {
        return customerDao.findById(id)
                .map(employee -> {
                    employee.setFirstName(updatedCustomer.getFirstName());
                    employee.setLastName(updatedCustomer.getLastName());
                    employee.setDateOfBirth(updatedCustomer.getDateOfBirth());
                    return customerDao.save(employee);
                })
                .orElseGet(() -> {
                    updatedCustomer.setId(id);
                    return customerDao.save(updatedCustomer);
                });
    }

    @Override
    public void delete(Long id) {
        if (!customerDao.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerDao.deleteById(id);
    }
}
