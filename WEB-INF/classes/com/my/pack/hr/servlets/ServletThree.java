package com.my.pack.hr.servlets;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.my.pack.hr.dl.*;
import java.util.*;
public class ServletThree extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception exception)
{
// do nothing
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
BufferedReader br=request.getReader();
StringBuffer b=new StringBuffer();
String d;
while(true)
{
d=br.readLine();
if(d==null) break;
b.append(d);
}
String rawData=b.toString();
System.out.println(rawData);
Gson gson=new Gson();
Customer c=gson.fromJson(rawData,Customer.class);
System.out.println(c);
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
pw.print(gson.toJson(c));
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