package com.hf;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtil {

    public static void main(String[] args) {

        List<String[]> list = readExcel("F:\\Tudw\\SL651ManulElements.xlsx");
        // 如果想跳过标题 这里i从1开始
        for (int i = 1; i< list.size();i++) {
            StringBuffer stringBuffer = new StringBuffer();

            String[] str= (String[]) list.get(i);
            // 循环输出每行的值
            //  ["24H", { name: "30分钟时段降水量", unit: "毫米", dataType: { type: "Number", specs: { length: 5, decimals: 1 } } }],

            // 正则表达式匹配数字
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(str[4]);
            // 用两个变量来存储匹配到的数字
            int firstNumber = 0;
            int secondNumber = 0;
            // 计数器用于区分第一个和第二个数字
            int count = 0;
            // 遍历匹配结果并赋值
            while (matcher.find()) {
                if (count == 0) {
                    firstNumber = Integer.parseInt(matcher.group());
                } else if (count == 1) {
                    secondNumber = Integer.parseInt(matcher.group());
                }
                count++;
            }
            stringBuffer.append("[\"").append(str[1]).append("\",{name:\"").append(str[2]).append("\",unit:\"").append(str[3]).append("\",dataType:{type:\"Number\",specs:{length:")
                    .append(firstNumber).append(", decimals: ").append(secondNumber).append("} } }],");

            System.out.println(stringBuffer.toString());

        }

        System.out.println("Total:"+list.size());
    }

    /***
     * 读取excel文件
     * @param excelPath
     * @return
     */
    public static List<String[]> readExcel(String excelPath) {
        // 验证文件是否存在
        if(!isExists(excelPath))
            return null;
        // 验证文件名是否合格
        if(!validateExcel(excelPath))
            return null;
        File file = new File(excelPath);
        // 创建list存放读取的数据
        List<String[]> list = new ArrayList<String[]>();
        // 创建IO流读取文件
        FileInputStream inputStream = null;
        Workbook workbook = null;
        // 日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            inputStream = new FileInputStream(file);
            // 这种方式 Excel 2003/2007/2010 都是可以处理的
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); //默认只读第一个sheet
            // 获取第一行
            Row row1 = sheet.getRow(0);
            // 第一行为空
            if (row1 == null) {
                System.err.println("The first row is empty.");
                return null;
            }
            int rowCount = sheet.getPhysicalNumberOfRows(); // 获取不为空的总行数
            int cellCount = row1.getPhysicalNumberOfCells(); // 从第一行获取不为空的总列数
            // 遍历每一行
            for (int r = 0; r < rowCount; r++) {
                Row row = sheet.getRow(r);
                if(row == null)
                    continue;

                // 创建一个数组 用来存储每一列的值
                String[] lineItem = new String[cellCount];

                // 遍历每一列
                for (int c = 0; c < cellCount; c++)
                {
                    Cell cell = row.getCell(c);
                    if (cell == null)
                    {
                        continue;
                    }
                    int cellType = cell.getCellType();
                    String cellValue = null;
                    if(Cell.CELL_TYPE_STRING == cellType){ // 文本
                        cellValue = cell.getStringCellValue();
                    } else if(Cell.CELL_TYPE_NUMERIC == cellType){
                        if (DateUtil.isCellDateFormatted(cell))
                        {
                            cellValue = sdf.format(cell.getDateCellValue()); // 日期型
                        }
                        else
                        {
                            cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
                            if (cellValue.endsWith(".0"))
                            {
                                cellValue = cellValue.substring(0, cellValue.length() - 2);
                            }
                            //防止科学计数法
                            BigDecimal bd = new BigDecimal(cellValue);
                            cellValue = bd.toPlainString();
                            if (cellValue.endsWith(".0"))
                            {
                                cellValue = cellValue.substring(0, cellValue.length() - 2);
                            }
                        }
                    } else if(Cell.CELL_TYPE_BOOLEAN == cellType){
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                    } else if(Cell.CELL_TYPE_BLANK == cellType){
                        cellValue = cell.getStringCellValue();
                    } else {
                        cellValue = "error";
                    }

                    lineItem[c] = cellValue;
                }

                list.add(lineItem);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("read excel error:----"+ e.toString());
        }finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 验证文件是否存在
     * @param filePath
     * @return boolean
     */
    public static boolean isExists(String filePath) {
        File  file = new File(filePath);
        if(!file.exists()){
            System.out.println("The File does not exist.");
            return false;
        }
        return true;
    }

    /**
     * 验证是否是EXCEL文件
     * @param filePath
     * @return boolean
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            System.out.println("File is not excel.");
            return false;
        }
        return true;
    }

    //是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
