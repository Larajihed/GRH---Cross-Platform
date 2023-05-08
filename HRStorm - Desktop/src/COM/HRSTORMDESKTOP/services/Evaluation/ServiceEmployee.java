/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.services.Evaluation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import COM.HRSTORMDESKTOP.models.Evaluation.Employee;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
/**
 *
 * @author conta
 */
public class ServiceEmployee implements IService<Employee>  {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @Override
    public void insertOne(Employee employee) throws SQLException {
       
    }

    @Override
    public void updateOne(Employee employee) throws SQLException {
        String req = "UPDATE `employee` SET `nom` = ?, `prenom` = ?, `email` = ?, `nomsociete` = ?, `isVerified` = ?, `password` = ?, `roles` = ? WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, employee.getNom());
        ps.setString(2, employee.getPrenom());
        ps.setString(3, employee.getEmail());
        ps.setString(4, employee.getNomsociete());
        ps.setBoolean(5, employee.getVerified());
        ps.setString(6, employee.getPassword());
        ps.setString(7, employee.getRoles());
        ps.setInt(8, employee.getId());
        ps.executeUpdate();
        System.out.println("Employee updated: " + employee);
    }

    @Override
    public void deleteOne(Employee employee) throws SQLException {
        String req = "DELETE FROM `employee` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, employee.getId());
        ps.executeUpdate();
        System.out.println("Employee deleted: " + employee);
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `employee` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Employee deleted with id = " + id);
    }

    @Override
    public List<Employee> selectAll() throws SQLException {

        List<Employee> employees = new ArrayList<>();
        String req = "SELECT * FROM `user`";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        

        while (rs.next()) {

            Employee employee = new Employee();

            employee.setId(rs.getInt("id"));
            employee.setNom(rs.getString("nom"));
            employee.setPrenom(rs.getString("prenom"));
            employee.setEmail(rs.getString("email"));
            employee.setNomsociete(rs.getString("nomsociete"));
            employee.setVerified(rs.getBoolean("is_verified"));
            employee.setPassword(rs.getString("password"));

        employee.setRoles(rs.getString("roles"));

        employees.add(employee);
    }
    return employees;
}
    
    public Employee selectOne(int id) throws SQLException {
    String req = "SELECT * FROM `user` WHERE `id` = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setNom(rs.getString("nom"));
        employee.setPrenom(rs.getString("prenom"));
        employee.setEmail(rs.getString("email"));
        employee.setNomsociete(rs.getString("nomsociete"));
        employee.setVerified(rs.getBoolean("is_verified"));
        employee.setPassword(rs.getString("password"));
        employee.setRoles(rs.getString("roles"));
        return employee;
    }
    return null;
}

    
}
