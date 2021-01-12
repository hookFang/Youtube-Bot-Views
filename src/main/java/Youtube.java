import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Youtube {
    //Paste your Link here
    static String linkToVideo = "https://www.youtube.com/watch?v=GrOTZ3wts_I";
    static Logger logger = Logger.getLogger(Youtube.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        ProxyRetriever tempProxy = new ProxyRetriever();

        //Install Chrome Browser
        //Need to download chrome driver : https://chromedriver.chromium.org/
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Chrome Driver\\chromedriver.exe");

        /*Web driver*/
        WebDriver driver = null;

        List<String> proxyList = new ArrayList<>(tempProxy.getProxyList());

        for(int i = 0; i<proxyList.size(); i++) {
            try {
                /*Proxy Variable*/
                Proxy proxy = new Proxy();
                String temp = proxyList.get(i);
                proxy.setHttpProxy(temp);
                proxy.setSslProxy(temp);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--autoplay-policy=no-user-gesture-required");
                options.setCapability("proxy", proxy);
                driver = new ChromeDriver(options);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                driver.get(linkToVideo);

            } catch (WebDriverException exception) {
                logger.log(Level.WARNING, "A Web Driver Exception caught, current driver will be closed to open a new one");
                proxyList.remove(i);
                assert driver != null : "Web driver null";
                driver.quit();
                driver = null;
                if(proxyList.size() == 0) break;
            }
        }
    }
}
