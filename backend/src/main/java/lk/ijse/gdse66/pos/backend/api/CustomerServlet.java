package lk.ijse.gdse66.pos.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos.backend.bo.BOFactory;
import lk.ijse.gdse66.pos.backend.bo.custom.CustomerBO;
import lk.ijse.gdse66.pos.backend.bo.custom.HomeBO;
import lk.ijse.gdse66.pos.backend.dto.CustomerDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);
    HomeBO dashboardBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HOME);
    List<String> cusIDList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String option = req.getParameter("option");

        switch (option) {
            case "GET": {
                getAllCustomer(resp);
                break;
            }
            case "SEARCH": {
                getCustomerById(req, resp);
                break;
            }
            case "ID":
                cusIDList = getCusIDList();
                Jsonb jsonb = JsonbBuilder.create();
                try {
                    jsonb.toJson(cusIDList, resp.getWriter());
                } catch (IOException e) {
                    sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
                }
                break;

            case "COUNT":
                getCustomerCount(resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        /*String id = customer.getCusID();
        String name = customer.getCusName();
        int phNO = customer.getPhNo();
        String address = customer.getAddress();
        String birthday = customer.getBirthday();*/
//        if (validation(id, resp, name, phNO, address, birthday)) return;

        /*if(cusIDList.contains(id)){
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "ID already added!");
            return;
        }*/

        boolean isSaved = customerBO.saveCustomer(customer);

        if(isSaved){
            sendServerMsg(resp, HttpServletResponse.SC_OK, "Customer Saved Successfully!");
        }else {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Customer Save Failed!");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)  {

        CustomerDTO customer;
        try {
            customer = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);
            String id = customer.getCusID();
            String name = customer.getCusName();
            int phNO = customer.getPhNo();
            String address = customer.getAddress();
            String birthday = customer.getBirthday();

            if (validation(id, resp, name, phNO, address, birthday)) return;

            if(!cusIDList.contains(id)){
                sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "ID not saved!");
                return;
            }

            boolean isUpdated = customerBO.updateCustomer( customer);

            if(isUpdated)
                sendServerMsg(resp,HttpServletResponse.SC_OK,"Customer Updated Successfully!");
            else
                sendServerMsg(resp,HttpServletResponse.SC_BAD_REQUEST,"Customer Update Failed!");
        } catch (IOException ex) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String cusID = req.getParameter("cusID");

        if(!cusIDList.contains(cusID)){
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "ID not saved!");
            return;
        }

        boolean isDeleted = customerBO.deleteCustomer( cusID);

        if(isDeleted){
            sendServerMsg(resp, HttpServletResponse.SC_OK, "Customer Deleted Successfully!");
        }else {
            sendServerMsg(resp, HttpServletResponse.SC_BAD_REQUEST, "Customer Delete Failed!");
        }
    }

    private void getCustomerCount(HttpServletResponse resp) {
        int count = dashboardBO.getCustomerCount();

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(count, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    private List<String> getCusIDList() {
        List<String> customerIDList;

        customerIDList = customerBO.getCustomerIDList();
        return customerIDList;
    }

    private void getCustomerById(HttpServletRequest req, HttpServletResponse resp) {
        String cusID = req.getParameter("cusID");
        CustomerDTO customer;

        customer = customerBO.getCustomerByID( cusID);

        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(customer, resp.getWriter());
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    private void getAllCustomer(HttpServletResponse resp) {
        List<CustomerDTO> customerList = customerBO.getAllCustomer();
        System.out.println(customerList);
        Jsonb jsonb = JsonbBuilder.create();
        try {
            jsonb.toJson(customerList, resp.getWriter());
        } catch (IOException e) {
            System.out.println("Methanata Enne");
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
    private void sendServerMsg(HttpServletResponse resp, int status, String msg){
        resp.setStatus(status);
        try {
            resp.getWriter().println(msg);
        } catch (IOException e) {
            sendServerMsg(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
    private boolean validation(String id, HttpServletResponse resp, String name, int phNO, String address, String birthday) {
        return false;
    }
}
