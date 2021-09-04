package au.com.alintaenergy.customercrud.business;

import au.com.alintaenergy.customercrud.BaseTest;
import au.com.alintaenergy.customercrud.dao.CustomerDAO;
import au.com.alintaenergy.customercrud.exception.CustomerNotFoundException;
import au.com.alintaenergy.customercrud.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerBusinessTest extends BaseTest {
    @Autowired
    private CustomerDAO customerDao;
    @Autowired
    private CustomerBusiness customerBusiness;

    private Customer stuartParkerCustomer;
    private Customer stuartTestCustomer;
    private Customer testParkerCustomer;

    @BeforeEach
    void setUp() {
        this.stuartParkerCustomer = new Customer();
        this.stuartParkerCustomer.setFirstName("Stuart");
        this.stuartParkerCustomer.setLastName("Parker");
        this.stuartParkerCustomer.setDateOfBirth(LocalDate.of(1994, Month.JULY, 26));
        this.stuartParkerCustomer = customerDao.save(stuartParkerCustomer);

        this.stuartTestCustomer = new Customer();
        this.stuartTestCustomer.setFirstName("Stuart");
        this.stuartTestCustomer.setLastName("Test");
        this.stuartTestCustomer.setDateOfBirth(LocalDate.of(1994, Month.JULY, 1));
        this.stuartTestCustomer = customerDao.save(stuartTestCustomer);

        this.testParkerCustomer = new Customer();
        this.testParkerCustomer.setFirstName("Test");
        this.testParkerCustomer.setLastName("Parker");
        this.testParkerCustomer.setDateOfBirth(LocalDate.of(1900, Month.JANUARY, 1));
        this.testParkerCustomer = customerDao.save(testParkerCustomer);
    }

    @AfterEach
    void tearDown() {
        customerDao.deleteAll();
    }

    @Test
    void findById_ExistingId_Success() {
        Customer customerFromApp = customerBusiness.findById(testParkerCustomer.getId());

        assertEquals(customerFromApp, testParkerCustomer);
    }

    @Test
    void findAllByAttributes_All_Success() {
        List<Customer> customersFromApp = customerBusiness.findAllByAttributes(Optional.empty(), Optional.empty());

        assertEquals(List.of(stuartParkerCustomer, stuartTestCustomer, testParkerCustomer), customersFromApp);
    }

    @Test
    void findAllByAttributes_FirstName_Success() {
        List<Customer> customersFromApp = customerBusiness.findAllByAttributes(Optional.of("Test"), Optional.empty());

        assertEquals(List.of(testParkerCustomer), customersFromApp);
    }

    @Test
    void findAllByAttributes_LastName_Success() {
        List<Customer> customersFromApp = customerBusiness.findAllByAttributes(Optional.empty(), Optional.of("Parker"));

        assertEquals(List.of(stuartParkerCustomer, testParkerCustomer), customersFromApp);
    }

    @Test
    void findAllByAttributes_FirstNameAndLastName_Success() {
        List<Customer> customersFromApp = customerBusiness.findAllByAttributes(Optional.of("Stuart"), Optional.of("Parker"));

        assertEquals(List.of(stuartParkerCustomer), customersFromApp);
    }

    @Test
    void create_DateOfBirthInPresent_Success() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("New");
        newCustomer.setLastName("Customer");
        newCustomer.setDateOfBirth(LocalDate.now());

        Customer newCustomerFromApp = customerBusiness.create(newCustomer);
        // Update id from the response but leave other fields alone
        newCustomer.setId(newCustomerFromApp.getId());

        assertEquals(customerDao.findById(newCustomerFromApp.getId()).get(), newCustomer);
    }

    @Test
    void create_DateOfBirthInPast_Success() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("New");
        newCustomer.setLastName("Customer");
        newCustomer.setDateOfBirth(LocalDate.now().minusDays(1L));

        Customer newCustomerFromApp = customerBusiness.create(newCustomer);
        // Update id from the response but leave other fields alone
        newCustomer.setId(newCustomerFromApp.getId());

        assertEquals(customerDao.findById(newCustomerFromApp.getId()).get(), newCustomer);
    }

    @Test
    void create_ValidationError_MissingFirstName_ConstraintViolationException() {
        Customer newCustomer = new Customer();
        newCustomer.setLastName("Last");
        newCustomer.setDateOfBirth(LocalDate.now());

        assertThrowsInCausesStack(ConstraintViolationException.class, () -> customerBusiness.create(newCustomer));
    }

    @Test
    void create_ValidationError_MissingLastName_ConstraintViolationException() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("First");
        newCustomer.setDateOfBirth(LocalDate.now());

        assertThrowsInCausesStack(ConstraintViolationException.class, () -> customerBusiness.create(newCustomer));
    }

    @Test
    void create_ValidationError_MissingDateOfBirth_ConstraintViolationException() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("First");
        newCustomer.setLastName("Last");

        assertThrowsInCausesStack(ConstraintViolationException.class, () -> customerBusiness.create(newCustomer));
    }

    @Test
    void create_ValidationError_DateOfBirthInFuture_ConstraintViolationException() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("First");
        newCustomer.setLastName("Last");
        newCustomer.setDateOfBirth(LocalDate.now().plusDays(1L));

        assertThrowsInCausesStack(ConstraintViolationException.class, () -> customerBusiness.create(newCustomer));
    }

    @Test
    void updateById_ExistingId_ChangedFirstName_Success() {
        Customer updatedCustomer = new Customer(testParkerCustomer);
        updatedCustomer.setFirstName("Updated");

        customerBusiness.updateById(testParkerCustomer.getId(), updatedCustomer);

        assertEquals(customerDao.findById(testParkerCustomer.getId()).get(), updatedCustomer);
    }

    @Test
    void updateById_ExistingId_ChangedLastName_Success() {
        Customer updatedCustomer = new Customer(testParkerCustomer);
        updatedCustomer.setLastName("Updated");

        customerBusiness.updateById(testParkerCustomer.getId(), updatedCustomer);

        assertEquals(customerDao.findById(testParkerCustomer.getId()).get(), updatedCustomer);
    }

    @Test
    void updateById_ExistingId_ChangedDateOfBirthInPresent_Success() {
        Customer updatedCustomer = new Customer(testParkerCustomer);
        updatedCustomer.setDateOfBirth(LocalDate.now());

        customerBusiness.updateById(testParkerCustomer.getId(), updatedCustomer);

        assertEquals(customerDao.findById(testParkerCustomer.getId()).get(), updatedCustomer);
    }

    @Test
    void updateById_ExistingId_ChangedDateOfBirthInPast_Success() {
        Customer updatedCustomer = new Customer(testParkerCustomer);
        updatedCustomer.setDateOfBirth(LocalDate.now().minusDays(1L));

        customerBusiness.updateById(testParkerCustomer.getId(), updatedCustomer);

        assertEquals(customerDao.findById(testParkerCustomer.getId()).get(), updatedCustomer);
    }

    @Test
    void updateById_NonExistingId_CustomerNotFoundException() {
        Customer updatedCustomer = new Customer(testParkerCustomer);
        updatedCustomer.setFirstName("Updated");

        assertThrows(CustomerNotFoundException.class, () -> customerBusiness.updateById(999L, updatedCustomer));
    }

    @Test
    void delete_ExistingId_Success() {
        customerBusiness.delete(stuartTestCustomer.getId());

        assertThat(customerDao.findAll(), not(hasItem(stuartTestCustomer)));
    }
}
