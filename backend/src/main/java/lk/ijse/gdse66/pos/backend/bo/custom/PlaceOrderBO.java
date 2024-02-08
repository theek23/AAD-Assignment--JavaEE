package lk.ijse.gdse66.pos.backend.bo.custom;

import lk.ijse.gdse66.pos.backend.dto.OrderDTO;

import java.util.List;

public interface PlaceOrderBO {
    OrderDTO getOrderByID(String id);
    List<String> getOrderIDList();
    boolean saveOrder(OrderDTO orderDTO);
    boolean updateOrder(OrderDTO orderDTO);
    boolean deleteOrder(String id);

}
