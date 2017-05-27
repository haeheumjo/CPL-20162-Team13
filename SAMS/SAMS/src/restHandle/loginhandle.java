package restHandle;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import JDBC.Method;
import JDBC.user;
import database.CommandsTest;
import jsonHandle.jsonparse;
import sam.client.ClientManager;

@Path("group")
public class loginhandle {
	// post fang fa
	@POST
	@Path("/login")
	public Response pushusertblpost(String info, @Context HttpServletRequest request)
			throws SQLException, IOException, PropertyVetoException {
		System.out.println(info);
		String id = jsonparse.jsonparsing(info).get("id");
		String pw = jsonparse.jsonparsing(info).get("pw");
		String sql = "SELECT * FROM user WHERE id='" + id + "'";
		user u = Method.Select(sql);
		System.out.print(id + pw +"\n");
		if (pw.equals(u.getPw()) && Method.gettype(id).equals("p")) {
			HttpSession session = request.getSession();
			session.setAttribute("pid", id);
			System.out.println("save the pid in session");
			return Response.ok("p").status(200).build();
		}
		if (pw.equals(u.getPw()) && Method.gettype(id).equals("a")) {
			return Response.ok("a").status(200).build();
		} else {
			return Response.ok(" login failure!!").status(400).build();
		}
	}
	
	//connect to rpi
	@POST
	@Path("/rpi/socket")
	public Response socketstart(String info, @Context HttpServletRequest request)
			throws SQLException, IOException, PropertyVetoException {
		if (info.equals("start socket!")) {
			HttpSession session = request.getSession();
			String pid = (String)session.getAttribute("pid");
			if(pid == null || pid.equals("")){
				System.out.println("He He !");
				return Response.ok().status(400).build();
			}
			else{
				CommandsTest test = new CommandsTest();
				String cid = test.getCid(pid);
				if(cid == null || cid.equals(""))
					return Response.ok().status(400).build();
				System.out.println("cid: "+cid);
				String ss = test.getSession();
				if(ss == null || ss.equals(""))
					return Response.ok().status(400).build();
				String rid = test.getRecordNo(cid, ss);
				if(rid == null || rid.equals(""))
					return Response.ok().status(400).build();
				String cr_id = test.getClassRoomNoByPid(pid);
				if(cr_id == null || cr_id.equals(""))
					return Response.ok().status(400).build();
				String ip = test.getIpAddress(cr_id);
				if(ip == null || ip.equals(""))
					return Response.ok().status(400).build();
				session.setAttribute("rid", rid);
				session.setAttribute("ss", ss);
				session.setAttribute("ip", ip);
				System.out.println("saved rid : "+rid);
				test.close();
				System.out.println("session : "+pid);
				if(!ClientManager.start(ip,rid))
					return Response.ok().status(400).build();
				session.setAttribute("attenceStatus", "stop");
			}
			return Response.ok().status(200).build();
		}else if (info.equals("stop socket!")) {
			HttpSession session = request.getSession();
			String ip = (String)session.getAttribute("ip");
			
			
			if(ip == null || ip.equals("")){
				System.out.println("He He !");
				return Response.ok().status(400).build();
			}
			else{
				System.out.println("session[ip] : "+ip);
				if(!ClientManager.stop(ip))
					return Response.ok().status(400).build();
			}
			session.setAttribute("attenceStatus", "start");
			return Response.ok().status(200).build();
		} else {
			return Response.ok().status(400).build();
		}
	}
	@POST
	@Path("/rpi/check")
	public Response checkstart(String info, @Context HttpServletRequest request)
			throws SQLException, IOException, PropertyVetoException {
		System.out.println(info);
		if (info.equals("start check!")) {
			HttpSession session = request.getSession();
			String pid = (String)session.getAttribute("pid");
			if(pid == null || pid.equals("")){
				System.out.println("He He !");
				return Response.ok().status(400).build();
			}
			else{
				CommandsTest test = new CommandsTest();
				String cid = test.getCid(pid);
				if(cid == null || cid.equals(""))
					return Response.ok().status(400).build();
				System.out.println("cid: "+cid);
				String ss = test.getSession();
				if(ss == null || ss.equals(""))
					return Response.ok().status(400).build();
				String rid = test.getRecordNo(cid, ss);
				if(rid == null || rid.equals(""))
					return Response.ok().status(400).build();
				String cr_id = test.getClassRoomNoByPid(pid);
				if(cr_id == null || cr_id.equals(""))
					return Response.ok().status(400).build();
				String ip = test.getIpAddress(cr_id);
				if(ip == null || ip.equals(""))
					return Response.ok().status(400).build();
				session.setAttribute("rid", rid);
				session.setAttribute("ss", ss);
				session.setAttribute("ip", ip);
				System.out.println("saved rid : "+rid);

				System.out.println("saved ip : "+ip);
				System.out.println("saved ss : "+ss);
				test.close();
				System.out.println("session : "+pid);
			}
			return Response.ok().status(200).build();
		} else {
			return Response.ok().status(400).build();
		}
	}
	@PUT
	@Path("entry")
	public Response pushusertblput(String info, @Context HttpServletRequest request) throws SQLException {
		String id = jsonparse.jsonparsing(info).get("id");
		String name = jsonparse.jsonparsing(info).get("name");
		String age = jsonparse.jsonparsing(info).get("age");
		String match = jsonparse.jsonparsing(info).get("match");
		String english = jsonparse.jsonparsing(info).get("english");
		String chinese = jsonparse.jsonparsing(info).get("chinese");
		if (id != null & name != null & age != null & match != null & english != null & chinese != null) {
			Method.gradeput(id, name, age, match, english, chinese);
			// ru guo chenng gong jiu fan hui 200
			return Response.ok(" insert success!!").status(200).build();
		} else {
			// ru guo shi bai jiu fan hui 400
			return Response.ok(" insert failure!!").status(400).build();
		}
	}

