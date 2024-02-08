package lk.ijse.gdse66.pos.backend.dao.custom;

import lk.ijse.gdse66.pos.backend.dao.CrudDAO;
import lk.ijse.gdse66.pos.backend.entity.Item;

import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    @Override
    boolean save(Item dto);

    @Override
    boolean update(Item dto);

    @Override
    boolean delete(String id);

    @Override
    Item search(String id);

    @Override
    int count();

    @Override
    List<Item> getAll();

    @Override
    List<String> getIDList();
}
