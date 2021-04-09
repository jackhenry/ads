package github.jackhenry.exception.model;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FailedAuthException extends Exception implements ExceptionMapper<FailedAuthException> {
    private static final long serialVersionUID = 1L;

    public FailedAuthException() {
        super("Authentication Error.");
    }

    public FailedAuthException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(FailedAuthException exception) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

}
