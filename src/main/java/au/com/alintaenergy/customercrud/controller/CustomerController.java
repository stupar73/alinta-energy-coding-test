package au.com.alintaenergy.customercrud.controller;

import au.com.alintaenergy.customercrud.business.CustomerBusiness;
import au.com.alintaenergy.customercrud.constant.OpenAPIConstants;
import au.com.alintaenergy.customercrud.model.Customer;
import au.com.alintaenergy.customercrud.model.ErrorResponse;
import au.com.alintaenergy.customercrud.model.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Customers")
@RestController
@RequestMapping("/customers")
public class CustomerController extends BaseController {

    @Autowired
    CustomerBusiness customerBusiness;

    @Operation(summary = "Get a customer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_RESPONSE)})
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_NOT_FOUND_ERROR_RESPONSE)})
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerBusiness.findById(id));
    }

    @Operation(summary = "Find customers based on attributes")
    @ApiResponse(responseCode = "200", description = "Customer(s) found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Customer.class)),
                    examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_ARRAY_RESPONSE)})
    })
    @GetMapping
    public ResponseEntity<List<Customer>> findAllByAttributes(@RequestParam("firstName") Optional<String> firstName,
            @RequestParam("lastName") Optional<String> lastName) {
        return ResponseEntity.ok(customerBusiness.findAllByAttributes(firstName, lastName));
    }

    @Operation(summary = "Create a customer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = {@Content(examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_REQUEST)})})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_RESPONSE)})
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_VALIDATION_VIOLATION_ERROR_RESPONSE)})
            })
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer newCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBusiness.create(newCustomer));
    }

    @Operation(summary = "Update a customer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = {@Content(examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_REQUEST)})})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_RESPONSE)})
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_VALIDATION_VIOLATION_ERROR_RESPONSE)})
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody Customer updatedCustomer) {
        return ResponseEntity.ok(customerBusiness.updateById(id, updatedCustomer));
    }

    @Operation(summary = "Delete a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(OpenAPIConstants.EXAMPLE_CUSTOMER_NOT_FOUND_ERROR_RESPONSE)})
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        customerBusiness.delete(id);
        return ResponseEntity.noContent().build();
    }
}
