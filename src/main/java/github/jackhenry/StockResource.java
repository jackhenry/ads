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
import github.jackhenry.db.StockAccess;
import github.jackhenry.dto.AddStockDTO;
import github.jackhenry.dto.UpdateStockDTO;
import github.jackhenry.model.Stock;

@Path("stock")
public class StockResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("_start") String startStr, @QueryParam("_end") String endStr,
            @QueryParam("_order") String orderStr, @QueryParam("_sort") String sortKeyStr) {
        StockAccess access = StockAccess.instance();
        // Convert query parame
        String start = Util.getValueOrDefault(startStr, "0");
        String end = Util.getValueOrDefault(endStr, "10");
        String order = Util.getValueOrDefault(orderStr, "ASC");
        String sortKey = Util.getValueOrDefault(sortKeyStr, "drug_id");

        List<Stock> stockList = access.getStockItemList(start, end, order, sortKey);

        return Response.status(200).entity(stockList)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", access.getNumberOfStockItems()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStockItemById(@PathParam("id") final String id) {
        Stock stock = StockAccess.instance().getStockItemById(id);
        if (stock == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(stock).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(final AddStockDTO dto) {
        Stock created = StockAccess.instance().createStockItem(dto);
        if (created == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePatient(@PathParam("id") final String id, final UpdateStockDTO dto) {
        Stock updatedStockItem = StockAccess.instance().updateStockItem(dto);
        if (updatedStockItem == null) {
            return Response.status(500).build();
        }

        return Response.status(200).entity(updatedStockItem).build();
    }
}
