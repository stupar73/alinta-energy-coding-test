package au.com.alintaenergy.customercrud.business;

import au.com.alintaenergy.customercrud.exception.CustomerNotFoundException;
import au.com.alintaenergy.customercrud.model.Customer;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CustomerBusiness {
    /**
     * Find a {@link Customer} by ID.
     *
     * @param id The ID to search for.
     * @return The {@link Customer}.
     * @throws CustomerNotFoundException if there is no customer with the provided {@literal id}.
     */
    Customer findById(Long id);

    /**
     * Find customers matching the input parameters.
     *
     * @param firstName First name, can be {@link Optional#empty()} to exclude this field from the filtering.
     * @param lastName Last name, can be {@link Optional#empty()} to exclude this field from the filtering.
     * @return A list of {@link Customer}s matching the input attributes.
     */
    List<Customer> findAllByAttributes(Optional<String> firstName, Optional<String> lastName);

    /**
     * Create a new {@link Customer}.
     *
     * @param newCustomer The new {@link Customer} to create.
     * @return The {@link Customer} that was created
     */
    Customer create(@Valid Customer newCustomer);

    /**
     * Update an existing {@link Customer}.
     *
     * @param id The ID of the existing {@link Customer}.
     * @return The updated {@link Customer}.
     * @throws CustomerNotFoundException if there is no customer with the provided {@literal id}.
     */
    Customer updateById(Long id, @Valid Customer updatedCustomer);

    /**
     * Delete an existing {@link Customer}.
     *
     * @param id The ID of the existing {@link Customer}.
     * @throws CustomerNotFoundException if there is no customer with the provided {@literal id}.
     */
    void delete(Long id);
}
