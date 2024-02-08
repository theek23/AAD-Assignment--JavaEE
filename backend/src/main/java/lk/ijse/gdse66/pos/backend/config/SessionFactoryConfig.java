package lk.ijse.gdse66.pos.backend.config;

import lk.ijse.gdse66.pos.backend.entity.Customer;
import lk.ijse.gdse66.pos.backend.entity.Item;
import lk.ijse.gdse66.pos.backend.entity.Order;
import lk.ijse.gdse66.pos.backend.entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionFactoryConfig {
    private static SessionFactoryConfig sessionFactoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfig(){
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
            properties.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/pos");

        } catch (IOException e) {
            e.printStackTrace();
        }

        sessionFactory = new Configuration().setProperties(properties)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetails.class)
                .buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance(){
        return (sessionFactoryConfig==null) ? sessionFactoryConfig = new SessionFactoryConfig() : sessionFactoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
