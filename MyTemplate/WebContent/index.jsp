<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="com.example.bean.MyBean" %>
<%
	MyBean myBean = new MyBean();
	myBean.setName("my name");
	myBean.setAddress("my address");
%>
<table>
<tr><th>Name</th><td><%= myBean.getName() %></td></tr>
<tr><th>Address</th><td><%= myBean.getAddress() %></td></tr>
</table>
