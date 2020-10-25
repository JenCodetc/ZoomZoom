/* Program that logs into zoom meetings for you (web browser only)
 * 
 * Notes: to run this file, you need to have Selenium WebDriver and chromedriver downloaded in your files; for the formatting of the text file, the first line should be your school email address, the second line your email password, and the following set of lines for as many classes as you want: name of class, web zoom link*, zoom link password
 * *for the web zoom link, you can find it by opening a normal zoom link, and instead of opening it in the app, you can copy the link for "open in browser"
 * 
 * Poorly put together by: Jennifer Li
 * 
 * */

package ZoomLogin;

import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ZoomLogin {

	public static void main(String[] args) {

		//stores zoom information
		Scanner fileIn = null;
		ArrayList<String> classNames = new ArrayList<String>();
		ArrayList<String> zoomLinks = new ArrayList<String>();
		ArrayList<String> zoomPasswords = new ArrayList<String>();
		
		//opens text file (default is "file.txt" but you can change it)
		try {
			fileIn = new Scanner(new File("file.txt"));
		}catch(FileNotFoundException e) {
			System.exit(-1);
		}
		
		String email = fileIn.nextLine();
		String password = fileIn.nextLine();
		
		//file.txt must be in correct format
		while(fileIn.hasNextLine()) {
			classNames.add(fileIn.nextLine());
			zoomLinks.add(fileIn.nextLine());
			zoomPasswords.add(fileIn.nextLine());
		}
		
		//displays options
		Scanner keyboard = new Scanner(System.in);
		int i = 0;
		for(; i < classNames.size(); i++)
			System.out.println((i+1) + " - " + classNames.get(i));
		System.out.println((i + 1) + " - Quit");
		
		int choice = keyboard.nextInt() - 1;

		if(choice == i)
			System.exit(-1);
		
		//sets up we driver
		System.setProperty("webdriver.chrome.driver", "/Users/jenniferli/Downloads/1...important/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		//zoom sign in page
		driver.get("https://zoom.us/signin");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//logs in to zoom
		driver.findElement(By.xpath("//*[@id=\"login\"]/div/div[3]/div/div[4]/a[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys(email);
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/div[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button")).click();
		
		//opens zoom web meeting link
			//filler link to avoid re-entering log in info
		driver.get("https://mcpsmd.zoom.us/meeting#/upcoming");
			//zoom web meeting link
		driver.get(zoomLinks.get(choice));
		driver.findElement(By.xpath("//*[@id=\"joinBtn\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"inputpasscode\"]")).sendKeys(zoomPasswords.get(choice));
		driver.findElement(By.xpath("//*[@id=\"joinBtn\"]")).click();
		
		//clicks join meeting and mutes
		driver.findElement(By.xpath("//*[@id=\"voip-tab\"]/div/button")).click();
		
	}

}