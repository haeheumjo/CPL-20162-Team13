package JDBC;

import java.io.IOException;

public class JDBCdemo {
public static void main(String args[]) throws IOException
{
	Method c=new Method();
//	String sql="insert into student(id,name,chinese,english,math) values(50,'wang',34,56,78);";
//	String sql="update student set id=8 where name='wang';";
//	String sql="delete from student where name='wang';";
//    c.Insert(sql);	
	//String sql="select *from user;"; 
//	
	//user u= Method.Select(sql);
    System.out.println(c.Selectallattencheck().toString("utf-8"));
	//System.out.println(u.getPw());
}
}
