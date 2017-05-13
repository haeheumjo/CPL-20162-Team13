package JDBC;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import com.mysql.jdbc.Connection;
public class Method {
	public void Insert(String sql)
	{
		Control.common(sql);
	}
	public void Delete(String sql)
	{
		Control.common(sql);
	}
	public void Update(String sql)
	{
		Control.common(sql);
	}
	public static   user Select(String sql){
		java.sql.Connection conn=null;
		java.sql.Statement st=null;
		ResultSet rs=null;
		user u = new user();
		try{
			//楷搬 loading
			conn=Control.getconnection();
			//己疙 芒汲
			st=conn.createStatement();
	        //sql 疙飞绢 焊辰促
			rs=st.executeQuery(sql);
			
			while(rs.next()){
				u.setPw(rs.getString("pw"));
				u.setId(rs.getString("id"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Control.release(conn, st, rs);
		}
		return u;	
	}
	
	public static ByteArrayOutputStream Selectallattencheck() throws IOException {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT  *FROM attancecheck";

		// json 的初始化
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// 创建一个新的json工厂
		JsonFactory f = new JsonFactory();
		// 初始化json流
		JsonGenerator g = f.createJsonGenerator(stream);
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			g.writeStartObject();
			//g.writeObjectFieldStart("facilitiesTblEntry");//这个干什么的？
			g.writeFieldName("AttenceCheck");
			g.writeStartArray();
			while (rs.next()) {

				g.writeStartObject();
				
				g.writeObjectField("name", rs.getString("name"));
				g.writeObjectField("id", rs.getString("id"));
				g.writeObjectField("statue", rs.getString("statue"));
				
				g.writeEndObject();

			}
			g.writeEndArray();
			g.writeEndObject();
			
			g.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Control.release(conn, st, rs);
		}
		return stream;
	}
	
	
	public static void   gradepost(String id,String name,String age,String match,String english,String chinese) throws SQLException{
		String sql="INSERT INTO grade VALUES('"+id+"','"+name+"','"+age+"','"+match+"','"+english+"','"+chinese+"');";
		//String sql="INSERT INTO grade(id,name,age,match,english,chinese) VALUES('"+id+"','"+name+"','"+age+"','"+match+"','"+english+"','"+chinese+"');";
		Method m=new Method();
		m.Insert(sql);
		
	}
	public static void   gradeput(String id,String name,String age,String match,String english,String chinese) throws SQLException{
		String sql="UPDATE  grade SET id='"+id+"',name='"+name+"',age='"+age+"'WHERE id='"+id+"';";
		//String sql="INSERT INTO grade(id,name,age,match,english,chinese) VALUES('"+id+"','"+name+"','"+age+"','"+match+"','"+english+"','"+chinese+"');";
		Method m=new Method();
		m.Insert(sql);
		
	}
	public static void gradedelete(String id,String name,String age,String match,String english,String chinese) throws SQLException{
		String sql="DELETE FROM grade WHERE id='"+id+"';";
		//String sql="INSERT INTO grade(id,name,age,match,english,chinese) VALUES('"+id+"','"+name+"','"+age+"','"+match+"','"+english+"','"+chinese+"');";
		Method m=new Method();
		m.Delete(sql);
		
	}
}
