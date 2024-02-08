package lk.ijse.gdse66.pos.backend.bo.custom;

import lk.ijse.gdse66.pos.backend.bo.SuperBO;
import lk.ijse.gdse66.pos.backend.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem( ItemDTO itemDTO);
    boolean updateItem(ItemDTO itemDTO);
    boolean deleteItem(String code);
    List<ItemDTO> getAllItems();
    ItemDTO getItemByCode(String code);
    List<String> getItemIDList();
}
