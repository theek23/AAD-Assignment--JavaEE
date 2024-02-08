package lk.ijse.gdse66.pos.backend.dao;

import lk.ijse.gdse66.pos.backend.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMERDAO, ITEMDAO, ORDERDAO, ORDERDETAILDAO, QUERYDAO
    }

    public <T extends SuperDAO> T getDAO (DAOFactory.DAOTypes daoTypes){

        switch (daoTypes){
            case CUSTOMERDAO:
                return (T) new CustomerDAOImpl();
            case ITEMDAO:
                return (T) new ItemDAOImpl();
            case ORDERDAO:
                return (T) new OrderDAOImpl();
            case ORDERDETAILDAO:
                return (T) new OrderDetailDAOImpl();
            case QUERYDAO:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
