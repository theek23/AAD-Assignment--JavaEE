package lk.ijse.gdse66.pos.backend.dao.custom;

import lk.ijse.gdse66.pos.backend.dao.CrudDAO;
import lk.ijse.gdse66.pos.backend.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    @Override
    boolean save(Customer dto);

    @Override
    boolean update(Customer dto);

    @Override
    boolean delete(String id);

    @Override
    Customer search(String id);

    @Override
    List<Customer> getAll();

    @Override
    List<String> getIDList();
}
