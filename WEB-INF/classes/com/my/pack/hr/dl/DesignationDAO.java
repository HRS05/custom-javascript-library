package com.my.pack.hr.dl;
import java.sql.*;
import java.util.*;
public class DesignationDAO
{
public void add(DesignationDTO designation) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=(?)");
preparedStatement.setString(1,designation.getTitle());
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close(); 
throw new DAOException("Designation : "+designation.getTitle()+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values (?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,designation.getTitle());
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
designation.setCode(code);
resultSet.close();
preparedStatement.close();
connection.close(); 
}catch(SQLException sqlexception)
{
throw new DAOException(sqlexception.getMessage());
}
}//function ends



public List<DesignationDTO> getAll() throws DAOException
{
List<DesignationDTO> designations;
designations=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation order by title");
DesignationDTO designationDTO;
int code;
String title;
while(resultSet.next())
{
code=resultSet.getInt("code");
title=resultSet.getString("title").trim();
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return designations;
}//function ends

public DesignationDTO getByCode(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation with code :"+code+" not exists.");
}
DesignationDTO designation;
designation=new DesignationDTO();
designation.setCode(resultSet.getInt("code"));
designation.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designation;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}//function ends

public void update(DesignationDTO designation) throws DAOException
{
try
{
int code=designation.getCode();
String title=designation.getTitle();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code :"+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from designation where title=? and code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(title+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?"); 
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}//function ends

public void delete(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code :"+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Cannot delete designation as it has been alloted to an employee");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}//function ends


public boolean  designationCodeExists(int code) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select code from designation where code=?");
preparedStatement.setInt(1,code);
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



}//class ends   










