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

public class PageController {


	@POST
	@Path("/createPagehome")
	public Response createpagehome(@FormParam("myemail") String myemail){
		Map<String, String> map = new HashMap<String, String>();
		map.put("myemail", myemail);
		return Response.ok(new Viewable("/jsp/createpagehome",map)).build();
	}
	
	@POST
	@Path("/createPage")
	public String createPage(@FormParam("myemail") String myemail,
			@FormParam("name") String name, @FormParam("type") String type,@FormParam("category") String category) {
		String urlParameters = "myemail=" + myemail + "&name=" + name + "&type=" + type + "&category=" + category;
		String serviceUrl = "http://localhost:8888/rest/createPage";
		String retJson = Connection.connect(
				"http://localhost:8888/rest/createPage", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "page created Successfully";
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}
	
	@POST
	@Path("/searchPage")
	@Produces("text/html")
	public Response searchPage( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		String serviceUrl = "http://localhost:8888/rest/searchPage";
		String urlParameters = "myemail=" + myemail + "&name=" + name;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/searchPage", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", object.get("name").toString());
			map.put("category", object.get("category").toString());
			map.put("type", object.get("type").toString());
			map.put("likes", object.get("likes").toString());
			map.put("owner", object.get("owner").toString());
			map.put("myemail", object.get("myemail").toString());
			return Response.ok(new Viewable("/jsp/result1", map)).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@POST
	@Path("/likePage")
	@Produces("text/html")
	public Response likePage( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		System.out.println(name);
		String serviceUrl = "http://localhost:8888/rest/likePage";
		String urlParameters = "myemail=" + myemail + "&name=" + name;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/likePage", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", object.get("name").toString());
			map.put("category", object.get("category").toString());
			map.put("type", object.get("type").toString());
			map.put("likes", object.get("likes").toString());
			map.put("owner", object.get("owner").toString());
			map.put("myemail", object.get("myemail").toString());
			return Response.ok(new Viewable("/jsp/result1", map)).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
}
