package github.jackhenry;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import github.jackhenry.db.AuthAccess;
import github.jackhenry.dto.GetPermissionDTO;
import github.jackhenry.dto.LoginDTO;
import github.jackhenry.dto.LogoutDTO;
import github.jackhenry.exception.EntityNotFoundException;
import github.jackhenry.exception.model.FailedAuthException;
import github.jackhenry.model.Account;
import github.jackhenry.model.Role;
import github.jackhenry.model.Token;

@Path("auth")
public class AuthResource {
    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(final LoginDTO dto) throws FailedAuthException {
        String username = dto.getUsername();
        String password = dto.getPassword();
        Token token = AuthAccess.instance().login(username, password);

        if (token == null) {
            return Response.status(401).build();
        }

        return Response.status(200).entity(token).build();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(final LogoutDTO dto) {
        String token = dto.getToken();
        AuthAccess.instance().logout(token);
        return Response.status(200).build();
    }

    @POST
    @Path("/permission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRole(final GetPermissionDTO dto) {
        String token = dto.getToken();
        String role = AuthAccess.instance().getTokenRole(token);
        return Response.status(200).entity(new Role(role)).build();
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountInfo(@PathParam("id") final int id) throws EntityNotFoundException {
        Account account = AuthAccess.instance().getAccount(id);
        if (account == null) {
            throw new EntityNotFoundException();
        }
        return Response.status(200).entity(account).build();
    }

}
