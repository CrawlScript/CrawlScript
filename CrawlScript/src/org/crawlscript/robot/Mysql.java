package org.crawlscript.robot;

import java.sql.*;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

public class Mysql extends ScriptableObject{
	String url;
	String user;
	String password;
	
	public Mysql()
	{
		
	}
	
	@JSConstructor
	public Mysql(String ip,String dbname,String user,String password)
	{
		
		url="jdbc:mysql://"+ip+"/"+dbname+"?useUnicode=true&characterEncoding=utf-8";
		this.user=(String) user;
		this.password=(String) password;
	}
	
	@JSFunction
	public  Connection getCon()
	{
		Connection con = null;
	
		try
		{
			con=DriverManager.getConnection(url,user,password);
		}
		catch (Exception e)
		{
			System.out.println("error 1");
		}
		return con;
	}

	
	@JSFunction
	public int execute(String sqlcmd)
	{
		int x=-1;
		Connection con1=getCon();
		
		if(con1==null)
			return -1;
		
		try {
			Statement st = con1.createStatement();
			x=st.executeUpdate(sqlcmd);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		return x;
	}
	
	
	public static void main(String[] args) throws SQLException 
	{
		
	
		
		
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "Mysql";
	}
}
