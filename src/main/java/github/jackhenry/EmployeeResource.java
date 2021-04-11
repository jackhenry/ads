package github.jackhenry;

import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import github.jackhenry.db.DatabaseAccess;
import github.jackhenry.dto.CreateEmployeeDTO;
import github.jackhenry.dto.UpdateEmployeeDTO;
import github.jackhenry.exception.EntityNotFoundException;
import github.jackhenry.model.Employee;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("employee")
public class EmployeeResource {
    /**
     * Method handling HTTP GET requests. The returned object will be sent to the client as
     * "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("_start") String startStr, @QueryParam("_end") String endStr,
            @QueryParam("_order") String orderStr, @QueryParam("_sort") String sortKeyStr) {

        // Convert query parame
        String start = Util.getValueOrDefault(startStr, "0");
        String end = Util.getValueOrDefault(endStr, "100");
        String order = Util.getValueOrDefault(orderStr, "ASC");
        String sortKey = Util.getValueOrDefault(sortKeyStr, "employee_id");

        DatabaseAccess access = DatabaseAccess.instance();
        List<Employee> employees = access.getEmployees(start, end, order, sortKey);
        return Response.status(200).entity(employees)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", access.getTotalNumberOfEmployees()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") String id) throws EntityNotFoundException {
        Employee employee = DatabaseAccess.instance().getEmployeeById(id);
        if (employee == null) {
            throw new EntityNotFoundException("MESSAGE");
        }
        return Response.status(200).entity(employee).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(final CreateEmployeeDTO dto) {
        Employee created = DatabaseAccess.instance().createEmployee(dto);
        if (created == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(created).build();
    }

    @RolesAllowed("pharmatech")
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") String id, final UpdateEmployeeDTO dto) {
        Employee updated = DatabaseAccess.instance().updateEmployee(dto);
        if (updated == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(updated).build();
    }

    @RolesAllowed("pharmatech")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") final String id) {
        Employee deletedEmployee = DatabaseAccess.instance().deleteEmployee(id);
        return Response.status(200).entity(deletedEmployee).build();
    }
}
