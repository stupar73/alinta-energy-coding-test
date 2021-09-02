package au.com.alintaenergy.customercrud.model;

public class ErrorResponse {
    private Long code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
