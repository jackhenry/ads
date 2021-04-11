package github.jackhenry;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import github.jackhenry.db.DrugAccess;
import github.jackhenry.dto.CreateDrugDTO;
import github.jackhenry.model.Drug;

@Path("drug")
public class DrugResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("_start") String startStr, @QueryParam("_end") String endStr,
            @QueryParam("_order") String orderStr, @QueryParam("_sort") String sortKeyStr) {

        // Convert query parame
        String start = Util.getValueOrDefault(startStr, "0");
        String end = Util.getValueOrDefault(endStr, "100");
        String order = Util.getValueOrDefault(orderStr, "ASC");
        String sortKey = Util.getValueOrDefault(sortKeyStr, "drug_id");

        DrugAccess access = DrugAccess.instance();
        List<Drug> employees = access.getDrugList(start, end, order, sortKey);
        return Response.status(200).entity(employees)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", access.getTotalNumberOfDrugs()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrugById(@PathParam("id") String id) {
        Drug drug = DrugAccess.instance().getDrugById(Integer.parseInt(id));
        if (drug == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(drug).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDrug(final CreateDrugDTO dto) {
        Drug created = DrugAccess.instance().createDrug(dto);
        if (created == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(created).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") final String id) {
        Drug deletedDrug = DrugAccess.instance().deleteDrug(id);
        return Response.status(200).entity(deletedDrug).build();
    }
}
