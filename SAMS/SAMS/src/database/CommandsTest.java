package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.SQLError;

import JDBC.Control;
import JDBC.Method;
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
			"C_ID", //0
			"C_NAME", //1
			"P_ID", //2
			"CR_NO", //3
			"SESSION", //4
			"START", //5
			"END", //6
			"DAY", //7
			"RPI_ADR"
	};
	private static final String[] SESSION_COLUMNNAME = {
			"SESSION",
			"START",
			"END"
	};
	private static final String[] STUDENT_COLUMNNAME = {
			"s_id",
			"s_name",
			"year",
			"dept"
	};
	private static final String[] ACCOUNTS_COLUMNNAME = {
			"ID",
			"PW",
			"TYPE"
	};
	private static final String[] CLASSROOM_COLUMNNAME = {
			"CR_NO",
			"RPI_ADR"
	};
	private static final String[] ENROLLMENT_COLUMNNAME= {
			"S_ID",
			"C_ID",
			"SESSION"
	};
	private static final String STUDENT = "STUDENT";
	private static final String ENROLLMENT = "ENROLLMENT";
	private static final String COURSE = "COURSE";
	private static final String CLASSROOM = "CLASSROOM";
	private static final String ACCOUNTS = "ACCOUNTS";
	private static final String ACCOUNTS_TYPE_PROF = "prof";
	private Connection conn = null;
	private Statement stmt = null;
	private static final int STUDENT_ID_IN_CLASSID = 1;
	public CommandsTest(){
		try {
			conn = Control.getconnection();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public List<Record> makeRecordListByRecordNo(String recordNo){
//		List<Record> recordList = new ArrayList<Record>();
//		String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", recordNo, RECORD_COLUMNNAME[1],MyDateClass.getYYYYMMDD());
//		
//		try {
//			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				String s_name = null;
//				String s_id = rs.getString(1);
//				String type = rs.getString(3);
//				String subsql = String.format("SELECT %s FROM %s WHERE %s = '%s'",
//						STUDENT_COLUMNNAME[1], STUDENT, STUDENT_COLUMNNAME[0], s_id);
//				Statement stmt2 = conn.createStatement();
//				ResultSet stdRs = stmt2.executeQuery(subsql);
//				if(stdRs.next())
//					s_name = stdRs.getString(1);
//				System.out.println("student: "+s_name);
//				Record item = new Record(s_id,s_name,type);
//				recordList.add(item);
//				stmt2.close();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		return recordList;
//	}
	
	public static String getClassRoomNoByPid(String pid) throws SQLException{
		String crid = null;
		String session = getSession();
		System.out.println("getClassRoomNoByPid(): [session]"+session);
		String now = MyDateClass.getHHMM();
		String day = MyDateClass.getDayOfWeek();
		String sql = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s' AND %s <= '%s' AND %s >= '%s' AND %s = '%s' ",
				CLASS_COLUMNNAME[3], COURSE, CLASS_COLUMNNAME[2],pid, CLASS_COLUMNNAME[4], session, CLASS_COLUMNNAME[5],now,CLASS_COLUMNNAME[6],now, CLASS_COLUMNNAME[7],day);
		System.out.println("getClassRoomNoByPid(): [sql]"+sql);
		crid = Method.getSingalCol(sql);
		return crid;
	}
	public String getIpAddress(String crid) throws SQLException {
		String ip = null;
		String subsql = String .format("SELECT %s FROM %s WHERE %s = '%s'",
				CLASSROOM_COLUMNNAME[1],CLASSROOM, CLASSROOM_COLUMNNAME[0],crid);
		ResultSet rs = stmt.executeQuery(subsql);
		if(rs.next())
			ip = rs.getString(1);
		return ip;
	}
	public int checkLog(String id, String pw) throws SQLException{
		String sql = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s'",
				ACCOUNTS_COLUMNNAME[2], ACCOUNTS, ACCOUNTS_COLUMNNAME[0], id, ACCOUNTS_COLUMNNAME[1],pw);
		ResultSet rs =  stmt.executeQuery(sql);
		String type = null;
		if(rs.next()){
			type = rs.getString(1);
			if(type.equals(ACCOUNTS_TYPE_PROF))
				return 1;
			else
				return 2;
		}
		else
			return -1;
	}
	public String getSName(String sid){
		String table = "student";
		String sql = String.format("SELECT %s FROM %s WHERE %s = '%s'", STUDENT_COLUMNNAME[1],
				table, STUDENT_COLUMNNAME[0],sid);
		String sname = Method.getSingalCol(sql);
		return sname;
	}
	public String CheckAttendance(String sid) throws SQLException{
		String subTable = "course";
		String table = "attendance";
		String session=getSession();
		String now = MyDateClass.getHHMM();
		String rid = null;
		String day = MyDateClass.getDayOfWeek();
		//get cid list 
		String subsql = String .format("SELECT %s FROM %s WHERE %s = '%s' AND %s <= '%s' AND %s >= '%s' AND %s = '%s' ",
				CLASS_COLUMNNAME[0], subTable, CLASS_COLUMNNAME[4], session, CLASS_COLUMNNAME[5],now,CLASS_COLUMNNAME[6],now, CLASS_COLUMNNAME[7],day);
		//get rid
		String sql = String.format("SELECT %s FROM %s WHERE %s in (%s) AND %s = '%s'", 
				ATTENDANCE_COLUMNNAME[1], table, ATTENDANCE_COLUMNNAME[0], subsql, ATTENDANCE_COLUMNNAME[2],session);
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
			rid = rs.getString(1);
		//get attendance
		sql = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s'", 
				RECORD_COLUMNNAME[2], rid, RECORD_COLUMNNAME[0],sid, RECORD_COLUMNNAME[1],MyDateClass.getYYYYMMDD());
		rs = stmt.executeQuery(sql);
		String attandence = null;
		while(rs.next())
			attandence = rs.getString(1);
		return attandence;
	}
	public int addNewSession(String session,String start,String end) throws SQLException{
		PreparedStatement pst;
		String table = "SESSION";
		String sql = String.format("INSERT INTO %s (%s,%s,%s) VALUES(?,?,?)", table, SESSION_COLUMNNAME[0],SESSION_COLUMNNAME[1],SESSION_COLUMNNAME[2]);
		pst = conn.prepareStatement(sql);
		pst.setString(1, session);
		pst.setString(2, start);
		pst.setString(3, end);
		boolean isFalse = pst.execute();
		System.out.println(isFalse);
		pst.close();
		if(isFalse)
			return -1;
		return 0;
	}
	/*****
	 * usage: addNewCol("R_001", "20171011", "present");
	 * @param rid
	 * @param date
	 * @param type
	 * @param defult
	 * @throws SQLException
	 */
	//get the session
	public static String getSession(){
		String table = "SESSION";
		String target = "SESSION";
		String date = MyDateClass.getYYYYMMDD();
		String sql = String.format("SELECT %s FROM %s WHERE %s <= '%s' AND %s >= '%s'",
				table, target, SESSION_COLUMNNAME[1], date, SESSION_COLUMNNAME[2], date);
		String session = Method.getSingalCol(sql);
		System.out.println(session);
		return session;
	}
	//student part
	public String getClassList(String sid) throws SQLException{
		
		String table = "enrollment";
		String column = "C_ID";
		String sql = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s'"
				,column,table,ENROLLMENT_COLUMNNAME[0], sid, ENROLLMENT_COLUMNNAME[2], getSession());
		return Method.getList(sql);
	}
	public void addNewCol(String rid, String date,String type, String defult) throws SQLException{
		
		ResultSet rs = null;
		String sql = null;
		if(defult != null)
		sql = String.format("ALTER TABLE %s ADD %s %s not null default '%s'"
				,rid,date,type,defult);
		System.out.println(sql);
		int i = stmt.executeUpdate(sql);
		
		System.out.println(i);
	}
	public static String getCid(String pid){
		String table = "COURSE";
		String now =  MyDateClass.getHHMM();
		String cid = null;
		String day = MyDateClass.getDayOfWeek();
		String getCidSql = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s='%s' AND %s='%s' AND %s<='%s' AND %s>='%s'", 
				CLASS_COLUMNNAME[0], table,"SESSION",getSession(), CLASS_COLUMNNAME[2],pid, CLASS_COLUMNNAME[7], MyDateClass.getDayOfWeek(), CLASS_COLUMNNAME[5], now, CLASS_COLUMNNAME[6], now);
		cid = Method.getSingalCol(getCidSql);
		System.out.println(cid);
		return cid;
	}
	/***鑾峰彇鍑哄嫟琛ㄧ紪鍙�
	 * get
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public String getRecordNo(String cid,String session){
		String table = "ATTENDANCE";
		String now =  MyDateClass.getHHMM();
		String rid = null;
		
		System.out.println(cid);
		String getRidSql = String.format("SELECT %s FROM %s WHERE %s='%s' AND %s = '%s'", 
				ATTENDANCE_COLUMNNAME[1], table, ATTENDANCE_COLUMNNAME[0],cid,ATTENDANCE_COLUMNNAME[2],session);
		rid = Method.getSingalCol(getRidSql);
		return rid;
	}
	public String getStudentList(String r_id){
		String stdList = null;
		ResultSet rs = null;
		String recordNo = r_id;
		/*****3.浠庣浉搴旂殑R_ID涓彁鍙栧鐢熻〃******/
