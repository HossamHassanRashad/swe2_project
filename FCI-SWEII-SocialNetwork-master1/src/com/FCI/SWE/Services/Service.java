package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.PostBuilder;
import com.FCI.SWE.Models.PostEntity;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;
import com.FCI.SWE.UserModel.User;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service{
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
	
	@POST
	@Path("/searchFriend")
	public String searchService( @FormParam("myemail") String myemail, @FormParam("email") String email) {
		JSONObject object = new JSONObject();
		//User.getUser(fname);
		UserEntity user = UserEntity.getSearch(myemail,email);
		System.out.println("returned object"+ user.getEmail());
		if (user.equals(null)) {
			object.put("Status", "Failed");

		} else{
		object.put("Status", "OK");
		object.put("name", user.getName());
		object.put("email", user.getEmail());
		object.put("myemail", myemail);
		}
		return object.toString();
	}
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		String mosthashtags = PostEntity.getMostTags();
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("ID", user.getID());
			object.put("mosthashtags", mosthashtags);
		}

		return object.toString();

	}
	@POST
	@Path("/sendRq")
	public String sendRq(@FormParam("myemail") String myemail ,@FormParam("email") String email) {
		System.out.println("email from searvice "+email);
		JSONObject object = new JSONObject();
		Boolean added= UserEntity.saveRequest(myemail,email,"pending");
		if (added.equals(null)||added.equals(false)) {
			object.put("Status", "Failed");
			System.out.println("false from DB in service");
		}else{
		System.out.println("d5l l else");
		object.put("Status", "OK");
		object.put("email", email);
		object.put("myemail", myemail);
		}
		return object.toString();
	}
	@POST
	@Path("/checkRq")
	public String checkRq(@FormParam("myemail") String myemail) {
		JSONObject object = new JSONObject();
		String checked= UserEntity.checkRequest(myemail);
		System.out.println("checkRq service");
		if (checked.equals(null)) {
			object.put("Status", "Failed");

		} else{
		object.put("Status", "OK");
		object.put("email", checked);
		object.put("myemail", myemail);
		}
		return object.toString();
	}
	@POST
	@Path("/acceptRq")
	public String acceptRq(@FormParam("myemail") String myemail) {
		JSONObject object = new JSONObject();
		String accepted= UserEntity.acceptRequest(myemail);
		System.out.println("acceptRq service");
		if (accepted.equals(null)) {
			System.out.println("l DB rgg3t null");
			object.put("Status", "Failed");

		} else{
		System.out.println("l DB rgg3t email");
		object.put("Status", "OK");
		object.put("email", accepted);
		object.put("myemail", myemail);
		}
		return object.toString();
	}
	//***************************************phase2-a***************
	@POST
	@Path("/sendMsg")
	public String sendMsg(@FormParam("myemail") String myemail ,@FormParam("email") String email,@FormParam("msg") String msg) {
		System.out.println("msg from searvice "+msg);
		JSONObject object = new JSONObject();
		Boolean added= UserEntity.saveMsg(myemail,email,msg);
		System.out.println("back from db");
		object.put("Status", "OK");
		object.put("email", email);
		object.put("myemail", myemail);
		object.put("msg", msg);
		return object.toString();
	}
	
	@POST
	@Path("/showNewMsg")
	public String showNewMsg(@FormParam("myemail") String myemail ,@FormParam("email") String email) {
		JSONObject object = new JSONObject();
		String added= UserEntity.showNewMsg(myemail,email);
		System.out.println("back from db");
		object.put("Status", "OK");
		object.put("email", email);
		object.put("myemail", myemail);
		object.put("msg", added);
		return object.toString();
	}
	
	@POST
	@Path("/showAllMsg")
	public String showAllMsg(@FormParam("myemail") String myemail ,@FormParam("email") String email) {
		JSONObject object = new JSONObject();
		String added= UserEntity.showAllMsg(myemail,email);
		System.out.println("back from db");
		object.put("Status", "OK");
		object.put("email", email);
		object.put("myemail", myemail);
		object.put("msg", added);
		return object.toString();
	}
	
