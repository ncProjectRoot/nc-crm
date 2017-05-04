package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import java.util.*;

/**
 * Created by AN on 16.04.2017.
 */
public class XSSFTableFiller {
    private XSSFWorkbook workbook;
    private Map<String, List<?>> table;
    private Map<String, Coordinates> coordinatesOfColumns;
    private List<String> titles;
    private String sheetName;
    private int rowStart;
    private int cellStart;


    public XSSFTableFiller(Workbook workbook, Map<String, List<?>> table, String sheetName, int rowStart, int cellStart) {
        this.workbook = (XSSFWorkbook) workbook;
        this.table = table;
        this.sheetName = sheetName;
        this.rowStart = rowStart;
        this.cellStart = cellStart;
        titles = new ArrayList<>(table.keySet());

    }

    public Workbook fillExcel(){
        createSheet(sheetName);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        /* Create an object of type XSSFTable */
        XSSFTable my_table = sheet.createTable();
        /* get CTTable object*/
        CTTable cttable = my_table.getCTTable();
        setTableStyle(cttable);
    /* Let us define the required Style for the table */
        setTableStyle(cttable);
        setAreaReference(cttable);
        setTableInfo(cttable, sheet);
        setTitles(sheet);
        fillData(sheet);
        return workbook;
    }

    private void setAreaReference(CTTable cttable){
        Coordinates coord = getAreaReferenceCoordinates();
        AreaReference my_data_range = new AreaReference(new CellReference(coord.getStartRow(), coord.getStartColumn()), new CellReference(coord.getEndRow(), coord.getEndColumn()));
        cttable.setRef(my_data_range.formatAsString());
    }

    private void setTableInfo(CTTable cttable, Sheet sheet){
        cttable.setDisplayName(sheetName);      /* this is the display name of the table */
        cttable.setName("Test");    /* This maps to "displayName" attribute in <table>, OOXML */
        cttable.setId(1L); //id attribute against table as long value
        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(titles.size()+1); //define number of columns
    }

    private void setTableStyle(CTTable cttable ){
        CTTableStyleInfo table_style = cttable.addNewTableStyleInfo();
        table_style.setName("TableStyleMedium9");
        table_style.setShowColumnStripes(false); //showColumnStripes=0
        table_style.setShowRowStripes(true); //showRowStripes=1
    }


    private Coordinates getAreaReferenceCoordinates(){
        int startColumn = cellStart;
        int startRow = rowStart;
        int endColumn = titles.size() + startColumn;
        int endRow = table.get(titles.get(0)).size() + startRow;
        return new Coordinates(startColumn,startRow,endColumn,endRow);
    }

    private void createSheet(String sheetName){
        workbook.createSheet(sheetName);
    }

    private void setTitles(Sheet sheet){
        Row row = sheet.createRow(rowStart);
        row.createCell(cellStart).setCellValue("№");
        String title;
        for(int i = 0; i < titles.size(); i++){
            title = titles.get(i);
            row.createCell(i+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(i+1+cellStart).setCellValue(title);
        }
    }

    private void fillData(Sheet sheet){
        int numOfColumns = table.size();
        int numOfRows = table.get(titles.get(0)).size();
        Row row;
        String currentTitle;
        Object currentValue;
        for(int i = 0; i < numOfRows; i++){
            row = sheet.createRow(i+1+rowStart);
            row.createCell(cellStart).setCellValue(i+1);
            for (int j = 0; j < numOfColumns; j++){
                currentTitle = titles.get(j);
                currentValue = table.get(currentTitle).get(i);
                setValueFromTable(currentValue, j, row);
            }

        }
        calculateCoordinates();
    }

    private void setValueFromTable(Object currentValue, int titleIndex, Row row){
        if(currentValue instanceof String){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((String) currentValue);
        }
        else if(currentValue instanceof Integer){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Integer) currentValue);
        }
        else if(currentValue instanceof Double){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Double) currentValue);
        }
        else if(currentValue instanceof Calendar){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((Calendar) currentValue);
        }
        else if(currentValue instanceof Date){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((Date) currentValue);
        }
    }

    private void calculateCoordinates(){
        int startColumn;
        int startRow;
        int endColumn;
        int endRow;
        coordinatesOfColumns = new HashMap<>();
        String currentTitle;
        for (int i = 0; i < titles.size(); i++){
            currentTitle = titles.get(i);
            startColumn = i+ cellStart + 1;
            startRow = rowStart + 1;
            endColumn = startColumn;
            endRow = rowStart + 1 +  table.get(currentTitle).size();
            coordinatesOfColumns.put(currentTitle, new Coordinates(startColumn,startRow,endColumn,endRow));
        }
    }

    public Map<String, Coordinates> getCoordinatesOfColumns() {
        return coordinatesOfColumns;
    }

}
