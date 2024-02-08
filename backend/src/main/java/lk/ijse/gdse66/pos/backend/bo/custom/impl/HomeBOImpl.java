package lk.ijse.gdse66.pos.backend.bo.custom.impl;

import lk.ijse.gdse66.pos.backend.bo.custom.HomeBO;
import lk.ijse.gdse66.pos.backend.dao.DAOFactory;
import lk.ijse.gdse66.pos.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse66.pos.backend.dao.custom.ItemDAO;
import lk.ijse.gdse66.pos.backend.dao.custom.OrderDAO;

public class HomeBOImpl implements HomeBO {
    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);

    @Override
    public int getCustomerCount() {
        return customerDAO.count();
    }

    @Override
    public int getItemCount(){
        return itemDAO.count();
    }

    @Override
    public int getOrderCount(){
        return orderDAO.count();
    }
}
