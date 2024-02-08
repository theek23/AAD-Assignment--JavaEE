package lk.ijse.gdse66.pos.backend.dao.custom.impl;

import lk.ijse.gdse66.pos.backend.config.SessionFactoryConfig;
import lk.ijse.gdse66.pos.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse66.pos.backend.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    Session session;

    @Override
    public boolean save(Customer customer) {
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(customer);
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
    public boolean update(Customer customer){

        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(customer);
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
    public boolean delete(String id){
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Customer customer = session.get(Customer.class, id);
            session.delete(customer);
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
    public Customer search(String id){

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            return session.get(Customer.class, id);
        }
    }

    @Override
    public int count() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            Long countResult = (Long) session.createQuery("SELECT count (c.cusID) FROM Customer c").getSingleResult();
            return countResult.intValue();
        }
    }

    @Override
    public List<Customer> getAll()  {

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            return session.createQuery("SELECT c FROM Customer c", Customer.class).list();
        }
    }

    @Override
    public List<String> getIDList() {

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            return session.createQuery("SELECT c.cusID FROM Customer c", String.class).list();
        }
    }
}
