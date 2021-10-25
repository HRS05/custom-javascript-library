package com.my.pack.hr.dl;
import java.util.*;
import java.sql.*;

public class AdministrationDAO
{
public AdministrationDTO getByUsername(String username) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from administration where username=(?)");
preparedStatement.setString(1,username);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close(); 
throw new DAOException("Invalid username : "+username);
}
AdministrationDTO administrator;
administrator=new AdministrationDTO();
String _username;
String _password;
_username=resultSet.getString("username").trim();
_password=resultSet.getString("password").trim();
administrator.setUsername(_username);
administrator.setPassword(_password);
resultSet.close();
preparedStatement.close();
connection.close(); 
return administrator;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}//function ends
}//class ends