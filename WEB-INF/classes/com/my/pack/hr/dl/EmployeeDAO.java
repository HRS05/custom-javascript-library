package com.my.pack.hr.dl;
import java.sql.*;
import java.util.*;
import java.math.*;
public class EmployeeDAO
{

public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO> employees;
employees=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select employee.employee_id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code order by employee.name");
EmployeeDTO employeeDTO;
int employeeId;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
while(resultSet.next())
{
employeeId=resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
designation=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth"); 
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employees;
}//function ends

public void add(EmployeeDTO employee) throws DAOException
{
try
{
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select employee_id from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number : "+panNumber+"exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar Card Number : "+aadharCardNumber+"exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getDesignationCode());
java.util.Date dateOfBirth=employee.getDateOfBirth();
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int id=resultSet.getInt(1);
employee.setEmployeeId("A"+id);
resultSet.close();
preparedStatement.close();
connection.close();
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}//function ends


public boolean  panNumberExists(String panNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}//function ends


public boolean  aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}//function ends




public void delete(String employeeId) throws DAOException
{
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid emplyee Id. :"+employeeId);
} 
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}//function ends



public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
int actualEmployeeId=0;
EmployeeDTO employeeDTO=null;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid emplyee Id. :"+employeeId);
}
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designationDTO=designationDAO.getByCode(designationCode);
designation=designationDTO.getTitle().trim();
dateOfBirth=resultSet.getDate("date_of_birth"); 
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO; 
}//function ends


public void update(EmployeeDTO employee) throws DAOException
{
try
{
String employeeId=employee.getEmployeeId();
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid emplyee Id. :"+employeeId);
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?"); 
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid emplyee Id. :"+employeeId);
}
resultSet.close();
preparedStatement.close();


String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
preparedStatement=connection.prepareStatement("select employee_id from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number : "+panNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar Card Number : "+aadharCardNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getDesignationCode());
java.util.Date dateOfBirth=employee.getDateOfBirth();
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}//function ends


public boolean employeeIdExists(String employeeId) throws DAOException
{
int actualEmployeeId=0;
EmployeeDTO employeeDTO=null;
boolean exists=false;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
return exists;
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
return exists;
}
return exists; 
}//function ends


public EmployeeDTO getByPANNumber(String panNumber) throws DAOException
{
EmployeeDTO employeeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN number :"+panNumber);
}
int actualEmployeeId;
String employeeId;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;
actualEmployeeId=resultSet.getInt("employee_id");
employeeId="A"+actualEmployeeId+"";
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designationDTO=designationDAO.getByCode(designationCode);
designation=designationDTO.getTitle().trim();
dateOfBirth=resultSet.getDate("date_of_birth"); 
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
resultSet.getString("pan_number");
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO; 
}//function ends


public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
EmployeeDTO employeeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar card number number :"+aadharCardNumber);
}
int actualEmployeeId;
String employeeId;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
actualEmployeeId=resultSet.getInt("employee_id");
employeeId="A"+actualEmployeeId+"";
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
DesignationDAO designationDAO=new DesignationDAO();
DesignationDTO designationDTO=designationDAO.getByCode(designationCode);
designation=designationDTO.getTitle().trim();
dateOfBirth=resultSet.getDate("date_of_birth"); 
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO; 
}//function ends





}//class ends   










