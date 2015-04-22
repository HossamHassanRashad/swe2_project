package com.FCI.SWE.Controller;

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

import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.UserModel.User;
import com.sun.jndi.ldap.Connection;
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
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	@POST
	@Path("/accept")
	@Produces("text/html")
	public Response search( @FormParam("myemail") String myemail, @FormParam("searchName") String email) {
		System.out.println(email);
		String serviceUrl = "http://localhost:8888/rest/searchFriend";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "email=" + email+"&myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", object.get("name").toString());
			map.put("email", object.get("email").toString());
			map.put("myemail", object.get("myemail").toString());
			return Response.ok(new Viewable("/jsp/result", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
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
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			//UserEntity user = UserEntity.getUser(object.toJSONString());
			User.getUser(retJson);
			User.getCurrentActiveUser();
			map.put("name", User.getCurrentActiveUser().getName());
			map.put("email", User.getCurrentActiveUser().getEmail());
			map.put("mosthashtags", object.get("mosthashtags").toString());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}

	
	@POST
	@Path("/sendRequest")
	@Produces("text/html")
	public Response sendRequest(@FormParam("myemail") String myemail,@FormParam("email") String email) {
		System.out.println("controller "+email);
		String serviceUrl = "http://localhost:8888/rest/sendRq";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "email=" + email+"&myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			return Response.ok(new Viewable("/jsp/addedFriend", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
	
	@POST
	@Path("/checkRequest")
	@Produces("text/html")
	public Response checkRequest(@FormParam("myemail") String myemail) {
		System.out.println("check request controller");
		String serviceUrl = "http://1-dot-socialnetwork-swe2.appspot.com/rest/checkRq";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			return Response.ok(new Viewable("/jsp/checkRequest", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
	
	@POST
	@Path("/acceptRequest")
	@Produces("text/html")
	public Response acceptRequest(@FormParam("myemail") String myemail,@FormParam("email") String email) {
		System.out.println("accept request controller");
		String serviceUrl = "http://1-dot-socialnetwork-swe2.appspot.com/rest/acceptRq";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "email=" + email+"&myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			return Response.ok(new Viewable("/jsp/acceptRequest", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}

//************************************phase 2-a**************************
	@POST
	@Path("/sendMsg")
	@Produces("text/html")
	public Response sendMsg(@FormParam("myemail") String myemail,@FormParam("email") String email,@FormParam("msg") String msg) {
		String serviceUrl = "http://localhost:8888/rest/sendMsg";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "email=" + email+"&myemail=" + myemail + "&msg=" + msg;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			map.put("msg", object.get("msg").toString());
			return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
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
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "email=" + email+"&myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		System.out.println(object.get("msg").toString());
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			map.put("email", object.get("email").toString());
			map.put("msg", object.get("msg").toString());
			return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
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
			try {
				URL url = new URL(serviceUrl);
				String urlParameters = "email=" + email+"&myemail=" + myemail;
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(60000);  //60 Seconds
				connection.setReadTimeout(60000);  //60 Seconds
				
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				String line, retJson = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					retJson += line;
				}
				writer.close();
				reader.close();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("Failed"))
					return null;
				Map<String, String> map = new HashMap<String, String>();
				map.put("myemail", object.get("myemail").toString());
				map.put("email", object.get("email").toString());
				map.put("msg", object.get("msg").toString());
				return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			}
		
	@POST
	@Path("/sendMSG")
	public Response sendMSG() {
		System.out.println("wsl wsl wsl");
		return Response.ok(new Viewable("/jsp/sendMsg")).build();
	}
	@POST
	@Path("/getnotification")
	public Response getnotification(@FormParam("myemail") String myemail){
		String serviceUrl = "http://localhost:8888/rest/getnotification";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("myemail", object.get("myemail").toString());
			
			return Response.ok(new Viewable("/jsp/sendMsg", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//*************************phase 2-b ***************************************
	@POST
	@Path("/createPost")
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(@FormParam("myemail") String myemail,@FormParam("email") String email,
			@FormParam("content") String content, @FormParam("feel") String feel,@FormParam("privacy") String privacy) {
		
		System.out.println("myemail: "+ myemail + "email: "+ email);
		String serviceUrl = "http://localhost:8888/rest/createPost";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&email=" + email + "&content=" + content
					+ "&feel=" + feel + "&privacy=" + privacy;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "created Successfully";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}
	
	
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
		System.out.println("email: "+myemail);
		System.out.println("name: "+name);
		System.out.println("type: "+type);
		System.out.println("category: "+category);
		
		String serviceUrl = "http://localhost:8888/rest/createPage";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&name=" + name + "&type=" + type + "&category=" + category;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "page created Successfully";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
		
	}
	
	@POST
	@Path("/searchPage")
	@Produces("text/html")
	public Response searchPage( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		System.out.println(name);
		String serviceUrl = "http://localhost:8888/rest/searchPage";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&name=" + name;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			System.out.println(object.get("name").toString());
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", object.get("name").toString());
			map.put("category", object.get("category").toString());
			map.put("type", object.get("type").toString());
			map.put("likes", object.get("likes").toString());
			map.put("owner", object.get("owner").toString());
			map.put("myemail", object.get("myemail").toString());
			return Response.ok(new Viewable("/jsp/result1", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}


	@POST
	@Path("/likePage")
	@Produces("text/html")
	public Response likePage( @FormParam("myemail") String myemail, @FormParam("name") String name) {
		System.out.println(name);
		String serviceUrl = "http://localhost:8888/rest/likePage";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&name=" + name;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			System.out.println(object.get("name").toString());
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", object.get("name").toString());
			map.put("category", object.get("category").toString());
			map.put("type", object.get("type").toString());
			map.put("likes", object.get("likes").toString());
			map.put("owner", object.get("owner").toString());
			map.put("myemail", object.get("myemail").toString());
			return Response.ok(new Viewable("/jsp/result1", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@POST
	@Path("/viewUserTimeline")
	@Produces("text/html")
	public Response viewUserTimeLine( @FormParam("myemail") String myemail, @FormParam("email") String email) {
		System.out.println(email);
		String serviceUrl = "http://localhost:8888/rest/viewUserTimeline";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&email=" + email;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String retJson = "",line; 
			//connection.connect(serviceUrl, urlParameters,"POST","application/x-www-form-urlencoded;charset=UTF-8");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			System.out.println("retjson: "+retJson);
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray object = (JSONArray) parser.parse(retJson);
			System.out.println("parsing");
			Map<String, Vector<Post>> map = new HashMap<String, Vector<Post>>();
			Vector <Post> vecpost = new Vector<Post>();
			for(int i = 0 ; i < object.size() ; i++){
				JSONObject o;
				o = (JSONObject) object.get(i);
				vecpost.add(Post.parsePostinfo(o.toJSONString()));
			}
			map.put("postlist", vecpost);
			System.out.println("vector result: "+vecpost.get(0).getFeel());
			return Response.ok(new Viewable("/jsp/userTimeline", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@POST
	@Path("/likePost")
	public String likePost( @FormParam("myemail") String myemail,@FormParam("postID") String postID){
		System.out.println(postID);
		String serviceUrl = "http://localhost:8888/rest/likePost";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&postID=" + postID;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("status").equals("Failed"))
				return null;
			return "you liked the post successfully";
					} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@POST
	@Path("/viewSearchTimeline")
	@Produces("text/html")
	public Response viewSearchTimeline( @FormParam("myemail") String myemail, @FormParam("email") String email) {
		System.out.println("controller fel awel myemail: "+ myemail + "controller fel awl email: "+ email);
		String serviceUrl = "http://localhost:8888/rest/viewSearchTimeline";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myemail=" + myemail + "&email=" + email;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String retJson = "",line; 
			//connection.connect(serviceUrl, urlParameters,"POST","application/x-www-form-urlencoded;charset=UTF-8");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			System.out.println("retjson: "+retJson);
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray object = (JSONArray) parser.parse(retJson);
			System.out.println("parsing");
			Map<String, Vector<Post>> map = new HashMap<String, Vector<Post>>();
			Vector <Post> vecpost = new Vector<Post>();
			for(int i = 0 ; i < object.size() ; i++){
				JSONObject o;
				o = (JSONObject) object.get(i);
				vecpost.add(Post.parsePostinfo(o.toJSONString()));
			}
			map.put("postlist", vecpost);
//			Map<String, String> map2 = new HashMap<String, String>();
//			map2.put("myemail", myemail);
//			map2.put("email", email);
			Vector <Post> searchpost = new Vector<Post>();
			Post p2 = new Post();
			p2.setTo(email);
			p2.setOwner(myemail);
			System.out.println("controller fel tany myemail: "+ myemail + "controller fel tany email: "+ email);
			System.out.println("p2 owner: "+p2.getOwner()+"p2 to: "+p2.getTo());
			searchpost.add(p2);
			map.put("emaillist", searchpost);
			return Response.ok(new Viewable("/jsp/userTimeline", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}