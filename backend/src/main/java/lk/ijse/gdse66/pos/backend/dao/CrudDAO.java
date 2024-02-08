package lk.ijse.gdse66.pos.backend.dao;
import java.util.List;


public interface CrudDAO<T> extends SuperDAO {
    boolean save( T dto) ;

    boolean update(T dto);

    boolean delete( String id) ;

    T search(String id);

    int count();

    List<T> getAll();

    List<String> getIDList();
}
