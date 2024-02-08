package lk.ijse.gdse66.pos.backend.bo;

import lk.ijse.gdse66.pos.backend.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMERBO, ITEMBO, PLACEORDERBO, HOME
    }

    public <T extends SuperBO> T getBO (BOTypes boTypes){
        switch (boTypes){
            case CUSTOMERBO:
                return (T) new CustomerBOImpl();
            case ITEMBO:
                return (T) new ItemBOImpl();
            case PLACEORDERBO:
                return (T) new PlaceOrderBOImpl();
            case HOME:
                return (T) new HomeBOImpl();
            default:
                return null;
        }
    }
}
