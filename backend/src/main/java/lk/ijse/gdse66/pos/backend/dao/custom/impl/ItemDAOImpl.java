package lk.ijse.gdse66.pos.backend.dao.custom.impl;

import lk.ijse.gdse66.pos.backend.config.SessionFactoryConfig;
import lk.ijse.gdse66.pos.backend.dao.custom.ItemDAO;
import lk.ijse.gdse66.pos.backend.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    Session session;

    @Override
    public boolean save(Item item){
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(item);
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
    public boolean update(Item item){

        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(item);
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
            Item item = session.get(Item.class, id);
            session.delete(item);
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
    public Item search(String id) {

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            return session.get(Item.class, id);
        }
    }

    @Override
    public int count() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Long countResult = (Long) session.createQuery("SELECT count (i.itemCode) FROM Item i").getSingleResult();
            return countResult.intValue();
        }
    }

    @Override
    public List<Item> getAll(){

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            return session.createQuery("SELECT i FROM Item i", Item.class).list();
        }
    }

    @Override
    public List<String> getIDList(){

        try (Session session = SessionFactoryConfig.getInstance().getSession()){
            return session.createQuery("SELECT i.itemCode FROM Item i", String.class).list();
        }
    }

}
