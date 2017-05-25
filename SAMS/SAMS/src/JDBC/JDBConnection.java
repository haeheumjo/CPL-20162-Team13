package JDBC;

import java.sql.*;

public class JDBConnection {
	private final String dbDriver = "com.mysql.jdbc.Driver"; // ����sql���ݿ�ķ���
	private final String url = "jdbc:mysql://localhost/";
	private final String userName = "root";
	private final String password = "root";
	private Connection con = null;
	public JDBConnection() {
		try {
			Class.forName(dbDriver).newInstance(); // �������ݿ�����
		} catch (Exception ex) {
			System.out.println("���ݿ����ʧ��");
		}
	}

	// �������ݿ�����
	public boolean creatConnection() {
		try {
			con = DriverManager.getConnection(url, userName, password);
			con.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("creatConnectionError!");
		}
		return true;
	}

	// �����ݿ�����ӡ��޸ĺ�ɾ���Ĳ���
	public boolean executeUpdate(String sql) {

		if (con == null) {
			creatConnection();
		}
		try {
			Statement stmt = con.createStatement();
			int iCount = stmt.executeUpdate(sql);
			System.out.println("�����ɹ�����Ӱ��ļ�¼��Ϊ" + String.valueOf(iCount));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("executeUpdaterError!");
		}
		return true;
	}

	// �����ݿ�Ĳ�ѯ����
	public ResultSet executeQuery(String sql) {
		ResultSet rs;
		try {
			if (con == null) {
				creatConnection();
			}
			Statement stmt = con.createStatement();
			try {
				rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return null;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("executeQueryError!");
			return null;
		}
		return rs;
	}

	// �ر����ݿ�Ĳ���
	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
				System.out.println("Failed to close connection!");
			} finally {
				con = null;
			}
		}
	}

}