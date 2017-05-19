import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommandsTest {
	private static final String[] RECORD_COLUMNNAME={
			"S_ID",
			"DATE",
			"TYPE"
	};
	private static final String[] ATTENDANCE_COLUMNNAME={
			"C_ID",
			"R_ID",
			"SESSION"
	};
	private static final String[] CLASS_COLUMNNAME={
			"C_ID",
			"C_NAME",
			"P_ID",
			"CR_NO",
			"SEESION",
			"START",
			"END",
			"DAY"
	};
	final String driver ="org.mariadb.jdbc.Driver";
	final String url = "jdbc:mariadb://127.0.0.1:3306/SAM";
	String user = "root";
	String pw = "root";
	private Connection conn = null;
	private Statement stmt = null;
	private static final int STUDENT_ID_IN_CLASSID = 1;
	
	
	
	public CommandsTest(){
		try {
			Class.forName(driver);
			System.out.println("connecting to db...");
			conn = DriverManager.getConnection(url,user,pw);
			System.out.println("connected");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	public static void main(String[] argv){
//		getStudentList();
//	}
	public String getRecordNo(String pid) throws SQLException{
		String[] table = {"COURSE","ATTENDANCE"};
		String now =  MyDateClass.getHHMM();
		String cid = null;
		String rid = null;
		stmt = conn.createStatement();
		String getCidSql = String.format("SELECT %s FROM %s WHERE %s='%s' AND %s='%s' AND %s<='%s' AND %s>='%s'", 
				CLASS_COLUMNNAME[0], table[0], CLASS_COLUMNNAME[2],pid, CLASS_COLUMNNAME[7], MyDateClass.getDayOfWeek(), CLASS_COLUMNNAME[5], now, CLASS_COLUMNNAME[6], now);
		ResultSet rs = stmt.executeQuery(getCidSql);
		while(rs.next())
			cid = rs.getString(1);
		System.out.println(cid);
		String getRidSql = String.format("SELECT %s FROM %s WHERE %s='%s'", 
				ATTENDANCE_COLUMNNAME[1], table[1], ATTENDANCE_COLUMNNAME[0],cid);
		rs.close();
		rs = stmt.executeQuery(getRidSql);
		while(rs.next())
			rid = rs.getString(1);
		rs.close();
		stmt.close();
		return rid;
	}
	public String getStudentList(String pid){
		if(conn == null){
			return "2014015036";
		}
		String stdList = null;
		ResultSet rs = null;
		String attandenceNo = null;
		Calendar cal = Calendar.getInstance();
        Date dt=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String fort = sdf.format(dt);
		/***********
		 * 1.根据日期查找从course表中查找C_ID
		 * 2.根据C_ID从attendance表中寻找R_ID
		 * 3.从相应的R_ID中提取学生表
		 * 3.1 并且在每次提取中插入新的日期状态。
		 * **************/
		
		/*****3.从相应的R_ID中提取学生表******/
		try {
			stmt = conn.createStatement();
			attandenceNo = getRecordNo(pid);
			String adtSql = String.format("SELECT DISTINCT %s from %s",RECORD_COLUMNNAME[0],attandenceNo);
			System.out.println(adtSql);
			rs = stmt.executeQuery(adtSql);
			while(rs.next()){
				String temp = null;
				if(stdList == null){
					stdList = rs.getString(RECORD_COLUMNNAME[0]);
					temp = stdList;
				}
				else{
					temp = rs.getString(RECORD_COLUMNNAME[0]);
					stdList = stdList + "+" + temp;
				}
				/***3.1***/
				insertNewRecord(temp,attandenceNo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(stdList);
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stdList;
	}
	public boolean insertNewProfessor(String pid, String pName, String dept) throws SQLException{
		PreparedStatement pst;
		String table = "PROFESSOR";
		final String[] professorCol = {
				"P_ID",
				"P_NAME",
				"DEPT"
		};
		String tempSql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", table, professorCol[0],professorCol[1],professorCol[2]);
		pst = conn.prepareStatement(tempSql);
		pst.setString(1,pid);
		pst.setString(2, pName);
		pst.setString(3, dept);
		return pst.execute();
	}
	private boolean insertNewRecord(String aStudent,String no) throws SQLException{
		PreparedStatement pst;
		String tempSql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", 
				no, RECORD_COLUMNNAME[0], RECORD_COLUMNNAME[1], RECORD_COLUMNNAME[2]);
		pst = conn.prepareStatement(tempSql);
		pst.setString(1, aStudent);
		pst.setString(2, MyDateClass.getYYYYMMDD());
		pst.setString(3, "absent");
		boolean isTrue = pst.execute();
		System.out.println(isTrue);
		pst.close();
		return isTrue;
	}
	public int updateNewRecord(String newStudentList) throws SQLException{
		stmt = conn.createStatement();
		String table = "R_001";
		String col = "TYPE";
		String where[] = {"S_ID","DATE"};
		List<String> studentList = Arrays.asList(newStudentList.split("\\+"));
		for(String aStudent : studentList){
			String sql = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s' AND %s = '%s'", table, col,"Y", where[0], aStudent,where[1],MyDateClass.getYYYYMMDD());
			boolean isFlase = stmt.execute(sql);
			System.out.println(isFlase);
		}

		stmt.close();
		return 1;
	}
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
