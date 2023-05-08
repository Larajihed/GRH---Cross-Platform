/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package COM.HRSTORMDESKTOP.services.conge;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Marwen
 */
public interface IService<T> {
    
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    void deleteOne(int id) throws SQLException;
    
    List<T> selectAll() throws SQLException;
    
    
}
