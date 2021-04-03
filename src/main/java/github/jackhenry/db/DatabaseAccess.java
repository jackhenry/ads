package github.jackhenry.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.Util;
import github.jackhenry.dto.CreateEmployeeDTO;
import github.jackhenry.model.Employee;

public class DatabaseAccess {

   public static DatabaseAccess instance = null;

   private DatabaseAccess() {
   }

   public static DatabaseAccess instance() {
      if (instance == null) {
         instance = new DatabaseAccess();
      }

      return instance;
   }

   public List<Employee> getEmployees(String start, String end, String order, String sortKey) {
      // Convert id to employee_id to match schema
      String orderBy = sortKey.equals("id") ? "employee_id" : sortKey;
      try {
         int limit = Integer.parseInt(end) - Integer.parseInt(start);

         Statement statement = DatabaseConnection.instance().createStatement();
         String sql = "SELECT * FROM employee ORDER BY " + orderBy + " " + order + " LIMIT " + limit
               + " OFFSET " + start;
         System.out.println(sql);
         ResultSet resultSet = statement.executeQuery(sql);

         ArrayList<Employee> employeesList = new ArrayList<>();
         while (resultSet.next()) {
            employeesList.add(Employee.resultToEmployee(resultSet));
         }

         return employeesList;
      } catch (SQLException | NamingException ex) {
         return new ArrayList<Employee>();
      }
   }

   public Employee getEmployeeById(String id) {
      try {
         Statement statement = DatabaseConnection.instance().createStatement();
         String sql = "SELECT * FROM employee WHERE employee_id=" + id;
         ResultSet resultSet = statement.executeQuery(sql);
         resultSet.next();
         if (resultSet == null) {
            return null;
         }
         return Employee.resultToEmployee(resultSet);
      } catch (SQLException | NamingException ex) {
         return null;
      }
   }

   public int getTotalNumberOfEmployees() {
      try {
         Statement statement = DatabaseConnection.instance().createStatement();
         String sql = "SELECT COUNT(*) FROM employee";
         ResultSet resultSet = statement.executeQuery(sql);
         int size = 0;
         if (resultSet != null) {
            resultSet.next();
            size = resultSet.getInt("count");
         }
         return size;
      } catch (SQLException | NamingException ex) {
         ex.printStackTrace();
         return 0;
      }
   }

   public Employee createEmploye(CreateEmployeeDTO dto) {
      String firstname = dto.getFirstname();
      String lastname = dto.getLastname();

      if (firstname == null || lastname == null) {
         return null;
      }

      try {
         Statement statement = DatabaseConnection.instance().createStatement();
         String sql = "INSERT INTO Employee (firstname, lastname) VALUES ('" + firstname + "','"
               + lastname + "')";
         System.out.println(sql);
         statement.executeUpdate(sql);
         return new Employee(firstname, lastname);
      } catch (SQLException | NamingException ex) {
         ex.printStackTrace();
         return null;
      }
   }
}
