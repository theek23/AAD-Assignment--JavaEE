package lk.ijse.gdse66.pos.backend.dao.custom;

import lk.ijse.gdse66.pos.backend.dao.CrudDAO;
import lk.ijse.gdse66.pos.backend.dao.SuperDAO;
import lk.ijse.gdse66.pos.backend.entity.OrderDetails;

import java.util.List;

public interface OrderDetailDAO extends SuperDAO {
    boolean save( List<OrderDetails> dto);
    boolean update(List<OrderDetails> dto);
}
