package com.akvasoft.amason.common;

import com.akvasoft.amason.repo.ItemRepo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Controller
public class ExcelReader {
    String start = "Belleze 48\" Rectangular Faux Leather Linen Storage Ottoman Bench Footrest, Large Space";
    String end = "CoverMates Window Air Conditioner Cover";
    boolean isItems = false;

    public List<Item> read() throws Exception {
        List<Item> list = new ArrayList<>();

        String filename = "/var/lib/tomcat8/items/abc.xlsx";
        FileInputStream fis = null;

        try {

            fis = new FileInputStream(filename);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIter = sheet.rowIterator();
            Item item = null;
            while (rowIter.hasNext()) {
                item = new Item();
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                Vector<String> cellStoreVector = new Vector<String>();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    String cellvalue = myCell.getStringCellValue();
                    cellStoreVector.addElement(cellvalue);
                }
                String firstcolumnValue = null;
                String secondcolumnValue = "";

                int i = 0;
                firstcolumnValue = cellStoreVector.get(i).toString();
                //secondcolumnValue = cellStoreVector.get(i + 1).toString();

                insertQuery(firstcolumnValue, secondcolumnValue);
                if (start.equalsIgnoreCase(firstcolumnValue)) {
                    isItems = true;
                }
                if (end.equalsIgnoreCase(firstcolumnValue)) {
                    break;
                }
                if (isItems) {
                    if (firstcolumnValue != null) {
                        item.setName(firstcolumnValue.toString());
                        list.add(item);
                        System.err.println("added " + firstcolumnValue);
                    }
                }
            }


        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (fis != null) {

                fis.close();

            }

        }

        return list;

    }

    private void insertQuery(String firstcolumnvalue, String secondcolumnvalue) {

//        System.out.println(firstcolumnvalue + " " + secondcolumnvalue);

    }

}
