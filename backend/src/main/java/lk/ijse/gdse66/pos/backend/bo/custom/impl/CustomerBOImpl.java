package lk.ijse.gdse66.pos.backend.bo.custom.impl;

import lk.ijse.gdse66.pos.backend.bo.custom.CustomerBO;
import lk.ijse.gdse66.pos.backend.dao.DAOFactory;
import lk.ijse.gdse66.pos.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse66.pos.backend.dto.CustomerDTO;
import lk.ijse.gdse66.pos.backend.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);


    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCusID(), customerDTO.getCusName(),
                customerDTO.getPhNo(), customerDTO.getAddress(), customerDTO.getBirthday());
        return customerDAO.save(customer);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCusID(), customerDTO.getCusName(),
                customerDTO.getPhNo(), customerDTO.getAddress(), customerDTO.getBirthday());

        return customerDAO.update( customer);
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDAO.delete(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customerList = customerDAO.getAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO;

        for (Customer customer : customerList) {
            customerDTO = new CustomerDTO(customer.getCusId(), customer.getCusName(), customer.getPhNo(), customer.getAddress(), customer.getBirthday());
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO getCustomerByID(String id) {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getCusId(), customer.getCusName(), customer.getPhNo(), customer.getAddress(), customer.getBirthday());
    }

    @Override
    public List<String> getCustomerIDList() {
        return customerDAO.getIDList();
    }
}
