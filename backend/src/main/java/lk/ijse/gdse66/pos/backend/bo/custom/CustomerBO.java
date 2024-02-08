package lk.ijse.gdse66.pos.backend.bo.custom;

import lk.ijse.gdse66.pos.backend.bo.SuperBO;
import lk.ijse.gdse66.pos.backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO customerDTO) ;
    boolean updateCustomer(CustomerDTO customerDTO) ;
    boolean deleteCustomer(String id) ;
    List<CustomerDTO> getAllCustomer() ;
    CustomerDTO getCustomerByID( String id) ;
    List<String> getCustomerIDList() ;
}
