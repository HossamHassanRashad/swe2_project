package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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

import com.FCI.SWE.Models.Post;
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
public class UserController{
	
	/**
	 * Action function to render Signout page, this function will be executed
	 * using url like this /rest/signout
	 * 
	 * @return  enty point page (Home page of this application)
	 */
	@POST
	@Path("/signout")
	public Response signout() {
		User.signout();
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	/**
	 * Action function to render Signout page, this function will be executed
	 * using url like this /rest/signout
	 * 
	 * @return  enty point page (Home page of this application)
	 */
	@POST
	@Path("/search")
	public Response search() {
		return Response.ok(new Viewable("/jsp/searchRes")).build();
	}
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		
		return Response.ok(new Viewable("/jsp/register")).build();
	}	
	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
			String retJson = Connection.connect(
					"http://localhost:8888/rest/RegistrationService", urlParameters,
					"POST", "application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			Object obj;
			try {
				// System.out.println(retJson);
				obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("OK"))
					return "Registered Successfully";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "Failed";
	}

	@POST
	@Path("/accept")
	@Produces("text/html")
	public Response search( @FormParam("myemail") String myemail, @FormParam("searchName") String email) {
		System.out.println(email);
		String serviceUrl = "http://localhost:8888/rest/searchFriend";
		String urlParameters = "email=" + email+"&myemail=" + myemail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/RegistrationService", urlParameters,
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
				map.put("email", object.get("email").toString());
				map.put("myemail", object.get("myemail").toString());
				return Response.ok(new Viewable("/jsp/result", map)).build();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;

	}
	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String serviceUrl = "http://localhost:8888/rest/LoginService";
		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/RegistrationService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		Object obj;
		JSONParser parser = new JSONParser();
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User.getUser(retJson);
			User.getCurrentActiveUser();
			map.put("name", User.getCurrentActiveUser().getName());
			map.put("email", User.getCurrentActiveUser().getEmail());
			map.put("mosthashtags", object.get("mosthashtags").toString());
			return Response.ok(new Viewable("/jsp/home", map)).build();
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;

	}

	
	@POST
	@Path("/sendRequest")
	@Produces("text/html")
	public Response sendRequest(@FormParam("myemail") String myemail,@FormParam("email") String email) {
		System.out.println("controller "+email);
		String serviceUrl = "http://localhost:8888/rest/sendRq";
		String urlParameters = "email=" + email+"&myemail=" + myemail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendRq", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj ;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			return Response.ok(new Viewable("/jsp/addedFriend", map)).build();
			}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}
	
	@POST
	@Path("/checkRequest")
	@Produces("text/html")
	public Response checkRequest(@FormParam("myemail") String myemail) {
		String serviceUrl = "http://1-dot-socialnetwork-swe2.appspot.com/rest/checkRq";
		String urlParameters = "myemail=" + myemail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/checkRq", urlParameters,
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
			return Response.ok(new Viewable("/jsp/checkRequest", map)).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
}
	
	@POST
	@Path("/acceptRequest")
	@Produces("text/html")
	public Response acceptRequest(@FormParam("myemail") String myemail,@FormParam("email") String email) {
		String serviceUrl = "http://1-dot-socialnetwork-swe2.appspot.com/rest/acceptRq";
		String urlParameters = "email=" + email+"&myemail=" + myemail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/acceptRq", urlParameters,
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
			return Response.ok(new Viewable("/jsp/acceptRequest", map)).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
	}


	
	
	@POST
	@Path("/viewUserTimeline")
	@Produces("text/html")
	public Response viewUserTimeLine( @FormParam("myemail") String myemail, @FormParam("email") String email) {
		String urlParameters = "myemail=" + myemail + "&email=" + email;
		String serviceUrl = "http://localhost:8888/rest/viewUserTimeline";
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewUserTimeline", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			JSONArray object = (JSONArray) parser.parse(retJson);
			Map<String, Vector<Post>> map = new HashMap<String, Vector<Post>>();
			Vector <Post> vecpost = new Vector<Post>();
			for(int i = 0 ; i < object.size() ; i++){
				JSONObject o;
				o = (JSONObject) object.get(i);
				vecpost.add(Post.parsePostinfo(o.toJSONString()));
			}
			map.put("postlist", vecpost);
			return Response.ok(new Viewable("/jsp/userTimeline", map)).build();
			}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}		
		return null;
	}
	
	
	@POST
	@Path("/viewSearchTimeline")
	@Produces("text/html")
	public Response viewSearchTimeline( @FormParam("myemail") String myemail, @FormParam("email") String email) {
		String serviceUrl = "http://localhost:8888/rest/viewSearchTimeline";
		String urlParameters = "myemail=" + myemail + "&email=" + email;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewSearchTimeline", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONArray object;
		try {
			object = (JSONArray) parser.parse(retJson);
			Map<String, Vector<Post>> map = new HashMap<String, Vector<Post>>();
			Vector <Post> vecpost = new Vector<Post>();
			for(int i = 0 ; i < object.size() ; i++){
				JSONObject o;
				o = (JSONObject) object.get(i);
				vecpost.add(Post.parsePostinfo(o.toJSONString()));
			}
			map.put("postlist", vecpost);
			Vector <Post> searchpost = new Vector<Post>();
			Post p2 = new Post();
			p2.setTo(email);
			p2.setOwner(myemail);
			searchpost.add(p2);
			map.put("emaillist", searchpost);
			return Response.ok(new Viewable("/jsp/userTimeline", map)).build();
		
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}