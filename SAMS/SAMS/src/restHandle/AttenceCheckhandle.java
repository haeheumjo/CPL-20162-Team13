package restHandle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import JDBC.Method;

@Path("entry")
public class AttenceCheckhandle {
	@GET
	@Path("attencecheck/all")
	public Response getpushusertblbyall(@Context HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		//if(Method.Selectallattencheck().toString("utf-8").length()>29){
		//return Response.ok(Method.Selectallattencheck().toString("utf-8")).status(200).build();
		
		HttpSession session = request.getSession();
		String rid = (String)session.getAttribute("rid");
		System.out.println("get from session : "+rid);
		if(Method.SelectRecord(rid).toString("utf-8").length()>29){
			System.out.println("HEHEHE: "+rid);
			return Response.ok(Method.SelectRecord(rid).toString("utf-8")).status(200).build();
		
	}else{
		return Response.ok("please check databases!!").status(400).build();
	}
	}
	@GET
	@Path("attencecheck/getrecordno")
	public Response getrecordid(@Context HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		HttpSession session = request.getSession();
		String rid = (String)session.getAttribute("rid");
		System.out.println("getrecordid: "+rid);
		System.out.println("get from session : "+rid);
		if(((String)session.getAttribute("rid") != null)&&!((String)session.getAttribute("rid")).equals("")){
			System.out.println("HEHEHE123123: "+rid);
			return Response.ok(rid).status(200).build();
		}else{
			return Response.ok("please check databases!!").status(400).build();
	}
		
	}
	@GET
	@Path("attencecheck/button")
	public Response getAttendanceButtonValue(@Context HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		HttpSession session = request.getSession();
		String valueOfButton = null;
		valueOfButton =(String)session.getAttribute("attenceStatus");
		System.out.println("get from session : "+ valueOfButton);
		if(((String)session.getAttribute("attenceStatus") != null)&&!((String)session.getAttribute("attenceStatus")).equals("")){
			System.out.println("HEHEHE: "+valueOfButton);
			return Response.ok(valueOfButton).status(200).build();
		}
		else{
			return Response.ok("start").status(400).build();
		}
	}
}
