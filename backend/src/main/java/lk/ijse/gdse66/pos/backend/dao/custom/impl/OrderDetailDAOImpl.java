package lk.ijse.gdse66.pos.backend.dao.custom.impl;

import lk.ijse.gdse66.pos.backend.config.SessionFactoryConfig;
import lk.ijse.gdse66.pos.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse66.pos.backend.entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    Session session;

    public boolean save(List<OrderDetails> orderDetailsList)  {

        int savedCount = 0;
        for (OrderDetails orderDetail : orderDetailsList) {
            session = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            try {
                session.save(orderDetail);
                transaction.commit();
                savedCount++;
            }catch (Exception e){
                transaction.rollback();
                return false;
            }finally {
                session.close();
            }
        }

        return savedCount == orderDetailsList.size();

    }

    public boolean update(List<OrderDetails> orderDetailsList)  {

        for (OrderDetails orderDetail : orderDetailsList) {
            session = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            try {
                Query query = session.createQuery("UPDATE OrderDetails o SET o.qtyOnHand = :qty, o.unitPrice = :uPrice" +
                        " WHERE o.item.itemCode = :itemCode AND o.orders.orderId = :orderID");
                query.setParameter("qty", orderDetail.getQty());
                query.setParameter("uPrice", orderDetail.getPrice());
                query.setParameter("itemCode", orderDetail.getItem().getItemId());
                query.setParameter("orderID", orderDetail.getOrders().getOrderId());
                return query.executeUpdate() > 0;

            }catch (Exception e){
                transaction.rollback();
                return false;
            }finally {
                session.close();
            }

        }
        return false;
    }
}
