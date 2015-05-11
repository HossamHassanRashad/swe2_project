package backEndTestBackage;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
@Test(groups="UserEntityTest")

public class UserEntityTest {
	private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

		  @BeforeTest
		  public void setUp() {
		    helper.setUp();
		  }

		  /*@AfterTest
		  public void tearDown() {
		    helper.tearDown();
		  }*/
	UserEntity userEntity ;
	
//	  @DataProvider(name = "getsearchTest2")
//	  public static Object[][]getsearchTest21(){
//	  	return new Object[][] {{true,"emy2","emy2"} , {false,"aa","hossam2"}};
//	  }
//	  
//	  @Test(dataProvider ="getsearchTest2")
//	  public void getSearch2(boolean res,String myemail,String email){
//		  Assert.assertEquals(res, UserEntity.getSearch2(myemail, email));
//
//	}
//	
//	@DataProvider(name = "getUserTest")
//	  public static Object[][]getUserTest(){
//	  	return new Object[][] {{new UserEntity("aml2","aml2","123"),"aml2","123"} ,{new UserEntity("emy2","emy2","123"),"emy2","123"}};
//	  }
//	  
//	  @Test(dataProvider ="getUserTest")
//	  public void getUser(UserEntity result,String name,String pass){
//		  Assert.assertNull(userEntity.getUser(name, pass));
//	  }

//  @Test
//  public void getUser() {
//	  userEntity = new UserEntity("emy2","emy2","123");
//	  //Assert.assertEquals(new UserEntity("emy2","emy2","123"), userEntity.getUser("emy2", "123"));
//	  //Assert.assertNotNull( userEntity.getUser("emy2", "123"));
//	  Assert.assertNull( userEntity.getUser("emy2", "123"));
//  }
//  
//  @DataProvider(name = "getsearchTest")
//  public static Object[][]getsearchTest(){
//  	return new Object[][] {{"aml2","emy2","aml2"} , {"b","aa","hossam2"}};
//  }
//  
//  @Test(dataProvider ="getsearchTest")
//  public void getSearch(String result,String myemail,String email){
//	  System.out.println("result" + result + " "+ myemail +" " + email );
//	  //System.out.println(" Sysoo " + UserEntity.getSearch(myemail, email).getName());
//	  
//	  Assert.assertEquals(result, UserEntity.getSearch(myemail, email).getName());
//}
  
/*

  @DataProvider(name = "saveUserTest")
  public static Object[][]saveUserTest(){
  	return new Object[][] {{true}};
  }
  
  @Test(dataProvider ="saveUserTest")
  public void saveUser(Boolean result){
	  Assert.assertNull(userEntity.saveUser());
  }
  
  */
//  @DataProvider(name = "saveRequestTest")
//  public static Object[][]saveRequestTest(){
//  	return new Object[][] {{true,"aml2","amir2","pending"},{false,"amir2","emy2","pending"}};
//  }
//  
//  @Test//(dataProvider ="saveRequestTest")
//  public void saveRequest(){//(Boolean result,String myemail,String email,String Rqstatus){
//	  boolean check = UserEntity.saveRequest("amir2", "emy2", "pending");
//	  Assert.assertEquals(false,check);
//  }
  
  	  @DataProvider(name = "checkRequestTest")
	  public static Object[][]checkRequestTest(){
	  	return new Object[][] {{"aml2","emy2"} , {"emy2","amir2"}};
	  }
	  
	  @Test(dataProvider ="checkRequestTest")
	  public void checkRequest(String res,String myemail){
		  Assert.assertNull(UserEntity.checkRequest(myemail));

	}
/*
	  @DataProvider(name = "acceptRequestTest")
	  public static Object[][]acceptRequestTest(){
	  	return new Object[][] {{"emy2","aml2"} , {"amir2","emy2"}};
	  }
	  
	  @Test(dataProvider ="acceptRequestTest")
	  public void acceptRequest(String res,String myemail){
		  Assert.assertNull(UserEntity.acceptRequest(myemail));
	}

	  @DataProvider(name = "saveMsgTest")
	  public static Object[][]saveMsgTest(){
	  	return new Object[][] {{true,"emy2","aml2","hiii"} , {true,"aml2","emy2","ezayk"}};
	  }
	  
	  @Test(dataProvider ="saveMsgTest")
	  public void saveMsg(String res,String myemail,String email, String msg){
		  Assert.assertNull(UserEntity.saveMsg(myemail, email, msg));
	}
	  @DataProvider(name = "showNewMsgTest")
	  public static Object[][]showNewMsgTest(){
	  	return new Object[][] {{"hiiiii","emy2","aml2"} , {"hiiiii","aml2","emy2"}};
	  }
	  
	  @Test(dataProvider ="showNewMsgTest")
	  public void showNewMsg(String res,String myemail,String email){
		  Assert.assertEquals(res,UserEntity.showNewMsg(myemail, email));
	  }	  
	
	  
	  @DataProvider(name = "showAllMsgTest")
	  public static Object[][]showAllMsgTest(){
	  	return new Object[][] {{"","emy2","aml2"} , {"","aml2","emy2"}};
	  }
	  
	  @Test(dataProvider ="showAllMsgTest")
	  public void showAllMsg(String res,String myemail,String email){
		  Assert.assertNull(UserEntity.showAllMsg(myemail, email));
	}
	  
//	  @DataProvider(name = "getnotificationTest")
//	  public static Object[][]getnotificationTest(){
//	  	return new Object[][] {{"","emy2","aml2"} , {"","aml2","emy2"}};
//	  }
//	  
//	  @Test(dataProvider ="getnotificationTest")
//	  public void showAllMsg(String res,String myemail,String email){
//		  Assert.assertNull(UserEntity.showAllMsg(myemail, email));
//	}  

	  @DataProvider(name = "createPageTest")
	  public static Object[][]createPageTest(){
	  	return new Object[][] {{true,"emy2","hossamghaly2","sport","nonprofit"} , {true,"aml2","amldesigns","media","profit"}};
	  }
	  
	  @Test(dataProvider ="createPageTest")
	  public void createPage(String res,String myemail,String name,String type,String category){
		  Assert.assertNull(UserEntity.createPage(myemail, name, type, category));
	}*/
}
