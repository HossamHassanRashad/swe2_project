package frontEndBackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.FormParam;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


@Test(groups="Service")
public class serviceTest {
	private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

		  @BeforeTest
		  public void setUp() {
		    helper.setUp();
		  }

		  @AfterTest
		  public void tearDown() {
		    helper.tearDown();
		  }
	UserEntity userEntity ;
	
  @DataProvider(name = "registrationServiceTest")
	  public static Object[][]registrationServiceTest(){
		return new Object[][] {{"OK","aml3","aml3","123"},{"OK","emy3","emy3","123"}};
	  }
  @Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="registrationServiceTest")
  public void registrationService(String res,String uname,String email,String pass) throws IOException, ParseException {
	  String serviceUrl = "http://localhost:8888/rest/RegistrationService";
	  URL url;
	try {
		url = new URL(serviceUrl);
	
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
	  
	  Assert.assertEquals(res,object.get("Status").toString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
  }
  
  @DataProvider(name = "sendRequestTest")
  public static Object[][]sendRequestTest(){
	return new Object[][] {{"OK","aml3","emy3"}};
  }
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="sendRequestTest")
public void sendRequest(String res,String myemail,String email) throws IOException, ParseException {
  String serviceUrl = "http://localhost:8888/rest/sendRequest";
  URL url;
try {
	url = new URL(serviceUrl);
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
  
  Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}
@DataProvider(name = "searchFriendTest")
public static Object[][]searchFriendTest(){
	return new Object[][] {{"OK","emy3","aml3"},{"OK","aml3","emy3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="searchFriendTest")
public void searchFriend(String res,String myemail,String email) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/searchFriend";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}
@DataProvider(name = "LoginServiceTest")
public static Object[][]LoginServiceTest(){
	return new Object[][] {{"OK","emy3","123"},{"OK","aml3","123"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="LoginServiceTest")
public void LoginService(String res,String uname,String pass) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/LoginService";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "checkRqTest")
public static Object[][]checkRqTest(){
	return new Object[][] {{"OK","emy3"},{"OK","aml3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="checkRqTest")
public void checkRq(String res,String myemail) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/checkRq";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "acceptRqTest")
public static Object[][]acceptRqTest(){
	return new Object[][] {{"OK","emy3"},{"OK","aml3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="acceptRqTest")
public void acceptRq(String res,String myemail) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/acceptRq";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "sendMsgTest")
public static Object[][]sendMsgTest(){
	return new Object[][] {{"OK","emy3","aml3","hi"},{"OK","aml3","emy3","ezayk"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="sendMsgTest")
public void sendMsg(String res,String myemail,String email,String msg) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/sendMsg";
URL url;
try {
	url = new URL(serviceUrl);
	String urlParameters = "myemail=" + myemail + "&email=" + email + "&msg=" + msg;
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "showNewMsgTest")
public static Object[][]showNewMsgTest(){
	return new Object[][] {{"OK","emy3","aml3"},{"OK","aml3","emy3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="showNewMsgTest")
public void showNewMsg(String res,String email,String myemail) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/showNewMsg";
URL url;
try {
	url = new URL(serviceUrl);
	String urlParameters = "email=" + email + "&myemail=" + myemail;
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "showAllMsgTest")
public static Object[][]showAllMsgTest(){
	return new Object[][] {{"OK","emy3","aml3"},{"OK","aml3","emy3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="showAllMsgTest")
public void showAllMsg(String res,String email,String myemail) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/showAllMsg";
URL url;
try {
	url = new URL(serviceUrl);
	String urlParameters = "email=" + email + "&myemail=" + myemail;
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "createpostTest")
public static Object[][]createpostTest(){
	return new Object[][] {{"OK","emy3","emy3","ya rb n3dy safy","hopeful","public"},{"OK","emy3","aml3","brd gaaamd","sick","aml3"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="createpostTest")
public void createpost(String res,String myemail,String email,String content, String feel,String privacy) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/createPost";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

@DataProvider(name = "createPageTest")
public static Object[][]createPageTest(){
	return new Object[][] {{"OK","emy3","aasrYasenLovers","actor","media"},{"OK","aml3","yaserGalalLovers","actor","media"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="createPageTest")
public void createPage(String res,String myemail,String name,String type,String category) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/createPage";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}


@DataProvider(name = "searchPageTest")
public static Object[][]searchPageTest(){
	return new Object[][] {{"OK","aml3","aasrYasenLovers"},{"OK","emy3","yaserGalalLovers"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="searchPageTest")
public void searchPage(String res,String myemail,String name) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/searchPage";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}


@DataProvider(name = "likePageServiceTest")
public static Object[][]likePageServiceTest(){
	return new Object[][] {{"OK","aml3","aasrYasenLovers"},{"OK","emy3","yaserGalalLovers"}};
}
@Test(groups="Service",dependsOnGroups="UserEntityTest",dataProvider ="likePageServiceTest")
public void likePageService(String res,String myemail,String name) throws IOException, ParseException {
String serviceUrl = "http://localhost:8888/rest/likePage";
URL url;
try {
	url = new URL(serviceUrl);
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

Assert.assertEquals(res,object.get("Status").toString());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
}

}
