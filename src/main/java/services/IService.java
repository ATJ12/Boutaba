package services;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    public  void ajouter(T t ) throws SQLException;
    public  void modifier(T t ,int id) throws SQLException;
    public boolean supprimer(int id );
    public List<T> afficher() throws SQLException;



}
