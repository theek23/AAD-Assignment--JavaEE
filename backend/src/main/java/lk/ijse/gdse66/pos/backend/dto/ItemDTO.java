package lk.ijse.gdse66.pos.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private String itemId;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
}
