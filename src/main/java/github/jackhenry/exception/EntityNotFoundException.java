package github.jackhenry.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import github.jackhenry.exception.model.ErrorModel;

@Provider
public class EntityNotFoundException extends Exception
        implements ExceptionMapper<EntityNotFoundException> {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException() {
        super("ENTITY NOT FOUND.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(501).entity(new ErrorModel("test")).build();
    }
}
