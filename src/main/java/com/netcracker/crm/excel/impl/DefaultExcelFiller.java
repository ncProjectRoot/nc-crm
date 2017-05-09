package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ExcelFiller;
import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

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
    private final int rowStart = 1;
    private final int cellStart = 0;

    private List<LinkedHashMap<String, List<?>>> additionalDataTables;
    private List<Map<String, Coordinates>> coordinatesOfAddDataRow;
    private List<List<String>> additionalDataInnerTitles;
    private List<String> additionalDataOuterTitles;

    public DefaultExcelFiller(Workbook workbook, Map<String, List<?>> table, String sheetName) {
        this.workbook = workbook;
        this.table = table;
        this.sheetName = sheetName;
        titles = new ArrayList<>(table.keySet());
        this.workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);
    }

    public DefaultExcelFiller
            (Workbook workbook, Map<String, List<?>> table, String sheetName,
             List<LinkedHashMap<String, List<?>>> additionalDataTables,
             List<String> additionalDataOuterTitles) {
        this(workbook, table, sheetName);
        this.additionalDataTables = additionalDataTables;
        this.additionalDataOuterTitles = additionalDataOuterTitles;
        additionalDataInnerTitles = new ArrayList<>();
        for (int i = 0; i < additionalDataTables.size(); i++){
            List<String> localTitles = new ArrayList<>(additionalDataTables.get(i).keySet());
            additionalDataInnerTitles.add(localTitles);
        }
    }

     public Workbook fillExcel(){
        setTitles(titles, rowStart, cellStart, "Report data");
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
        coordinatesOfAddDataRow = new ArrayList<>();
        for (int i = 0; i < additionalDataInnerTitles.size(); i++){
            setTitles(additionalDataInnerTitles.get(i), additionalRowStart, additionalCellStart, additionalDataOuterTitles.get(i));
            fillData(additionalDataTables.get(i), additionalDataInnerTitles.get(i), additionalRowStart, additionalCellStart);
            coordinatesOfAddDataRow.add(new HashMap<>());
            calculateCoordinatesAdditionalData(coordinatesOfAddDataRow.get(i), additionalDataTables.get(i),
                    additionalDataInnerTitles.get(i), additionalRowStart, additionalCellStart);
            setColumnAutoSize(additionalCellStart, additionalDataInnerTitles.get(i));

            additionalCellStart += additionalDataInnerTitles.get(i).size() + cellStep;
        }
    }

    private void setTitles(List<String> titles,int rowStart, int cellStart, String outerTitle){
        sheet.createRow(rowStart-1).createCell(cellStart);
        mergeColumnsAndSetTitle(rowStart-1,cellStart, cellStart+titles.size(), outerTitle);
        setTitleCellStyle(sheet.getRow(0).getCell(0));
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
                row.createCell(j+1+cellStart);
                setValueFromTable(j+1+cellStart, currentValue, row);
                setValueCellStyle(row.getCell(j+1+cellStart));
            }
        }
    }

    private void setValueFromTable(int cellIndex, Object currentValue, Row row){
        if(currentValue instanceof String){
            row.getCell(cellIndex).setCellValue((String) currentValue);
        }
        else if(currentValue instanceof Long){
            row.getCell(cellIndex).setCellValue((Long) currentValue);
        }
        else if(currentValue instanceof Double){
            row.getCell(cellIndex).setCellValue((Double) currentValue);
        }
        else if(currentValue instanceof Boolean){
            row.getCell(cellIndex).setCellValue((Boolean) currentValue);
        }
        else if(currentValue instanceof LocalDateTime){
            row.getCell(cellIndex).setCellValue(currentValue.toString());
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

    private void calculateCoordinatesAdditionalData
            (Map<String, Coordinates> coordinatesOfAdditionalDataTableRows, Map<String, List<?>> table, List<String> titles, int rowStart, int cellStart){
        String currentTitle = sheet.getRow(rowStart-1).getCell(cellStart).getStringCellValue();

        int startColumn = cellStart + 2;;
        int startRow;
        int endColumn = cellStart + titles.size();
        int endRow;
        coordinatesOfAdditionalDataTableRows.put(currentTitle, new Coordinates(startColumn,rowStart,endColumn,rowStart));

        int border = table.get(titles.get(0)).size();
        for (int i = 0; i < border; i++){
            currentTitle = (String) table.get(titles.get(0)).get(i);
            startRow = i + rowStart + 1;
            endRow = startRow;
            coordinatesOfAdditionalDataTableRows.put(currentTitle, new Coordinates(startColumn,startRow,endColumn,endRow));
        }
    }

    private void setColumnAutoSize(int cellStart, List<String> titles){
        for(int i = cellStart; i < titles.size() + cellStart + 1; i++){
            sheet.autoSizeColumn(i);
        }
    }

    private void mergeColumnsAndSetTitle(int rowIndex, int cellStartIndex, int cellEndIndex, String title){
        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,cellStartIndex,cellEndIndex));
        Cell cell = sheet.getRow(rowIndex).getCell(cellStartIndex);
        cell.setCellValue(title);
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

    public List<Map<String, Coordinates>> getCoordinatesOfAddDataRow() {
        return coordinatesOfAddDataRow;
    }

    public Workbook getWorkbook() {
        return workbook;
    }


}
