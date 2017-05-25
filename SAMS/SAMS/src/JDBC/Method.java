package JDBC;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import com.mysql.jdbc.Connection;

import database.MyDateClass;
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
			//���� loading
			conn=Control.getconnection();
			//���� â��
			st=conn.createStatement();
	        //sql ��ɾ� ������
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
	public static String gettype(String id)
	{
		String position=null;
		java.sql.Connection conn=null;
		java.sql.Statement st=null;
		ResultSet rs=null;
		String sql="SELECT *FROM user WHERE id='"+id+"';";
		try{
			//���� loading
			conn=Control.getconnection();
			//���� â��
			st=conn.createStatement();
	        //sql ��ɾ� ������
			rs=st.executeQuery(sql);
			
			while(rs.next()){
				position=	rs.getString("position");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Control.release(conn, st, rs);
		}
		return position;	
		
		
	}
	public static ByteArrayOutputStream Selectallattencheck() throws IOException {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT  * FROM attancecheck";
		// json �ĳ�ʼ��
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// ����һ���µ�json����
		JsonFactory f = new JsonFactory();
		// ��ʼ��json��
		JsonGenerator g = f.createJsonGenerator(stream);
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			g.writeStartObject();
			//g.writeObjectFieldStart("facilitiesTblEntry");//�����ʲô�ģ�
			g.writeFieldName("AttenceCheck");
			g.writeStartArray();
			while (rs.next()) {
				g.writeStartObject();
				g.writeObjectField("name", rs.getString("name"));
				g.writeObjectField("id", rs.getString("id"));
				g.writeObjectField("type", rs.getString("type"));
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
	//By MR
	public static ByteArrayOutputStream SelectRecord(String rid) throws IOException {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		java.sql.Statement subst = null;
		ResultSet rs = null;
		ResultSet subrs = null;
		String sql = String.format("SELECT * FROM %s WHERE DATE = '%s'", rid, MyDateClass.getYYYYMMDD());
//		String sql = String.format("SELECT * FROM %s WHERE DATE = '%s'", rid, "2017-05-22");
		
		// json �ĳ�ʼ��
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// ����һ���µ�json����
		JsonFactory f = new JsonFactory();
		// ��ʼ��json��
		JsonGenerator g = f.createJsonGenerator(stream);
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			subst = conn.createStatement();
			rs = st.executeQuery(sql);
			g.writeStartObject();
			//g.writeObjectFieldStart("facilitiesTblEntry");//�����ʲô�ģ�
			g.writeFieldName("AttenceCheck");
			g.writeStartArray();
			while (rs.next()) {
				String sid = rs.getString("S_ID");
				String type = rs.getString("TYPE");
				String subSql = String.format("SELECT * FROM student WHERE s_id = '%s'",sid);
				String sname = null;
				subrs = subst.executeQuery(subSql);
				if(subrs.next())
					sname = subrs.getString("S_NAME");
				g.writeStartObject();
				g.writeObjectField("name", sname);
				g.writeObjectField("id", sid);
				g.writeObjectField("type", type);
				g.writeEndObject();
			}
			g.writeEndArray();
			g.writeEndObject();
			g.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Control.release(conn, st, rs);
			Control.release(null, subst, subrs);
		}
		return stream;
	}
	public static String getSingalCol(String sql){
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		ResultSet rs = null;
		String col = null;
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
				col = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println(sql);
			System.out.println(col);
			Control.release(conn, st, rs);
			return col;
		}
	}
	public static boolean delectItem(String sql){
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		boolean isFalse = true;
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			System.out.println(sql);
			isFalse = st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Control.release(conn, st, null);
			System.out.println(Method.class.getName()+"[delectItem]"+!isFalse);
			return isFalse;
		}
	}
	public static String getList(String sql){
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		ResultSet rs = null;
		String classList = null;
		try {
			conn = Control.getconnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next())
				if(classList == null)
					classList = rs.getString(1);
				else{
					classList = String.format("%s+%s", classList, rs.getString(1));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Control.release(conn, st, rs);
			System.out.println(Method.class.getName()+"[get]"+classList);
			return classList;
		}
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
	public static void main(String args[]) throws UnsupportedEncodingException, IOException
	{
		System.out.println(Selectallattencheck().toString("utf-8"));
		//System.out.println(Method.gettype("admin"));
	}
}

