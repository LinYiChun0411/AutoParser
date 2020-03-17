package utils;

import POJO.DD;
import POJO.ISP;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelUtil {

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
            new ISP();
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

    public static void printToExcel(List<String>title , List<List<String>> content){
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Data");
        //This data needs to be written (Object[])
        Map<String, List<String>> data = new TreeMap<String, List<String>>();
//        data.put("-1", new Object[] {"监测点", "对比网址", "解析IP", "HTTP状态", "总耗时", "解析时间","连接时间", "下载时间" ,"文件大小" ,"下载速度", "操作","赞助商"});
        data.put("-1", title);
        for(int i = 0; i < content.size(); i++){
            List<String> row = content.get(i);
            data.put( i+"" ,row);
        }
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            List<String> objArr = data.get(key);
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
