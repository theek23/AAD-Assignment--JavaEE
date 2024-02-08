package lk.ijse.gdse66.pos.backend.dao.custom;

import lk.ijse.gdse66.pos.backend.dao.CrudDAO;
import lk.ijse.gdse66.pos.backend.entity.Order;

import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
    @Override
    boolean save(Order dto);

    @Override
    boolean update(Order dto);

    @Override
    boolean delete(String id);

    @Override
    Order search(String id);

    @Override
    int count();

    @Override
    List<Order> getAll();

    @Override
    List<String> getIDList();
}