	@DELETE
	@Path("entry")
	public Response deletebyphonenumber(String info, @Context HttpServletRequest request,
			@PathParam("phone") String phone) throws UnsupportedEncodingException, IOException, SQLException {
		String id = jsonparse.jsonparsing(info).get("id");
		String name = jsonparse.jsonparsing(info).get("name");
		String age = jsonparse.jsonparsing(info).get("age");
		String match = jsonparse.jsonparsing(info).get("match");
		String english = jsonparse.jsonparsing(info).get("english");
		String chinese = jsonparse.jsonparsing(info).get("chinese");
		if (id != null) {
			Method.gradedelete(id, name, age, match, english, chinese);
			return Response.ok("successful!").status(200).build();
		} else {
			return Response.ok("please check phonenumber").status(400).build();
		}

	}

	/*
	 * //���� �߰�
	 * 
	 * @POST
	 * 
	 * @Path("deviceinfo/entry") public Response pushusertblpostbydevice(String
	 * info, @Context HttpServletRequest request) throws
	 * UnsupportedEncodingException, IOException {
	 * 
	 * System.out.println(ParseJson.jsonparsing(info).get("phone")); String
	 * mobileplatform=ParseJson.jsonparsing(info).get("mobilePlatform"); String
	 * phone=ParseJson.jsonparsing(info).get("phone"); String
	 * gcminfo=ParseJson.jsonparsing(info).get("gcmInfo"); String
	 * apnsinfo=ParseJson.jsonparsing(info).get("apnsInfo"); String
	 * userlevel=ParseJson.jsonparsing(info).get("userLevel"); String
	 * notifyfilter=ParseJson.jsonparsing(info).get("notifyFilter"); int
	 * length=Method.Selectbyphonenumber(phone).toString("utf-8").length();
	 * if(mobileplatform!=null&&phone!=null&&gcminfo!=null&&apnsinfo!=null&&
	 * length>50){ Method.pushusertbluserput( phone, gcminfo, apnsinfo,
	 * mobileplatform, userlevel, notifyfilter);
	 * 
	 * return Response.ok(" update success!!").status(200).build(); }else {
	 * Method.pushusertbluserinsert(phone, gcminfo, apnsinfo, mobileplatform,
	 * userlevel,notifyfilter); return
	 * Response.ok(" insert success!!").status(400).build(); } } //Ǫ������� ����
	 * ����
	 * 
	 * @PUT
	 * 
	 * @Path("entry") public Response pushusertblput(String info, @Context
	 * HttpServletRequest request) {
	 * System.out.println(ParseJson.jsonparsing(info).get("phone")); String
	 * mobileplatform=ParseJson.jsonparsing(info).get("mobilePlatform"); String
	 * phone=ParseJson.jsonparsing(info).get("phone"); String
	 * gcminfo=ParseJson.jsonparsing(info).get("gcmInfo"); String
	 * apnsinfo=ParseJson.jsonparsing(info).get("apnsInfo"); String
	 * userlevel=ParseJson.jsonparsing(info).get("userLevel"); String
	 * notifyfilter=ParseJson.jsonparsing(info).get("notifyFilter");
	 * if(notifyfilter!=null&&mobileplatform!=null&&phone!=null&&gcminfo!=null&&
	 * apnsinfo!=null){
	 * 
	 * Method.pushusertbluserput(phone, gcminfo, apnsinfo, mobileplatform,
	 * userlevel,notifyfilter); return
	 * Response.ok(" put success!!").status(200).build(); }else { return
	 * Response.ok(" put failure!!").status(400).build(); }
	 * 
	 * } //����ȣ�� ���Ͽ� ��������
	 * 
	 * @PUT
	 * 
	 * @Path("entry/indexes/phonenumber/{phonenumber}") public Response
	 * pushusertbltest(String info, @Context HttpServletRequest request ) {
	 * System.out.println(ParseJson.jsonparsing(info).get("phone")); String
	 * mobileplatform=ParseJson.jsonparsing(info).get("mobilePlatform"); String
	 * phone=ParseJson.jsonparsing(info).get("phone"); String
	 * gcminfo=ParseJson.jsonparsing(info).get("gcmInfo"); String
	 * apnsinfo=ParseJson.jsonparsing(info).get("apnsInfo"); String
	 * userlevel=ParseJson.jsonparsing(info).get("userLevel"); String
	 * notifyfilter=ParseJson.jsonparsing(info).get("notifyFilter");
	 * if(notifyfilter!=null&&mobileplatform!=null&&phone!=null&&gcminfo!=null&&
	 * apnsinfo!=null){
	 * 
	 * Method.pushusertbluserput(phone, gcminfo, apnsinfo, mobileplatform,
	 * userlevel,notifyfilter); //return
	 * Response.ok("{\"code\":\"04\",\"msg\":\"�Ϲݿ���\"}").status(400).build();
	 * return
	 * Response.ok("{\"code\":\"02\",\"msg\":\"����\"}").status(200).build();
	 * }else { return
	 * Response.ok("{\"code\":\"04\",\"msg\":\"�Ϲݿ���\"}").status(400).build();
	 * }
	 * 
	 * } //Ǫ������� ��������
	 * 
	 * @PUT
	 * 
	 * @Path("entry/phonenumber") public Response pushusertblputbyphone(String
	 * info, @Context HttpServletRequest request) {
	 * System.out.println(ParseJson.jsonparsing(info).get("phone"));
	 * System.out.println(ParseJson.jsonparsing(info).get("userLevel"));
	 * System.out.println(ParseJson.jsonparsing(info).get("sms")); String
	 * phone=ParseJson.jsonparsing(info).get("phone");
	 * 
	 * String userlevel=ParseJson.jsonparsing(info).get("userLevel"); String
	 * sms=ParseJson.jsonparsing(info).get("sms"); if(phone.length()==11){
	 * 
	 * Method.pushusertbluserputbyphone(phone,userlevel,sms); return
	 * Response.ok(" put success!!").status(200).build(); }else { return
	 * Response.ok(" put failure!!").status(400).build(); }
	 * 
	 * } //Ǫ������� ��������
	 * 
	 * @DELETE
	 * 
	 * @Path("entry") public Response pushusertblDelete(String info, @Context
	 * HttpServletRequest request) {
	 * System.out.println(ParseJson.jsonparsing(info).get("phone")); String
	 * mobileplatform=ParseJson.jsonparsing(info).get("mobilePlatform"); String
	 * phone=ParseJson.jsonparsing(info).get("phone"); String
	 * gcminfo=ParseJson.jsonparsing(info).get("gcmInfo"); String
	 * apnsinfo=ParseJson.jsonparsing(info).get("apnsInfo"); String
	 * userlevel=ParseJson.jsonparsing(info).get("userLevel"); String
	 * notifyfilter=ParseJson.jsonparsing(info).get("notifyFilter");
	 * if(notifyfilter!=null&&mobileplatform!=null&&phone!=null&&gcminfo!=null&&
	 * apnsinfo!=null){
	 * 
	 * Method.pushusertbluserdelete(phone, gcminfo, apnsinfo, mobileplatform,
	 * userlevel,notifyfilter); return
	 * Response.ok(" delete success!!").status(200).build(); }else { return
	 * Response.ok(" delete failure!!").status(400).build(); }
	 * 
	 * } //����ȣ�� ���Ͽ� ��������
	 * 
	 * @GET
	 * 
	 * @Path("entry/phonenumber/{phone}") public Response
	 * getbyphonenumber(@Context HttpServletRequest request,@PathParam("phone")
	 * String phone) throws UnsupportedEncodingException, IOException {
	 * 
	 * if(Method.Selectbyphonenumber(phone).toString("utf-8").length()>29){
	 * return
	 * Response.ok(Method.Selectbyphonenumber(phone).toString("utf-8")).status(
	 * 200).build(); }else{ return
	 * Response.ok("please check phonenumber").status(400).build(); } }
	 * //����ȣ�� ���Ͽ� ��������
	 * 
	 * @DELETE
	 * 
	 * @Path("entry/phonenumber/{phone}") public Response
	 * deletebyphonenumber(@Context HttpServletRequest
	 * request,@PathParam("phone") String phone) throws
	 * UnsupportedEncodingException, IOException {
	 * 
	 * if(phone!=null){ Method.pushusertbluserdeletebyphone(phone); return
	 * Response.ok("successful!").status(200).build(); }else{ return
	 * Response.ok("please check phonenumber").status(400).build(); }
	 * 
	 * }
	 * 
	 * //�� �����(os)�� ���Ͽ� ��������
	 * 
	 * @GET
	 * 
	 * @Path("entry/mobileplatform/{mobileplatform}") public Response
	 * getbymobileplatform(@Context HttpServletRequest
	 * request,@PathParam("mobileplatform") String mobileplatform) throws
	 * UnsupportedEncodingException, IOException {
	 * 
	 * if(Method.SelectbymobilePlatform(mobileplatform).toString("utf-8").length
	 * ()>29){ return
	 * Response.ok(Method.SelectbymobilePlatform(mobileplatform).toString(
	 * "utf-8")).status(200).build(); }else{ return
	 * Response.ok("please check mobileplatform").status(400).build(); } }
	 * //����ڵ���� ���Ͽ� ��������
	 * 
	 * @GET
	 * 
	 * @Path("entry/userlevel/{userlevel}") public Response
	 * getbyuserlevel(@Context HttpServletRequest
	 * request,@PathParam("userlevel") String userlevel) throws
	 * UnsupportedEncodingException, IOException {
	 * 
	 * if(Method.SelectbyuserLevel(userlevel).toString("utf-8").length()>29){
	 * return
	 * Response.ok(Method.SelectbyuserLevel(userlevel).toString("utf-8")).status
	 * (200).build(); }else{ return
	 * Response.ok("please check userlevel").status(400).build(); } } //����
	 * ��������
	 * 
	 * @GET
	 * 
	 * @Path("entry/all") public Response getpushusertblbyall(@Context
	 * HttpServletRequest request) throws UnsupportedEncodingException,
	 * IOException {
	 * 
	 * if(Method.Selectbyall().toString("utf-8").length()>29){ return
	 * Response.ok(Method.Selectbyall().toString("utf-8")).status(200).build();
	 * }else{ return
	 * Response.ok("please check databases!!").status(400).build(); } }
	 */
}
