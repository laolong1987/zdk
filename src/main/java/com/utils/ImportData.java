package com.utils;

/**
 * Created by gaoyang on 2016/6/6.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ImportData {

    public List<Map> getData(String filepath) {
        List result = new ArrayList<Map>();
        File file = new File(filepath);
        Workbook book = null;
        try {
            book = Workbook.getWorkbook(file);
        } catch (BiffException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();

        list1 = getList(0, 0, 1, book);
        list2 = getList(0, 1, 1, book);
        list3 = getList(0, 1, 1, book);

        for (int i = 0; i < list1.size(); i++) {
            Map map = new HashMap();
            map.put("list1", list1.get(i));
            map.put("list2", list2.get(i));
            map.put("list3", list3.get(i));
            if(!"".equals(list1.get(i))){
                result.add(map);
            }
        }
        return result;
    }

    public List<Map> getMoreData(String filepath) {
        List result = new ArrayList<Map>();
        File file = new File(filepath);
        Workbook book = null;
        try {
            book = Workbook.getWorkbook(file);
        } catch (BiffException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        List list5 = new ArrayList();
        List list6 = new ArrayList();
        List list7 = new ArrayList();
        List list8 = new ArrayList();
        List list9 = new ArrayList();
//		List list10 = new ArrayList();
//		List list11 = new ArrayList();
//		List list12 = new ArrayList();
//		List list13 = new ArrayList();
//		List list14 = new ArrayList();
//		List list15 = new ArrayList();
//		List list16 = new ArrayList();
//		List list11 = new ArrayList();
//		List list12 = new ArrayList();
//		List list13 = new ArrayList();
        list1 = getList(0, 0, 1, book);
        list2 = getList(0, 1, 1, book);
        list3 = getList(0, 2, 1, book);
        list4 = getList(0, 3, 1, book);
        list5 = getList(0, 4, 1, book);
        list6 = getList(0, 5, 1, book);
        list7 = getList(0, 6, 1, book);
        list8 = getList(0, 7, 1, book);
        list9 = getList(0, 8, 1, book);
//	    list10 = getList(3, 9, 1, book);
//	    list11 = getList(3, 10, 1, book);
//	    list12 = getList(3, 11, 1, book);
//	    list13 = getList(3, 12, 1, book);
//	    list14 = getList(3, 13, 1, book);
//	    list15 = getList(3, 14, 1, book);
//	    list16 = getList(3, 15, 1, book);
//	    list11 = getList(0, 10, 0, book);
//	    list12 = getList(0, 11, 0, book);
//	    list13 = getList(0, 12, 0, book);
        for (int i = 0; i < list1.size(); i++) {
            Map map = new HashMap();
            map.put("list1", list1.get(i));
            map.put("list2", list2.get(i));
            map.put("list3", list3.get(i));
            map.put("list4", list4.get(i));
            map.put("list5", list5.get(i));
            map.put("list6", list6.get(i));
            map.put("list7", list7.get(i));
            map.put("list8", list8.get(i));
            map.put("list9", list9.get(i));
//			map.put("list10", list10.get(i));
//			map.put("list11", list11.get(i));
//			map.put("list12", list12.get(i));
//			map.put("list13", list13.get(i));
//			map.put("list14", list14.get(i));
//			map.put("list15", list15.get(i));
//			map.put("list16", list15.get(i));
//			map.put("list11", list11.get(i));
//			map.put("list12", list12.get(i));
//			map.put("list13", list13.get(i));
            if(!"".equals(list1.get(i))){
                result.add(map);
            }

        }
        return result;
    }
    public static ArrayList getList(int sheetid, int rowid, int fromrowno,
                                    Workbook book) {
        ArrayList aryList = new ArrayList();
        Sheet sheet = book.getSheet(sheetid);
        String result;
        int i = 0;
        int rowcnt = sheet.getRows();
        for (i = 0; i < rowcnt - fromrowno; i++) {
            result = "";
            Cell cell = sheet.getCell(rowid, i + fromrowno);
            result = cell.getContents();
            result = result.replace('\n', ' ');
            result = result.trim();
            aryList.add(result);
        }
        return aryList;
    }

    public void writeExcel(OutputStream out, List list, String[] title) {
        if (list == null) {
            throw new IllegalArgumentException("");
        }
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(out);
            WritableSheet ws = workbook.createSheet("sheet 1", 0);
            int rowNum = 0;
            if (title != null) {
                putRow(ws, 0, title);
                rowNum = 1;
            }
            for (int i = 0; i < list.size(); i++, rowNum++) {
                Object[] cells = (Object[]) list.get(i);
                putRow(ws, rowNum, cells);
            }
            workbook.write();
            workbook.close();
        } catch (RowsExceededException e) {
            System.out.println("jxl write RowsExceededException: "
                    + e.getMessage());
        } catch (WriteException e) {
            System.out.println("jxl write WriteException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("jxl write file i/o exception!, cause by: "
                    + e.getMessage());
        }
    }

    private void putRow(WritableSheet ws, int rowNum, Object[] cells)
            throws RowsExceededException, WriteException {
        for (int j = 0; j < cells.length; j++) {// дһ��
            Label cell = new Label(j, rowNum, "" + cells[j]);
            ws.addCell(cell);
        }
    }

    public List<Map> getDataAuto(String filePath){
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        ImportData importData = new ImportData();
        List<Map> list = importData.getData("d:\\aa.xls");
        int i=1;
        for(Map map:list){
            System.out.println(i+"------"+map.get("list1")+"---"+map.get("list2"));
            i++;
        }
    }

}
