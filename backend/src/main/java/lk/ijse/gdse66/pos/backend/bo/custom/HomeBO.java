package lk.ijse.gdse66.pos.backend.bo.custom;

import lk.ijse.gdse66.pos.backend.bo.SuperBO;


public interface HomeBO extends SuperBO {
    int getCustomerCount();
    int getItemCount();
    int getOrderCount();
}
