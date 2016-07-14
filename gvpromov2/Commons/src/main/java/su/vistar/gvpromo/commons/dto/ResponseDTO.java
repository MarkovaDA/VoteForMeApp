
package su.vistar.gvpromo.commons.dto;

public class ResponseDTO<T> {
    private boolean ok;
    private String errorMessage;
    private T object;

    public ResponseDTO() {
        this.ok = true;
    }

    public ResponseDTO(T object) {
        this.object = object;
        this.ok = true;
    }

    public ResponseDTO(String errorMessage) {
        this.ok = false;
        this.errorMessage = errorMessage;
    }
    
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
