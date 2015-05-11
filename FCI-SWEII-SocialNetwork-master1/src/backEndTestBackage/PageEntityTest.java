package backEndTestBackage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.UserEntity;
@Test(groups="PageEntityTest")
public class PageEntityTest {
	@DataProvider(name = "getPageSearchTest")
	  public static Object[][]getPageSearchTest(){
	  	return new Object[][] {{new PageEntity("hossam ghaly","sport","entertainment",2,"emy2"),"emy2","hossam ghaly"}};
	  }
	  
	  @Test(dataProvider ="getPageSearchTest")
	  public void getPageSearch(PageEntity res,String myemail, String name){
		  Assert.assertEquals(res,PageEntity.getPageSearch(myemail, name));
	}
	  
	  
	  @DataProvider(name = "likePageTest")
	  public static Object[][]likePageTest(){
	  	return new Object[][] {{new PageEntity("hossam ghaly","sport","entertainment",3,"emy2"),"emy2","hossam ghaly"}};
	  }
	  
	  @Test(dataProvider ="likePageTest")
	  public void likePage(PageEntity res,String myemail, String name){
		  Assert.assertEquals(res,PageEntity.getPageSearch(myemail, name));
	}
}
