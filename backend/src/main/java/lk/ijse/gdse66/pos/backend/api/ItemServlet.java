package lk.ijse.gdse66.pos.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos.backend.bo.BOFactory;
import lk.ijse.gdse66.pos.backend.bo.custom.HomeBO;
import lk.ijse.gdse66.pos.backend.bo.custom.ItemBO;
import lk.ijse.gdse66.pos.backend.dto.ItemDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    ItemBO itemBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMBO);
    HomeBO dashboardBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HOME);

    List<String> itemCodeList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        String option = req.getParameter("option");

        switch (option) {
            case "GET": {
                getAllItem(resp);
                break;
            }
            case "SEARCH": {
                getItemById(req, resp);
                break;
            }
            case "ID":
                itemCodeList = getItemIDList();
                Jsonb jsonb = JsonbBuilder.create();
                try {
                    jsonb.toJson(itemCodeList, resp.getWriter());
                } catch (IOException e) {
                    sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
                }
                break;

            case "COUNT":
                getItemCount(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        ItemDTO item;
        try {
            item = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);
            String id = item.getItemId();
            String description = item.getDescription();
            int qtyOnHand = item.getQtyOnHand();
            double unitPrice = item.getUnitPrice();

            if (validation(id, resp, description, qtyOnHand, unitPrice)) return;

            if(itemCodeList.contains(id)){
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item code already saved!");
                return;
            }

            boolean isSaved = itemBO.saveItem(item);

            if(isSaved){
                sendServerMsg(resp, HttpServletResponse.SC_OK, "Item Saved Successfully!");
            }else {
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item Save Failed!");
            }
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)  {

        ItemDTO item;
        try {
            item = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);
            String id = item.getItemId();
            String description = item.getDescription();
            int qtyOnHand = item.getQtyOnHand();
            double unitPrice = item.getUnitPrice();

            if (validation(id, resp, description, qtyOnHand, unitPrice)) return;

            if(!itemCodeList.contains(id)){
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item id not added!");
                return;
            }

            boolean isUpdated = itemBO.updateItem(item);

            if(isUpdated)
                sendServerMsg(resp,HttpServletResponse.SC_OK,"Item Updated Successfully!");
            else
                sendServerMsg(resp,HttpServletResponse.SC_BAD_REQUEST,"Item Update Failed!");
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String itemCode = req.getParameter("itemCode");

        if(!itemCodeList.contains(itemCode)){
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item code not saved!");
            return;
        }

        boolean isDeleted = itemBO.deleteItem( itemCode);

        if(isDeleted){
            sendServerMsg(resp, HttpServletResponse.SC_OK, "Item Deleted Successfully!");
        }else {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item Delete Failed!");
        }
    }

    private void getAllItem(HttpServletResponse resp) {

        List<ItemDTO> itemList = itemBO.getAllItems();

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(itemList, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }



    private void getItemById(HttpServletRequest req, HttpServletResponse resp) {
        String itemCode = req.getParameter("itemCode");

        ItemDTO item = itemBO.getItemByCode( itemCode);

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(item, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

    private List<String> getItemIDList(){

        return itemBO.getItemIDList();

    }

    private void getItemCount(HttpServletResponse resp) {

        int count = dashboardBO.getItemCount();

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(count, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

    private boolean validation(String code, HttpServletResponse resp, String description, int qty, double price) {
        if(code.equals("") || !code.matches("^(I00-)[0-9]{3}$")){
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item code is a required field : Pattern I00-000");
            return true;
        } else if (description.length() < 5 || description.length() > 50) {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item Name is a required field : Min 5, Max 20, Spaces Allowed");
            return true;
        } else if (qty==0 || !String.valueOf(qty).matches("^\\d+$")) {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item quantity is a required field : only numbers");
            return true;
        } else if (price == 0.0 || !String.valueOf(price).matches("^[0-9]*(\\.[0-9]{0,2})?$")) {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Item Price is a required field : Pattern 100.00 or 100");
            return true;
        }
        return false;
    }

    private void sendServerMsg(HttpServletResponse resp, int status, String msg)  {
        resp.setStatus(status);
        try {
            resp.getWriter().println(msg);
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
