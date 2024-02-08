package lk.ijse.gdse66.pos.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column (name = "description", columnDefinition = "TEXT")
    private String description;

    @Column (name = "qty_on_hand")
    private int qtyOnHand;

    @Column (name = "unit_price")
    private double unitPrice;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "item")
    private List<OrderDetails> orderDetails = new ArrayList<>();

    public Item(String itemCode, String description, int qtyOnHand, double unitPrice) {
        this.itemId = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public Item(String itemId) {
        this.itemId = itemId;
    }
}
