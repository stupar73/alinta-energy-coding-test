package au.com.alintaenergy.customercrud.exception;

import au.com.alintaenergy.customercrud.constant.ErrorConstants;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApiException {
    private Long id;

    public CustomerNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, ErrorConstants.ERROR_CODE_CUSTOMER_NOT_FOUND, "No customer with id '" + id +  "' was found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
