package au.com.alintaenergy.customercrud.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
    List<ValidationViolation> violations;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(Long code, String message, List<ValidationViolation> validationViolations) {
        super(code, message);
        this.violations = validationViolations;
    }

    public List<ValidationViolation> getViolations() {
        return violations;
    }

    public void setViolations(List<ValidationViolation> violations) {
        this.violations = violations;
    }

    public void addViolation(ValidationViolation violation) {
        if (this.violations == null) {
            this.violations = new ArrayList<>();
        }
        this.violations.add(violation);
    }
}
