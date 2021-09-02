package au.com.alintaenergy.customercrud.controller;

import au.com.alintaenergy.customercrud.business.CustomerBusiness;
import au.com.alintaenergy.customercrud.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerBusiness.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllByAttributes(@RequestParam("firstName") Optional<String> firstName,
            @RequestParam("lastName") Optional<String> lastName) {
        return ResponseEntity.ok(customerBusiness.findAllByAttributes(firstName, lastName));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody @Valid Customer newCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBusiness.create(newCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody @Valid Customer updatedCustomer) {
        return ResponseEntity.ok(customerBusiness.updateById(id, updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        customerBusiness.delete(id);
        return ResponseEntity.noContent().build();
    }
}
