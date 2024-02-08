package lk.ijse.gdse66.pos.backend.bo.custom.impl;

import lk.ijse.gdse66.pos.backend.bo.custom.ItemBO;
import lk.ijse.gdse66.pos.backend.dao.DAOFactory;
import lk.ijse.gdse66.pos.backend.dao.custom.ItemDAO;
import lk.ijse.gdse66.pos.backend.dto.ItemDTO;
import lk.ijse.gdse66.pos.backend.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);

    @Override
    public boolean saveItem(ItemDTO itemDTO){
        Item item = new Item(itemDTO.getItemId(), itemDTO.getDescription(), itemDTO.getQtyOnHand(),itemDTO.getUnitPrice());

        return itemDAO.save(item);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO){
        Item item = new Item(itemDTO.getItemId(), itemDTO.getDescription(), itemDTO.getQtyOnHand(),itemDTO.getUnitPrice());

        return itemDAO.update(item);
    }

    @Override
    public boolean deleteItem(String code){
        return  itemDAO.delete(code);
    }

    @Override
    public List<ItemDTO> getAllItems(){
        List<Item> itemList = itemDAO.getAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO itemDTO;

        for (Item item : itemList) {
            itemDTO = new ItemDTO(item.getItemId(), item.getDescription(), item.getQtyOnHand() , item.getUnitPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public ItemDTO getItemByCode(String code){
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemId(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public List<String> getItemIDList(){
        return itemDAO.getIDList();
    }
}
