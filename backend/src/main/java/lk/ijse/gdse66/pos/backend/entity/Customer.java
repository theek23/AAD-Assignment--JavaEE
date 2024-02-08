package lk.ijse.gdse66.pos.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String cusId;

    @Column(name = "name")
    private String cusName;

    @Column(name = "phone_number")
    private Integer phNo;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private String birthday;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "customer")
    private List<Order> orderList = new ArrayList<>();

    public Customer(String cusId, String cusName, Integer phNo, String address, String birthday) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.phNo = phNo;
        this.address = address;
        this.birthday = birthday;
    }
    public Customer(String cusID) {
        this.cusId = cusID;
    }
}
