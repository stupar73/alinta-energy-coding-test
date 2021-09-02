package au.com.alintaenergy.customercrud.constant;

public class OpenAPIConstants {
    public static final String EXAMPLE_CUSTOMER_REQUEST = "{\"firstName\":\"Stuart\",\"lastName\":\"Parker\",\"dateOfBirth\":\"1994-07-26\"}";
    public static final String EXAMPLE_CUSTOMER_RESPONSE = "{\"id\":1,\"firstName\":\"Stuart\",\"lastName\":\"Parker\",\"dateOfBirth\":\"1994-07-26\"}";
    public static final String EXAMPLE_CUSTOMER_ARRAY_RESPONSE = "[" + EXAMPLE_CUSTOMER_RESPONSE + "]";
    public static final String EXAMPLE_CUSTOMER_NOT_FOUND_ERROR_RESPONSE = "{\"code\":40401,\"message\":\"No customer with id '1' was found\"}";
    public static final String EXAMPLE_VALIDATION_VIOLATION_ERROR_RESPONSE = "{\"code\":40001,\"message\":\"One or more request validations failed\",\"violations\":[{\"fieldName\":\"dateOfBirth\",\"message\":\"Text '1994-13-26' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 13\"}]}";
}
