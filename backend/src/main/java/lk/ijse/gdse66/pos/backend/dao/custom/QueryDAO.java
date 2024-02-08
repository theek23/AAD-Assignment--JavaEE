package lk.ijse.gdse66.pos.backend.dao.custom;

import lk.ijse.gdse66.pos.backend.dao.SuperDAO;
import lk.ijse.gdse66.pos.backend.entity.Order;

public interface QueryDAO extends SuperDAO {
    Order getOrderDetail(String id);
}
