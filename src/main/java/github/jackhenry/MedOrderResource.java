package github.jackhenry;

import java.util.List;
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
import github.jackhenry.db.MedOrderAccess;
import github.jackhenry.db.StockAccess;
import github.jackhenry.dto.AddStockDTO;
import github.jackhenry.dto.CreateMedicationOrderDTO;
import github.jackhenry.dto.UpdateMedicationOrderDTO;
import github.jackhenry.dto.UpdateStockDTO;
import github.jackhenry.model.MedicationOrder;
import github.jackhenry.model.Stock;

@Path("mo")
public class MedOrderResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("_start") String startStr, @QueryParam("_end") String endStr,
            @QueryParam("_order") String orderStr, @QueryParam("_sort") String sortKeyStr) {
        MedOrderAccess access = MedOrderAccess.instance();
        // Convert query parame
        String start = Util.getValueOrDefault(startStr, "0");
        String end = Util.getValueOrDefault(endStr, "10");
        String order = Util.getValueOrDefault(orderStr, "ASC");
        String sortKey = Util.getValueOrDefault(sortKeyStr, "order_id");

        List<Stock> medOrderList = access.getGetMedOrders(start, end, order, sortKey);

        return Response.status(200).entity(medOrderList)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", access.getNumberOfMedOrders()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedOrderById(@PathParam("id") final String id) {
        MedicationOrder order = MedOrderAccess.instance().getMedOrderById(id);
        if (order == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(order).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMedOrder(final CreateMedicationOrderDTO dto) {
        MedicationOrder created = MedOrderAccess.instance().createMedOrder(dto);
        if (created == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMedOrder(@PathParam("id") final String id,
            final UpdateMedicationOrderDTO dto) {
        MedicationOrder updatedMedOrder = MedOrderAccess.instance().updateMedOrder(dto);
        if (updatedMedOrder == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(updatedMedOrder).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteMedOrder(@PathParam("id") final String id) {
        MedicationOrder deletedMedOrder = MedOrderAccess.instance().deleteMedOrder(id);

        if (deletedMedOrder == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(deletedMedOrder).build();
    }
}
