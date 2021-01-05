import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Youtube {
    public static void main(String[] args) throws InterruptedException, IOException {
        ProxyRetriever tempProxy = new ProxyRetriever();


        /*Step 1 Web driver*/
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Chrome Driver\\chromedriver.exe");

        /*Web driver*/
        WebDriver driver = null;
        int randomValue = 0;

        List<String> proxyList = tempProxy.getProxyList();


        for(int i =0; i<proxyList.size(); i++) {
            randomValue = new Random().nextInt(proxyList.size());
            try {
                /*Proxy Variable*/
                Proxy proxy = new Proxy();
                String temp = proxyList.get(randomValue);
                proxy.setHttpProxy(temp);
                proxy.setSslProxy(temp);
                
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--autoplay-policy=no-user-gesture-required");
                options.setCapability("proxy", proxy);
                driver = new ChromeDriver(options);
                driver.get("https://www.youtube.com/watch?v=GrOTZ3wts_I");
                
            } catch (WebDriverException exception) {
                proxyList.remove(randomValue);
                driver.quit();
                driver.close();
            }
        }
        //Time wait specified
        Thread.sleep(1000000);
        driver.quit();
    }
}
