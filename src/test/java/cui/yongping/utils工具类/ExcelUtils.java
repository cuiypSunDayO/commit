package cui.yongping.utils工具类;

//import ExcelPOI_WookbookFACTORY20210511.Write;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cui.yongping.pojo实体类.WriteBackData;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @读取excel 数据并封装到指定对象中
 * @author 崔崔
 * @date 2021/5/24-11:03
 * 小崔hello
 */


public class ExcelUtils {

    public static List<WriteBackData> wbdList =new ArrayList<>() ;

    public static List  read(int sheetIndex,int sheetNum,Class clazz) throws Exception {
        //easypoi
        //1.excel的文件流
        FileInputStream fis = new FileInputStream(Constants.EXCEL_PATH);
        //2.easypoi导入的参数
        ImportParams params = new ImportParams();
        //从第0个sheet开始读
        params.setStartSheetIndex(sheetIndex);
        //每次获取一个sheet
        params.setSheetNum(sheetNum);
        //3.导入
        List caseInfoList = ExcelImportUtil.importExcel(fis, clazz, params);
        //4关闭流
        fis.close();
        return caseInfoList;
    }
    //回写
    public static void batchWrite() throws Exception {
        //回写的逻辑：变量wbdList集合，取出sheetIndex、rowNum、cellNum、content
        FileInputStream fis = new FileInputStream(Constants.EXCEL_PATH);
        //获取所有的sheet
        Workbook sheets = WorkbookFactory.create(fis);
        //循环wbdList集合
        for (WriteBackData wbd : wbdList) {
            int sheetIndex = wbd.getSheetIndex();
            int rowNum = wbd.getRowNum();
            int cellNum = wbd.getCellNum();
            String content = wbd.getContent();
            //获取对应的Sheet对象
            Sheet sheet = sheets.getSheetAt(sheetIndex);
            //获取对应的Row对象
            Row row = sheet.getRow(rowNum);
            //获取对应cell对象
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            //回写内容
            cell.setCellValue(content);
        }
        //回写到excel文件中
        FileOutputStream fos = new FileOutputStream(Constants.EXCEL_PATH);
        sheets.write(fos);
        fis.close();
        fos.close();
    }
       /* public static void batchWrite() throws Exception {
            //1.打开文件
            FileInputStream fis = new FileInputStream("src/test/resources/cases_v3.xlsx");
            //2.获取所有的sheets
            Workbook sheets = WorkbookFactory.create(fis);
            //遍历所有的wb列表
            for (WriteBackData write : wb) {
                //获取到sheetIndex，行，列，内容
                int sheetIndex= write.getSheetIndex();
                int rowNum = write.getRowNum();
                int cellNum = write.getCellNum();
                String content = write.getContent();
                //根据定位的sheetIndex在定位，行，列，内容
                Sheet sheet = sheets.getSheetAt(sheetIndex);
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //写在内存中
                cell.setCellValue(content);
            }
            //保存到文件
            FileOutputStream fos =new FileOutputStream("src/test/resources/cases_v3.xlsx");
            sheets.write(fos);
            fis.close();
            fos.close();

        }
*/

    }

