package lk.ijse.gdse66.pos.backend.dao.custom.impl;

import lk.ijse.gdse66.pos.backend.config.SessionFactoryConfig;
import lk.ijse.gdse66.pos.backend.dao.custom.OrderDAO;
import lk.ijse.gdse66.pos.backend.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    Session session;

    @Override
    public boolean save(Order order) {
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(order);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }

    }

    @Override
    public boolean update(Order order) {

        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(order);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }

    }

    @Override
    public boolean delete(String id) {
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Order order = session.get(Order.class, id);
            session.delete(order);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }

    }

    @Override
    public Order search(String id) {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public int count() {

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            Long countResult = (Long) session.createQuery("SELECT COUNT(o.orderId) FROM Order o").getSingleResult();
            return countResult.intValue();
        }
    }

    @Override
    public List<Order> getAll() {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public List<String> getIDList() {

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            return   session.createQuery("SELECT o.orderId FROM Order o", String.class).list();
        }
    }
}
