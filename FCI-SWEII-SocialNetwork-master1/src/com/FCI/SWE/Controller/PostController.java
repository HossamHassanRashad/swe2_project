package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PostController {
	@POST
	@Path("/likePost")
	public String likePost( @FormParam("myemail") String myemail,@FormParam("postID") String postID){
		String urlParameters = "myemail=" + myemail + "&postID=" + postID;
		String serviceUrl = "http://localhost:8888/rest/likePost";
		String retJson = Connection.connect(
				"http://localhost:8888/rest/likePost", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("status").equals("Failed"))
				return null;
			return "you liked the post successfully";
			}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@POST
	@Path("/createPost")
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(@FormParam("myemail") String myemail,@FormParam("email") String email,
			@FormParam("content") String content, @FormParam("feel") String feel,@FormParam("privacy") String privacy) {
	String urlParameters = "myemail=" + myemail + "&email=" + email + "&content=" + content
				+ "&feel=" + feel + "&privacy=" + privacy;	
	String retJson = Connection.connect(
			"http://localhost:8888/rest/createPost", urlParameters,
			"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		String serviceUrl = "http://localhost:8888/rest/createPost";
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "created Successfully";
		
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}
	
}
