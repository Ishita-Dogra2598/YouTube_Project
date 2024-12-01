package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     
   //  WebDriverWait wait=new WebDriverWaitWebdr(WebDriver d, Duration.ofSeconds(5));
   

     public void  getURLWrapper(WebDriver driver,String URL)
     {
      driver.get(URL);
      // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
      
     }

     public boolean CheckCurrentURl(WebDriver driver)
     {
      if(driver.getCurrentUrl().equals("https://www.youtube.com/"))
      return true;

      return false;
     }

     public void clickElementWrapper(WebDriver driver,By locator)
     {
      WebElement element=driver.findElement(locator);
      element.click();
     }

     public void sendkeyselementwrapper(WebDriver driver,By locator,String keyword)
     {
          WebElement selement=driver.findElement(locator);
          WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
          wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
          selement.sendKeys(keyword);
          selement.sendKeys(Keys.ENTER);
     }

     public void  messageOnScreen(WebDriver driver,String heading,String para)
     {
      String Message=driver.findElement( By.xpath("//section[@class='ytabout__content']/child::"+heading+"")).getText();
      List<WebElement> paraElements=driver.findElements(By.xpath("//section[@class='ytabout__content']/child::"+para+""));
      String parastring="";
      for (WebElement webElement : paraElements) {

         
         parastring=parastring+webElement.getText();
      }
      System.out.println("The message is :- "+ Message+ parastring);
     }

     


     public void checkMovieDetails(WebDriver driver,By locator,String MovieName,String  ExpectedCategory,List<String> ExpectedGenre )
     {
      try {
           WebElement ele=driver.findElement(locator);
           ele.click();
           while(ele.isDisplayed())
           {
           ele.click();
           
           Thread.sleep(3000);
            System.out.println("clicked");
           }
           Thread.sleep(3000); 
         WebElement Genre=driver.findElement(By.xpath("//span[@title='"+MovieName+"']/../following-sibling::span[contains(@class,'renderer')]"));
         WebElement Category=driver.findElement(By.xpath("//span[@title='"+MovieName+"']/../../..//p[contains(text(),'U')]"));
          SoftAssert sf=new SoftAssert();
          sf.assertTrue(Category.getText().contains(ExpectedCategory),"Oops!! Not the expected Category");
          String ActualGenreString="";
          String ExpectedGenreString=Genre.getText();
          for (String string : ExpectedGenre) {
            if(Genre.getText().contains(string))
            {
            ActualGenreString=string;
            ExpectedGenreString=ActualGenreString;
            }
          }
          sf.assertEquals(ActualGenreString, ExpectedGenreString, "Oops!! Not the expected Genre");
          sf.assertAll();
         } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
         }
     }
     


     public void checkMusicDetails(WebDriver driver,String MusicCategoryName,String ExpectedMusicPlayListName)
     {
      try {

         WebElement Scroller =driver.findElement(By.xpath("//span[text()=\""+MusicCategoryName+"\"]/ancestor::div/following-sibling::div//button[@aria-label='Next']"));
              while(Scroller.isDisplayed())
              {
                Scroller.click();
              
              Thread.sleep(3000);
               System.out.println("clicked");
              }
            List< WebElement> ParentElement=driver.findElements(By.xpath("//span[text()='This year in music']/ancestor::div[@id='dismissible']//yt-lockup-view-model"));

            for(int i=0;i<ParentElement.size();i++)
            {
                if(i==ParentElement.size()-1)
                {
                    WebElement PlayListTitle=ParentElement.get(i).findElement(By.xpath(".//span[contains(@class,'string yt-core')]"));
                    WebElement Song_Count=ParentElement.get(i).findElement(By.xpath(".//div[contains(@class,'shape-wiz__text')]"));
                    String res=Song_Count.getText().split(" ")[0];
                    int songs_number=Integer.parseInt(res);
                   
                    SoftAssert softAssert=new SoftAssert();
                    softAssert.assertTrue(songs_number<=50,"The songs count is greater than 50");
                    System.out.println("The title of the playlist is:--" +PlayListTitle.getText());
                    softAssert.assertAll();
                    
                }
            }
   
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println(e);
      } 
     }
     

     public void ChecknewsDetails(WebDriver driver,String NewsCategory)
     {
      List <WebElement> ParentBody=driver.findElements(By.xpath("//span[text()='"+NewsCategory+"']/../../../../../following-sibling::div//ytd-rich-item-renderer[@items-per-row='3']"));
      int Likes=0;
      int i=0;
      for (WebElement webElement : ParentBody) {
         
       
       if( i<3)
       {
      WebElement titleElement=webElement.findElement(By.xpath(".//a[@id='author-text']/span"));
      WebElement bodyElement=webElement.findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']"));

      WebElement LikeElement=webElement.findElement(By.xpath(".//span[@id='vote-count-middle']"));
      if(LikeElement.getText().equals(""))
      {
        Likes=0;
      }
     else{
      Likes=Likes+Integer.parseInt(LikeElement.getText());
     }
      System.out.println("The Tittle of news is :-"+ titleElement.getText() +"The body of news is :- "+ bodyElement.getText() + 
      "The Likes on this post is:-" + LikeElement.getText());
      i++;
       }

       if(i==3)
       break;
      

      }

      System.out.println("The Sum of all likes are :- " + Likes);
     }

     public long changeViewsConverter(String str)
     {
        // String ViewArray=new String();
        //     String view=str.split("")
        long million=1000000;
        long thousand=1000;
        long billion=1000000000;
        for(int i=0;i<str.length();i++)
        {
         if(str.charAt(i)=='M')
         {
          if(str.contains("."))
          {
          long number=Long.parseLong(str.substring(0, i).replace(".", ""));
          long final_value=number/10*million;
          return final_value;
          }
          long number=Long.parseLong(str.substring(0, i));
          long final_value=number*million;
          return final_value;
         }
         else if(str.charAt(i)=='K')
         {
          if(str.contains("."))
          {
          long number=Long.parseLong(str.substring(0, i).replace(".", ""));
          long final_value=number/10*thousand;
          return final_value;
          }
          long number=Long.parseLong(str.substring(0, i));
          long final_value=number*thousand;
          return final_value;
         }
         else if(str.charAt(i)=='B')
         {
          if(str.contains("."))
          {
          long number=Long.parseLong(str.substring(0, i).replaceAll(".", "").replace(" ",""));
          long final_value=number/10*billion;
          return final_value;
          }
          long number=Long.parseLong(str.substring(0, i));
          long final_value=number*billion;
          return final_value;
         }
        }
        long  number=Long.parseLong(str);
        return number;
     }

     public long  checkViewsCount(WebDriver driver)
     {
      long final_view_count=0;
        List<WebElement> CardContents=driver.findElements(By.xpath("//ytd-video-renderer[@class='style-scope ytd-item-section-renderer']/descendant::span[contains(text(),'views')]"));
        long view_count=0,last_sum_count=0;
        for(int i=0;i<CardContents.size();i++)
        {
        WebElement AllViews=CardContents.get(i);
         Actions a = new Actions(driver);
        a.moveToElement(AllViews);
        a.perform();
        String Views=AllViews.getText().split(" ")[0];

        if(changeViewsConverter(Views)<100000000)
        {
          if(view_count<100000000 )
          {
          view_count=view_count+changeViewsConverter(Views);
          System.out.println(view_count);
          System.out.println("Scrolling....");
          if(view_count>100000000)
          {
            System.out.println("View count cross 10 cr ");

            view_count=last_sum_count;
            return view_count;
          }
          last_sum_count=view_count;
        }
        if(view_count==100000000)
        {
         return view_count;
        }
   

        }
        if(i==0 && changeViewsConverter(Views)==100000000)
        {
               return changeViewsConverter(Views);
        }
       

       
        }

        return final_view_count;
     }


}
