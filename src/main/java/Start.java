import POJO.DD;
import POJO.ISP;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ExcelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Start {
    public static void main(String[] args) {
        parseSpeedWebsite();
//      parseFreeProxyList();
    }

    public static void parseFreeProxyList(){
        Crawler crawler = new Crawler(new ChromeDriver(),"http://spys.one/free-proxy-list/CN/");
        WebDriver driver = crawler.getDriver();
        WebElement table = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[5]/td/table"));
        List<WebElement>  trList = table.findElements(By.tagName("tr"));
        List title= trList.get(2).findElements(By.tagName("td")).stream().map(e-> e.getText().toString() ).collect(Collectors.toList());
        List content = new ArrayList();
        for(int i = 3; i < trList.size(); i++ )
        {
            List<WebElement>  tdList  = trList.get(i).findElements(By.cssSelector("td[colspan='1']"));
            List<String> rowList = tdList.stream().map(e-> e.getText().toString() ).collect(Collectors.toList());
            content.add(rowList);

        }
        ExcelUtil.printToExcel(title,content);
    }


    public static void parseSpeedWebsite(){
        Crawler crawler = new Crawler(new ChromeDriver(),"https://tool.chinaz.com/speedcom/pc.juxiangbaojie.com-www.yabo278.com");
        WebDriver driver = crawler.getDriver();
        List<ISP> ispList = new ArrayList<ISP>();
        WebElement speedList = driver.findElement(By.id("speedlist"));
        List<WebElement> list = speedList.findElements(By.xpath(".//div[@class='row listw compare clearfix']"));
        WebElement header = speedList.findElement(By.xpath(".//div[@class='row tb-head clearfix']"));
        String tableTitleList[] = header.getText().toString().split("\n");
        for (int i = 0; i < list.size(); i++) {
            WebElement item = list.get(i);
            List<WebElement> fiveDiv = item.findElements(By.xpath(".//div"));
            WebElement part1 = fiveDiv.get(1);
            WebElement part2 = fiveDiv.get(2);
            WebElement part3 = fiveDiv.get(3);
            String cityName = part1.findElement(By.name("city")).getText().toString();
            String support = part3.findElement(By.name("support")).getText().toString();
            List<WebElement> twoDl = part2.findElements(By.tagName("dl"));
            List<WebElement> tenDD = twoDl.get(0).findElements(By.tagName("dd"));
            String domain = tenDD.get(0).getText().toString();
            String ip = tenDD.get(1).getText().toString();
            String httpStatus = tenDD.get(2).getText().toString();
            String allTime = tenDD.get(3).getText().toString();
            String dnsTime = tenDD.get(4).getText().toString();
            String connTime = tenDD.get(5).getText().toString();
            String downTime = tenDD.get(6).getText().toString();
            String fileSize = tenDD.get(7).getText().toString();
            String downSpeed = tenDD.get(8).getText().toString();
            ISP isp = new ISP();
            DD dd01 = new DD(domain, ip, httpStatus, allTime, dnsTime, connTime, downTime, fileSize, downSpeed);
            tenDD = twoDl.get(1).findElements(By.tagName("dd"));
            domain = tenDD.get(0).getText().toString();
            ip = tenDD.get(1).getText().toString();
            httpStatus = tenDD.get(2).getText().toString();
            allTime = tenDD.get(3).getText().toString();
            dnsTime = tenDD.get(4).getText().toString();
            connTime = tenDD.get(5).getText().toString();
            downTime = tenDD.get(6).getText().toString();
            fileSize = tenDD.get(7).getText().toString();
            downSpeed = tenDD.get(8).getText().toString();
            DD dd02 = new DD(domain, ip, httpStatus, allTime, dnsTime, connTime, downTime, fileSize, downSpeed);
            isp.setDd01(dd01);
            isp.setDd02(dd02);
            isp.setName(cityName);
            isp.setSupport(support);
            ispList.add(isp);
        }
        ExcelUtil.printToExcel(tableTitleList,ispList);

    }


}
