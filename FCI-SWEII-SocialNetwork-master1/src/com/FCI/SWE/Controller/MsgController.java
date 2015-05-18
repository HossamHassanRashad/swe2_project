package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MsgController {

	
	@POST
	@Path("/sendMsg")
	@Produces("text/html")
	public Response sendMsg(@FormParam("myemail") String myemail,@FormParam("email") String email,@FormParam("msg") String msg) {
		String serviceUrl = "http://localhost:8888/rest/sendMsg";
		String urlParameters = "email=" + email+"&myemail=" + myemail + "&msg=" + msg;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendMsg", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			map.put("msg", object.get("msg").toString());
			return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}
//****************************
	@POST
	@Path("/showNewMsg")
	@Produces("text/html")
	public Response showNewMsg(@FormParam("myemail") String myemail,@FormParam("email") String email) {
		String serviceUrl = "http://localhost:8888/rest/showNewMsg";
		String urlParameters = "email=" + email+"&myemail=" + myemail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/showNewMsg", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			map.put("msg", object.get("msg").toString());
			return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
		
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}
	//****************************
		@POST
		@Path("/showAllMsg")
		@Produces("text/html")
		public Response showAllMsg(@FormParam("myemail") String myemail,@FormParam("email") String email) {
			String serviceUrl = "http://localhost:8888/rest/showAllMsg";
			String urlParameters = "email=" + email+"&myemail=" + myemail;
			String retJson = Connection.connect(
					"http://localhost:8888/rest/showAllMsg", urlParameters,
					"POST", "application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("Failed"))
					return null;
				Map<String, String> map = new HashMap<String, String>();
				map.put("myemail", object.get("myemail").toString());
				map.put("email", object.get("email").toString());
				map.put("msg", object.get("msg").toString());
				return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
			
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			}
		
	@POST
	@Path("/sendMSG")
	public Response sendMSG() {
		return Response.ok(new Viewable("/jsp/sendMsg")).build();
	}
}
