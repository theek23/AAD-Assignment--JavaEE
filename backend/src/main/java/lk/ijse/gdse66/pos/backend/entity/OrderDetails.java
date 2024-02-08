package lk.ijse.gdse66.pos.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "order_detail_id")
    private int orderDetailId;

    @Column (name = "quantity")
    private int qty;

    @Column (name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "item_code", referencedColumnName = "item_code", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order orders;


    public OrderDetails( int qtyOnHand, double price, Item item, Order orders) {
        this.qty = qtyOnHand;
        this.price = price;
        this.item = item;
        this.orders = orders;
    }
}
