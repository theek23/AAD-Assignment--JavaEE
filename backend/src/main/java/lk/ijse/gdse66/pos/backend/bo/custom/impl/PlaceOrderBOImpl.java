package lk.ijse.gdse66.pos.backend.bo.custom.impl;

import lk.ijse.gdse66.pos.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse66.pos.backend.config.SessionFactoryConfig;
import lk.ijse.gdse66.pos.backend.dao.DAOFactory;
import lk.ijse.gdse66.pos.backend.dao.custom.OrderDAO;
import lk.ijse.gdse66.pos.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse66.pos.backend.dao.custom.QueryDAO;
import lk.ijse.gdse66.pos.backend.dto.*;
import lk.ijse.gdse66.pos.backend.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    OrderDetailDAO orderDetailDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILDAO);
    QueryDAO queryDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public OrderDTO getOrderByID(String id) {
        Order order = queryDAO.getOrderDetail(id);
        List<OrderDetailDTO> orderDTOS = new ArrayList<>();

        for (OrderDetails details: order.getOrderDetails()) {
            OrderDetailDTO detailDTO = new OrderDetailDTO(details.getOrders().getOrderId(), details.getItem().getItemId(),
                    details.getQty(), details.getPrice());
            orderDTOS.add(detailDTO);
        }
        return new OrderDTO(order.getOrderId(), order.getOrderDate(), order.getCustomer().getCusId(), orderDTOS);

    }

    @Override
    public List<String> getOrderIDList(){

        return orderDAO.getIDList();
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO)  {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Customer customer = new Customer(orderDTO.getCustomerId());
            Order orderEntity = new Order(orderDTO.getOrderId(), orderDTO.getOrderDate(), customer);
            boolean isOrderAdded = orderDAO.save(orderEntity);

            if (isOrderAdded){
                List<OrderDetails> orderDetailsList = new ArrayList<>();

                for (OrderDetailDTO orderDetails : orderDTO.getOrderDetails()) {
                    Item item = new Item(orderDetails.getItemId());
                    Order order = new Order(orderDetails.getOrderId());
                    OrderDetails orderDetailsEntity = new OrderDetails( orderDetails.getQty(),
                            orderDetails.getPrice(), item, order);
                    orderDetailsList.add(orderDetailsEntity);
                }
                boolean isOrderDetailSaved =  orderDetailDAO.save(orderDetailsList);

                if (isOrderDetailSaved){
                    transaction.commit();
                    return true;
                }
            }
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateOrder( OrderDTO orderDTO){
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Customer customer = new Customer(orderDTO.getCustomerId());
            Order orderEntity = new Order(orderDTO.getOrderId(), orderDTO.getOrderDate(), customer);
            System.out.println("order "+orderEntity);
            boolean isOrderUpdated = orderDAO.update(orderEntity);

            if (isOrderUpdated){
                List<OrderDetails> orderDetailsList = new ArrayList<>();

                for (OrderDetailDTO orderDetails : orderDTO.getOrderDetails()) {
                    Item item = new Item(orderDetails.getItemId());
                    Order order = new Order(orderDetails.getOrderId());

                    OrderDetails orderDetailsEntity = new OrderDetails( orderDetails.getQty(),
                            orderDetails.getPrice(), item, order);
                    orderDetailsList.add(orderDetailsEntity);
                }
                System.out.println("orderDetailsList "+orderDetailsList);
                boolean isOrderDetailUpdated =  orderDetailDAO.update(orderDetailsList);

                if (isOrderDetailUpdated){
                    transaction.commit();
                    return true;
                }
            }
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
        return false;
    }


    @Override
    public boolean deleteOrder(String id){
        return  orderDAO.delete( id);
    }
}
