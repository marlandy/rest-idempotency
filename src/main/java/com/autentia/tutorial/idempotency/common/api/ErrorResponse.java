package com.autentia.tutorial.idempotency.common.api;

import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marlandy
 */
@XmlRootElement(name = "error")
public class ErrorResponse {

    private String cause;

    public ErrorResponse() {
        this.cause = "Error!!!";
    }

    public ErrorResponse(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.cause);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ErrorResponse other = (ErrorResponse) obj;
        if (!Objects.equals(this.cause, other.cause)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" + "cause=" + cause + '}';
    }

}
