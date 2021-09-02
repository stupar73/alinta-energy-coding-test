package au.com.alintaenergy.customercrud.controller;

import au.com.alintaenergy.customercrud.business.CustomerBusiness;
import au.com.alintaenergy.customercrud.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController extends BaseController {

    @Autowired
    CustomerBusiness customerBusiness;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerBusiness.findById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> findAllByAttributes(@RequestParam("firstName") Optional<String> firstName,
            @RequestParam("lastName") Optional<String> lastName) {
        return ResponseEntity.ok(customerBusiness.findAllByAttributes(firstName, lastName));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> create(@RequestBody @Valid Customer newCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBusiness.create(newCustomer));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody @Valid Customer updatedCustomer) {
            return ResponseEntity.ok(customerBusiness.updateById(id, updatedCustomer));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        customerBusiness.delete(id);
        return ResponseEntity.noContent().build();
    }
}
