package github.jackhenry;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import github.jackhenry.db.PatientAccess;
import github.jackhenry.dto.CreatePatientDTO;
import github.jackhenry.dto.UpdatePatientDTO;
import github.jackhenry.model.Patient;

@Path("patient")
public class PatientResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("_start") String startStr, @QueryParam("_end") String endStr,
            @QueryParam("_order") String orderStr, @QueryParam("_sort") String sortKeyStr) {
        PatientAccess access = PatientAccess.instance();
        // Convert query parame
        String start = Util.getValueOrDefault(startStr, "0");
        String end = Util.getValueOrDefault(endStr, "10");
        String order = Util.getValueOrDefault(orderStr, "ASC");
        String sortKey = Util.getValueOrDefault(sortKeyStr, "employee_id");

        List<Patient> patients = access.getPatientList(start, end, order, sortKey);

        return Response.status(200).entity(patients)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", access.getTotalNumberOfPatients()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") final String id) {
        Patient patient = PatientAccess.instance().getPatientById(id);
        if (patient == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(patient).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(final CreatePatientDTO dto) {
        Patient created = PatientAccess.instance().createPatient(dto);
        if (created == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePatient(@PathParam("id") final String id, final UpdatePatientDTO dto) {
        Patient updatedPatient = PatientAccess.instance().updatePatient(dto);
        if (updatedPatient == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(updatedPatient).build();
    }

    @PUT
    @Path("/{id}/discharge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dischargePatient(@PathParam("id") final String id) {
        Patient patient = PatientAccess.instance().dischargePatient(id);
        if (patient == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(patient).build();
    }
}
