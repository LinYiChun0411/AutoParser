import POJO.DD;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {
    private static String URL = "https://tool.chinaz.com/speedcom/pc.juxiangbaojie.com-www.yabo278.com";
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        if(args.length == 0){
            System.out.println("請輸入正確參數!");
            return;
        }
        String URL = args[0];
        String driverPath = args[1];
        System.setProperty("webdriver.chrome.driver",driverPath);

        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, 6000);
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        if(wait.until(jQueryLoad) && wait.until(jsLoad)){
            String[] tableTitleList = parseTitle(driver);
            List<ISP> ispList = parseTableContent(driver);
            printToExcel(tableTitleList,ispList);
        }
        driver.close();
    }

    public static String[] parseTitle(WebDriver driver){
        WebElement speedList = driver.findElement(By.id("speedlist"));
        WebElement header = speedList.findElement(By.xpath(".//div[@class='row tb-head clearfix']"));
        String tableTitleList[] = header.getText().toString().split("\n");
        return tableTitleList;
    }

   public static List<ISP> parseTableContent(WebDriver driver){
       List<ISP> ispList = new ArrayList<ISP>();
       WebElement speedList = driver.findElement(By.id("speedlist"));
       List<WebElement> list = speedList.findElements(By.xpath(".//div[@class='row listw compare clearfix']"));

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
           return ispList;
       }

    public static void printToExcel(Object[] tableTitleList, List<ISP> dataList)  {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Data");
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
//        data.put("-1", new Object[] {"监测点", "对比网址", "解析IP", "HTTP状态", "总耗时", "解析时间","连接时间", "下载时间" ,"文件大小" ,"下载速度", "操作","赞助商"});
        data.put("-1", tableTitleList);
        for(int i = 0; i < dataList.size(); i++){
            ISP isp = dataList.get(i);
            DD dd01 = isp.getDd01();
            DD dd02 = isp.getDd02();
            data.put(i+"a",new Object[] {isp.getName(), dd01.getDomain(),dd01.getIpAddress(),dd01.getHttpStatus(),dd01.getHttpStatus(),dd01.getAllTime(),dd01.getConnTime(),dd01.getDownTime(),dd01.getFileSize(),dd01.getDownSpeed(),"",isp.getSupport()});
            data.put(i+"b",new Object[] {isp.getName(), dd02.getDomain(),dd02.getIpAddress(),dd02.getHttpStatus(),dd02.getHttpStatus(),dd02.getAllTime(),dd02.getConnTime(),dd02.getDownTime(),dd02.getFileSize(),dd02.getDownSpeed(),"",isp.getSupport()});

        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            String fileName =  dtf.format(now)+".xlsx";
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            System.out.println( fileName + " " + "written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}





