package github.jackhenry.exception.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import github.jackhenry.exception.EntityNotFoundException;
import github.jackhenry.exception.model.ErrorModel;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        ErrorModel entity = new ErrorModel("Entity was not found.");
        return Response.status(404).entity(entity).build();
    }

}
