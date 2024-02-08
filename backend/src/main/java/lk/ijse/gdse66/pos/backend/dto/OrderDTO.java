package lk.ijse.gdse66.pos.backend.dto;

import lk.ijse.gdse66.pos.backend.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String customerId;
    private List<OrderDetailDTO> orderDetails;
}
