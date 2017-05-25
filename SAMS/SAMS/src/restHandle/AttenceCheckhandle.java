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
			return Response.ok(Method.SelectRecord("r_001").toString("utf-8")).status(200).build();
		
	}else{
		return Response.ok("please check databases!!").status(400).build();
	}
	}
}