//		try {
			String adtSql = String.format("SELECT DISTINCT %s from %s",RECORD_COLUMNNAME[0],recordNo);
			System.out.println(adtSql);
			stdList = Method.getList(adtSql);
//			rs = stmt.executeQuery(adtSql);
//			while(rs.next()){
//				String temp = null;
//				if(stdList == null){
//					stdList = rs.getString(1);
//					temp = stdList;
//				}
//				else{
//					temp = rs.getString(1);
//					stdList = stdList + "+" + temp;
//				}
//				/***3.1***/
//				insertNewRecord(temp,recordNo);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(stdList);
		return stdList;
	}
	/***鍔犲叆鏂版暀鎺�**/
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
	public boolean clearToDayRecord(String r_id){
		String sql = String.format("DELETE FROM %s WHERE %s = '%s'",r_id,RECORD_COLUMNNAME[1],MyDateClass.getYYYYMMDD());
		return Method.delectItem(sql);
	}
	/**鍒涘缓鏂板嚭鍕よ〃*/
	public boolean insertNewRecord(String aStudent,String no){
		PreparedStatement pst;
		String tempSql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", no, RECORD_COLUMNNAME[0],
				RECORD_COLUMNNAME[1], RECORD_COLUMNNAME[2]);
		try {
			pst = conn.prepareStatement(tempSql);
			pst.setString(1, aStudent);
			pst.setString(2, MyDateClass.getYYYYMMDD());
			pst.setString(3, "present");
			boolean isTrue = pst.execute();
			System.out.println(isTrue);
			pst.close();
			return isTrue;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static String createPart(String newStudentList){
		List<String> studentList = Arrays.asList(newStudentList.split("\\+"));
		String r = null;
		for(String aStudent:studentList)
			if(r == null)
				r = String.format("('%s'",aStudent);
			else{
				r = r + String.format(",'%s'", aStudent);
			}
		r = r + ")";
		return r;
	}
	/**妫�鏌ユ洿鏀圭姸鎬佷负鍑哄嫟*/
	public int updateRecordAsAttend(String newStudentList, String RNo, String AttendanceType) throws SQLException{
		String table = RNo;
		String type = AttendanceType;
		String sql = String.format("UPDATE %s SET %s = '%s' WHERE %s in %s AND %s = '%s'", table, RECORD_COLUMNNAME[2],type, RECORD_COLUMNNAME[0], createPart(newStudentList),RECORD_COLUMNNAME[1],MyDateClass.getYYYYMMDD());
		boolean isFalse = stmt.execute(sql);
		System.out.println(!isFalse);
		if(isFalse)
			return -1;
		return 0;
	}
	public int updateRecordToAbsent(String RNo, String AttendanceType){
		String table = RNo;
		String type = AttendanceType;
		String defaultValue = "present";
		String sql = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s' AND %s = '%s'", table, RECORD_COLUMNNAME[2],type, RECORD_COLUMNNAME[2], defaultValue,RECORD_COLUMNNAME[1],MyDateClass.getYYYYMMDD());
		boolean isFlase = true;
		try {
			isFlase = stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(!isFlase);
		if(isFlase)
			return -1;
		return 0;
	}
	public int insertNewEnroll(String sid, String cid, String session) throws SQLException{
		String table = "ENROLLMENT";
		String colSql = String.format(
				"select COLUMN_NAME from information_schema.COLUMNS where table_name = '%s' and table_schema = '%s'"
				, table,"SAM_TEST");
		ResultSet qs = stmt.executeQuery(colSql);
		List<String> col = new ArrayList<String>();
		while(qs.next()){
			for(int i = 0; i < qs.getMetaData().getColumnCount(); i++)
				col.add(qs.getString(i+1));
		}
		String sql = String.format("INSERT into %s(%s,%s,%s) VALUES(?,?,?) ",
				table,col.get(0),col.get(1),col.get(2));
		System.out.println(sql);
		
		boolean isFalse = false;
		if(isFalse)
			return -1;
		return 0;
	}
	public void close(){
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
	}

}
