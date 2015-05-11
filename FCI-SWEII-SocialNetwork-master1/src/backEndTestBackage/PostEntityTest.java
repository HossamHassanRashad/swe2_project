package backEndTestBackage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.PostEntity;
@Test(groups="PostEntityTest")
public class PostEntityTest {
	
	@DataProvider(name = "createPostTest")
	  public static Object[][]createPostTest(){
	  	return new Object[][] {{true,"emy2","emy2","new","bad","public"},{true,"aml2","aml2","hii","happy","public"}};
	  }
	  
	  @Test(dataProvider ="createPostTest")
	  public void createPost(boolean res,String myemail,String email,String content,String feel,String Privacy){
		  boolean check = PostEntity.createPost(myemail, email, content, feel, Privacy);
		  Assert.assertEquals(res, check);
		  //Assert.assertEquals(res, PostEntity.createPost(myemail, email, content, feel, Privacy));    
	  }
	
//void
	  @Test(dependsOnMethods = { "createPost" })
	  public void likePage(String name){
		//  Assert.assertEquals(res, PostEntity.increaseCount(name));
	}
	  
	  
	  @DataProvider(name = "getMostTagsTest")
	  public static Object[][]getMostTags(){
	  	return new Object[][] {{""}};
	  }
	  
	  @Test(dataProvider ="getMostTagsTest")
	  public void getMostTags(String res){
		 Assert.assertEquals(res, PostEntity.getMostTags());    
	  }
	  //getMostTags()
	  
	  //*********like post**********
	  @DataProvider(name = "likePostTest")
	  public static Object[][]likePostTest(){
	  	return new Object[][] {{true,"2"},{true,"6"}};
	  }
	  
	  @Test(dataProvider ="likePostTest")
	  public void likePost(boolean res,String postID){
		 Assert.assertEquals(res, PostEntity.likePost("2"));    
	  } 
	  
	  
}
