package lk.ijse.gdse66.pos.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String cusID;
    private String cusName;
    private Integer phNo;
    private String address;
    private String birthday;
}
