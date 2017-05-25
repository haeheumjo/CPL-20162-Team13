package JDBC;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Control {
	static String driver = null;
	static String url = null;
	static String username = null;
	static String password = null;
	static {
		try {
			// properties file���� data ����
			InputStream in = Control.class.getClassLoader().getResourceAsStream("db.properties");
			// properties class â��

			Properties prop = new Properties();
			// prop�� data�� ����
			prop.load(in);
			// properties���� driver,url,username,password ����
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			// driver �ε�
			Class.forName(driver);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getconnection() throws SQLException {
		// properties���� ���� url,username,passwordf�� loading
		return DriverManager.getConnection(url, username, password);
	}

	public static void release(Connection conn, Statement st, ResultSet rs) {
		// ���� �ߴ� ��ġ
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (st != null)
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void common(String sql) {
		java.sql.Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// ���� loading
			conn = Control.getconnection();
			// ���� â��
			st = conn.createStatement();
			// sql ��ɾ� ������
			st.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Control.release(conn, st, rs);
		}
	}
}