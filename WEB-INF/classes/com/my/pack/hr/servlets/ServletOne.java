package com.my.pack.hr.servlets;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.my.pack.hr.dl.*;
import java.util.*;
public class ServletOne extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception exception)
{
// do nothing
}
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setContentType("utf-8");
DesignationDAO designationDAO=new DesignationDAO();
List<DesignationDTO> designations=designationDAO.getAll();

Gson gson=new Gson();
String  jsonString=gson.toJson(designations);
pw.print(jsonString);
pw.flush();
}catch(Exception e)
{
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception ee)
{
// do nothing
}
}
}
}