//************ phase 2-b*******
	@POST
	@Path("/createPost")
	public String createpost(@FormParam("myemail") String myemail,@FormParam("email") String email,
			@FormParam("content") String content, @FormParam("feel") String feel,@FormParam("privacy") String privacy) {
		
		System.out.println("myemail: "+ myemail + "email: "+ email);
		PostBuilder obj = new UserPost();
		obj.setContent(content);
		obj.setOwner(myemail);
		obj.setTo(email);
		obj.setLikes(0);
		obj.setFeel(feel);
		obj.setPrivacy(privacy);
		obj.Build();
		Post postObj = null;
		postObj.setContent(content);
		postObj.setOwner(myemail);
		postObj.setTo(email);
		postObj.setLikes(0);
		postObj.setFeel(feel);
		postObj.setPrivacy(privacy);
		
		
		boolean create = PostEntity.createPost(postObj);
		System.out.println("ntegt l create "+create);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		object.put("myemail", myemail);
		object.put("email", email);
		object.put("content", content);
		object.put("feel", feel);
		object.put("Privacy", privacy);
		return object.toString();
	}
	
	@POST
	@Path("/createPage")
	public String createPage(@FormParam("myemail") String myemail,
			@FormParam("name") String name, @FormParam("type") String type,@FormParam("category") String category) {
		
		System.out.println("myemail : "+myemail);
		
		boolean create = UserEntity.createPage(myemail, name, type, category);
		System.out.println("ntegt l create "+create);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		object.put("myemail", myemail);
		object.put("name", name);
		object.put("type", type);
		object.put("category", category);
		
		return object.toString();
	}
	@POST
	@Path("/searchPage")
	public String searchPageService( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		JSONObject object = new JSONObject();
		//User.getUser(fname);
		PageEntity page = PageEntity.getPageSearch(myemail, name);
		System.out.println(page.getName());
		if (page.equals(null)) {
			object.put("Status", "Failed");

		} else{
		object.put("Status", "OK");
		object.put("name", page.getName());
		object.put("category", page.getCategory());
		object.put("type", page.getType());
		object.put("likes", page.getLikes());
		object.put("owner", page.getOwner());
		object.put("myemail", myemail);
		System.out.println(object.get("name").toString());
		}
		return object.toString();
	}
	
	@POST
	@Path("/likePage")
	public String likePageService( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		JSONObject object = new JSONObject();
		//User.getUser(fname);
		PageEntity page = PageEntity.likePage(myemail, name);
		System.out.println(page.getName());
		if (page.equals(null)) {
			object.put("Status", "Failed");

		} else{
		object.put("Status", "OK");
		object.put("name", page.getName());
		object.put("category", page.getCategory());
		object.put("type", page.getType());
		object.put("likes", page.getLikes());
		object.put("owner", page.getOwner());
		object.put("myemail", myemail);
		System.out.println(object.get("name").toString());
		}
		return object.toString();
	}
	
	
	@POST
	@Path("/viewUserTimeline")
	public String viewUserTimeline( @FormParam("myemail") String myemail,@FormParam("email") String email){
		Vector<PostEntity> res = PostEntity.viewUserTimeline(email);
		JSONArray returnedJSON = new JSONArray();
		System.out.println("feel: "+res.get(0).getFeel());
		
		for(PostEntity p: res){
			JSONObject obj = new JSONObject();
			obj.put("content", p.getContent());
			obj.put("owner", p.getOwner());
			obj.put("to", p.getTo());
			obj.put("likes", p.getLikes());
			obj.put("feel", p.getFeel());
			obj.put("postID", p.getPostID());
			obj.put("myemail", myemail);
			obj.put("Privacy", p.getPrivacy());
			returnedJSON.add(obj);
		}
		System.out.println("viewUserTimeline: "+ returnedJSON.toJSONString());
		return returnedJSON.toJSONString();
	}
	
	@POST
	@Path("/likePost")
	public String likePost( @FormParam("myemail") String myemail,@FormParam("postID") String postID){
		boolean res = PostEntity.likePost(postID);
		System.out.println("res: "+res);
			JSONObject obj = new JSONObject();
			obj.put("status", "ok");
			return obj.toJSONString();
	}


	@POST
	@Path("/viewSearchTimeline")
	public String viewSearchTimeline( @FormParam("myemail") String myemail,@FormParam("email") String email){
		Vector<PostEntity> res = PostEntity.searchuserTimeline(email);
		JSONArray returnedJSON = new JSONArray();
		System.out.println("myemail: "+ myemail + "email: "+ email);
		
		for(PostEntity p: res){
			JSONObject obj = new JSONObject();
			obj.put("content", p.getContent());
			obj.put("owner", p.getOwner());
			obj.put("to", p.getTo());
			obj.put("likes", p.getLikes());
			obj.put("feel", p.getFeel());
			obj.put("Privacy", p.getPrivacy());
			obj.put("postID", p.getPostID());
			obj.put("myemail", myemail);
			returnedJSON.add(obj);
		}
		System.out.println("viewUserTimeline: "+ returnedJSON.toJSONString());
		return returnedJSON.toJSONString();
	}
}