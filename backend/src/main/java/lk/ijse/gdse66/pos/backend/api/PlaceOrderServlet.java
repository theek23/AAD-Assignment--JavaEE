package lk.ijse.gdse66.pos.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos.backend.bo.BOFactory;
import lk.ijse.gdse66.pos.backend.bo.custom.HomeBO;
import lk.ijse.gdse66.pos.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse66.pos.backend.dto.OrderDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/order")
public class PlaceOrderServlet extends HttpServlet {
    PlaceOrderBO placeOrderBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDERBO);
    HomeBO dashboardBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HOME);
    List<String> orderIDList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        String option = req.getParameter("option");

        switch (option){
            case "SEARCH" :
                getOrderDetailsByID(req, resp);
                break;

            case "ID" :
                orderIDList = getOrderIDs();
                Jsonb jsonb = JsonbBuilder.create();
                try {
                    jsonb.toJson(orderIDList, resp.getWriter());
                } catch (IOException e) {
                    sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
                }
                break;

            case "COUNT":
                getOrderCount(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        OrderDTO orderDTO;
        try {
            orderDTO = JsonbBuilder.create().fromJson(req.getReader(), OrderDTO.class);

            if(orderIDList.contains(orderDTO.getOrderId())){
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order ID already saved!");
                return;
            }

            boolean isOrderSaved = placeOrderBO.saveOrder( orderDTO);

            if (isOrderSaved) {
                sendServerMsg(resp, HttpServletResponse.SC_OK, "Order Saved Successfully!");
            }else {
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order Save Failed!");
            }
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        OrderDTO orderDTO;
        try {
            orderDTO = JsonbBuilder.create().fromJson(req.getReader(), OrderDTO.class);

            if(!orderIDList.contains(orderDTO.getOrderId())){
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order ID not saved!");
                return;
            }

            boolean isOrderSaved = placeOrderBO.updateOrder(orderDTO);

            if (isOrderSaved) {
                sendServerMsg(resp, HttpServletResponse.SC_OK, "Order Updated Successfully!");
            }else {
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order Update Failed!");
            }
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }



    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String orderID = req.getParameter("ID");

        if(!orderIDList.contains(orderID)){
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order ID not saved!");
            return;
        }

        boolean isDeleted = placeOrderBO.deleteOrder(orderID);

        if(isDeleted){
            sendServerMsg(resp, HttpServletResponse.SC_OK, "Order Deleted Successfully!");
        }else {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Order Delete Failed!");
        }
    }

    private void getOrderDetailsByID(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");

        OrderDTO order = placeOrderBO.getOrderByID( id);

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(order, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

    private List<String> getOrderIDs() {
        return placeOrderBO.getOrderIDList();
    }

    private void getOrderCount(HttpServletResponse resp){
        try {
            int count = dashboardBO.getOrderCount();

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(count, resp.getWriter());

        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    private void sendServerMsg(HttpServletResponse resp, int status, String msg) {
        resp.setStatus(status);
        try {
            resp.getWriter().println(msg);
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
