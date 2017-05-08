package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ExcelFiller;
import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by AN on 16.04.2017.
 */
public class DefaultExcelFiller implements ExcelFiller{
    private Workbook workbook;
    private Sheet sheet;
    private String sheetName;

    private Map<String, List<?>> table;
    private Map<String, Coordinates> coordinatesOfTableColumns;
    private List<String> titles;
    private int rowStart;
    private int cellStart;

    private List<LinkedHashMap<String, List<?>>> additionalDataTables;
    private List<Map<String, Coordinates>> coordinatesOfAddDataColumns;
    private List<List<String>> additionalDataTitles;
   // private List<Integer> additionalDataRowStart;
   // private List<Integer> additionalDataCellStart;


    public DefaultExcelFiller(Workbook workbook, Map<String, List<?>> table, String sheetName, int rowStart, int cellStart) {
        this.workbook = workbook;
        this.table = table;
        this.sheetName = sheetName;
        this.rowStart = rowStart;
        this.cellStart = cellStart;
        titles = new ArrayList<>(table.keySet());
        this.workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);
    }

    public DefaultExcelFiller
            (Workbook workbook, Map<String, List<?>> table, String sheetName,
             int rowStart, int cellStart, List<LinkedHashMap<String, List<?>>> additionalDataTables) {
        this.workbook = workbook;
        this.table = table;
        this.sheetName = sheetName;
        this.rowStart = rowStart;
        this.cellStart = cellStart;
        titles = new ArrayList<>(table.keySet());
        this.workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);
        this.additionalDataTables = additionalDataTables;
        additionalDataTitles = new ArrayList<>();
        for (int i = 0; i < additionalDataTables.size(); i++){
            List<String> localTitles = new ArrayList<>(additionalDataTables.get(i).keySet());
            additionalDataTitles.add(localTitles);
        }
    }

     public Workbook fillExcel(){
        setTitles(titles, rowStart, cellStart);
        fillData(table, titles, rowStart, cellStart);
        coordinatesOfTableColumns = new HashMap<>();
        calculateCoordinates(coordinatesOfTableColumns, table, titles, rowStart, cellStart);
        setColumnAutoSize(cellStart, titles);
        if(additionalDataTables != null){
            fillAdditionalData();
        }
        return workbook;
    }

    private void fillAdditionalData(){
        int rowStep = 2;
        int cellStep = 2;
        int additionalRowStart = rowStart + 1 + table.get(titles.get(0)).size() + rowStep;
        int additionalCellStart = 0;
       // additionalDataRowStart = new ArrayList<>();
       // additionalDataCellStart = new ArrayList<>();
        coordinatesOfAddDataColumns = new ArrayList<>();
        for (int i = 0; i < additionalDataTitles.size(); i++){
            //additionalDataRowStart.add(additionalRowStart);
            //additionalDataCellStart.add(additionalCellStart);
            setTitles(additionalDataTitles.get(i), additionalRowStart, additionalCellStart);
            fillData(additionalDataTables.get(i), additionalDataTitles.get(i), additionalRowStart, additionalCellStart);
            coordinatesOfAddDataColumns.add(new HashMap<>());
            calculateCoordinates(coordinatesOfAddDataColumns.get(i), additionalDataTables.get(i),
                    additionalDataTitles.get(i), additionalRowStart, additionalCellStart);
            setColumnAutoSize(additionalCellStart,additionalDataTitles.get(i));

            additionalCellStart += additionalDataTitles.get(i).size() + cellStep;
        }
    }

    private void createSheet(String sheetName){
        workbook.createSheet(sheetName);
    }

    private void setTitles(List<String> titles,int rowStart, int cellStart){
        if(sheet.getRow(rowStart) == null){
            sheet.createRow(rowStart);
        }
        Row row = sheet.getRow(rowStart);
        row.createCell(cellStart).setCellValue("№");
        setTitleCellStyle(row.getCell(cellStart));
        String title;
        for(int i = 0; i < titles.size(); i++){
            title = titles.get(i);
            row.createCell(i+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(i+1+cellStart).setCellValue(title);
            setTitleCellStyle(row.getCell(i+1+cellStart));
        }
    }

    private void fillData(Map<String, List<?>> table, List<String> titles, int rowStart, int cellStart){
        int numOfColumns = table.size();
        int numOfRows = table.get(titles.get(0)).size();
        Row row;
        String currentTitle;
        Object currentValue;
        for(int i = 0; i < numOfRows; i++){
            if(sheet.getRow(i+1+rowStart) == null){
                sheet.createRow(i+1+rowStart);
            }
            row = sheet.getRow(i+1+rowStart);
            row.createCell(cellStart).setCellValue(i+1);
            setTitleCellStyle(row.getCell(cellStart));
            for (int j = 0; j < numOfColumns; j++){
                currentTitle = titles.get(j);
                currentValue = table.get(currentTitle).get(i);
                setValueFromTable(cellStart, currentValue, j, row);
                setValueCellStyle(row.getCell(j+1+cellStart));
            }
        }
    }

    private void setValueFromTable(int cellStart, Object currentValue, int titleIndex, Row row){
        if(currentValue instanceof String){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((String) currentValue);
        }
        else if(currentValue instanceof Long){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Long) currentValue);
        }
        else if(currentValue instanceof Double){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Double) currentValue);
        }
        else if(currentValue instanceof Boolean){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_BOOLEAN);
            row.getCell(titleIndex+1+cellStart).setCellValue((Boolean) currentValue);
        }
        else if(currentValue instanceof LocalDateTime){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue(currentValue.toString());
        }
        else if(currentValue == null){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
        }
    }

    private void calculateCoordinates
            (Map<String, Coordinates> coordinatesOfTableColumns, Map<String, List<?>> table, List<String> titles, int rowStart, int cellStart){
        int startColumn;
        int startRow;
        int endColumn;
        int endRow;
        String currentTitle;
        for (int i = 0; i < titles.size(); i++){
            currentTitle = titles.get(i);
            startColumn = i+ cellStart + 1;
            startRow = rowStart + 1;
            endColumn = startColumn;
            endRow = rowStart  +  table.get(currentTitle).size();
            coordinatesOfTableColumns.put(currentTitle, new Coordinates(startColumn,startRow,endColumn,endRow));
        }
    }

    private void setColumnAutoSize(int cellStart, List<String> titles){
        for(int i = cellStart; i < titles.size() + cellStart + 1; i++){
            sheet.autoSizeColumn(i);
        }
    }

    private void setTitleCellStyle(Cell cell){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
    }

    private void setValueCellStyle(Cell cell){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
    }

    public Map<String, Coordinates> getCoordinatesOfTableColumns() {
        return coordinatesOfTableColumns;
    }

    public List<Map<String, Coordinates>> getCoordinatesOfAddDataColumns() {
        return coordinatesOfAddDataColumns;
    }

    public Workbook getWorkbook() {
        return workbook;
    }


}
