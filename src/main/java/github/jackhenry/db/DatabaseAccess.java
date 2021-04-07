package github.jackhenry.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.dto.CreateEmployeeDTO;
import github.jackhenry.dto.UpdateEmployeeDTO;
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
         String sql = "SELECT * FROM all_employees ORDER BY " + orderBy + " " + order + " LIMIT "
               + limit + " OFFSET " + start;
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
         String sql = "SELECT * FROM all_employees WHERE employee_id=" + id;
         ResultSet resultSet = statement.executeQuery(sql);
         resultSet.next();
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

   public Employee createEmployee(CreateEmployeeDTO dto) {
      String firstname = dto.getFirstname();
      String lastname = dto.getLastname();
      String password = dto.getPassword();
      String employeeType = dto.getEmployeeType();

      if (firstname == null || lastname == null) {
         return null;
      }

      try {
         // Insert the new employee into employee table
         int employeeId = insertEmployee(firstname, lastname);
         // Create the new account
         int accountId = insertAccount(password, employeeId);
         // Create the Employee type record
         insertEmployeeType(employeeType, employeeId, accountId);
         return getEmployeeById(employeeId + "");
      } catch (SQLException | NamingException ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public int insertEmployeeType(String employeeType, int employeeId, int accountId)
         throws SQLException, NamingException {
      String sql = "INSERT INTO " + employeeType + " (employee_id, account_id) VALUES (?, ?)";
      PreparedStatement statement =
            DatabaseConnection.instance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setInt(1, employeeId);
      statement.setInt(2, accountId);
      statement.executeUpdate();
      return employeeId;
   }

   public int insertEmployee(String firstname, String lastname)
         throws SQLException, NamingException {
      String sql = "INSERT INTO Employee (firstname, lastname) VALUES (?, ?)";
      PreparedStatement statement =
            DatabaseConnection.instance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, firstname);
      statement.setString(2, lastname);
      statement.executeUpdate();
      ResultSet generatedKey = statement.getGeneratedKeys();
      generatedKey.next();
      int employeeId = generatedKey.getInt(1);
      return employeeId;
   }

   public int insertAccount(String password, int employeeId) throws SQLException, NamingException {
      String sql = "INSERT INTO Account (password, employee_id) VALUES (?, ?)";
      PreparedStatement statement =
            DatabaseConnection.instance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, password);
      statement.setInt(2, employeeId);
      statement.executeUpdate();
      ResultSet generatedKey = statement.getGeneratedKeys();
      generatedKey.next();
      int accountId = generatedKey.getInt(1);
      return accountId;
   }

   /**
    * Delete a record from employee table by id
    * 
    * @param id the id of the record to be removed
    * @return number of records successfully deleted
    */
   public Employee deleteEmployee(String id) {
      try {
         Statement statement = DatabaseConnection.instance().createStatement();
         // Get the record being deleted
         String select = "SELECT * FROM all_employees WHERE employee_id=" + id;
         ResultSet rs = statement.executeQuery(select);
         rs.next();
         Employee employee = Employee.resultToEmployee(rs);
         String sql = "DELETE FROM employee WHERE employee_id=" + id;
         statement.executeUpdate(sql);
         return employee;
      } catch (SQLException | NamingException ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public Employee updateEmployee(UpdateEmployeeDTO dto) {
      try {
         String employeeId = dto.getId();
         String accountId = dto.getAccountId();
         String employeeType = dto.getEmployeeType();
         String firstname = dto.getFirstname();
         String lastname = dto.getLastname();
         Connection instance = DatabaseConnection.instance();
         Employee employee = getEmployeeById(employeeId);
         // Update employee information
         String employeeUpdatedSql =
               "UPDATE employee SET firstname=?, lastname=? WHERE employee_id=?";
         PreparedStatement updateStatement = instance.prepareStatement(employeeUpdatedSql);
         updateStatement.setString(1, firstname);
         updateStatement.setString(2, lastname);
         updateStatement.setInt(3, employee.getId());
         updateStatement.executeUpdate();

         /**
          * If the employee type changes, the record in the old type needs to be deleted and a new
          * record needs to be created in the new type.
          */
         if (!employee.getEmployeeType().equals(employeeType)) {
            String deleteSql = "DELETE FROM " + employee.getEmployeeType() + " WHERE employee_id="
                  + employee.getId();
            Statement deleteStatement = instance.createStatement();
            deleteStatement.executeUpdate(deleteSql);

            String createSql =
                  "INSERT INTO " + employeeType + " (employee_id, account_id) VALUES (?, ?)";
            PreparedStatement createStatement = instance.prepareStatement(createSql);
            createStatement.setInt(1, Integer.parseInt(employeeId));
            createStatement.setInt(2, Integer.parseInt(accountId));
            createStatement.executeUpdate();
         }

         return getEmployeeById(employeeId);

      } catch (SQLException | NamingException ex) {
         ex.printStackTrace();
         return null;
      }
   }

}
