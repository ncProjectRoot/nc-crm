package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ChartBuilder;
import com.netcracker.crm.excel.ExcelFiller;
import com.netcracker.crm.excel.additional.Coordinates;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AN on 24.04.2017.
 */
public class DefaultExcelBuilder {

    public DefaultExcelBuilder(){}

    public Workbook getWorkbook
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName){
        return getExcelFiller(fileFormat, table, sheetName).fillExcel();
    }

    public Workbook getWorkbookChart
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, Map<String, List<String>> xColumns_yColumns){
        ExcelFiller defaultExcelFiller = getExcelFiller(fileFormat, table, sheetName);
        Workbook workbook = defaultExcelFiller.fillExcel();
        addChart(fileFormat, defaultExcelFiller, xColumns_yColumns);
        return workbook;
    }

    public Workbook getWorkbookChart
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<Map<String, List<String>>> xColumns_yColumns){
        ExcelFiller defaultExcelFiller = getExcelFiller(fileFormat, table, sheetName);
        Workbook workbook = defaultExcelFiller.fillExcel();
        addCharts(fileFormat, defaultExcelFiller, xColumns_yColumns);
        return workbook;
    }

    public Workbook getWorkbook
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<LinkedHashMap<String, List<?>>> additionalDataTables){
        return getExcelFiller(fileFormat, table, sheetName, additionalDataTables).fillExcel();
    }

    public Workbook getWorkbookChart
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, Map<String, List<String>> xColumns_yColumns, List<LinkedHashMap<String, List<?>>> additionalDataTables){
        ExcelFiller defaultExcelFiller = getExcelFiller(fileFormat, table, sheetName, additionalDataTables);
        Workbook workbook = defaultExcelFiller.fillExcel();
        addChart(fileFormat, defaultExcelFiller, xColumns_yColumns);
        return workbook;
    }

    public Workbook getWorkbookChart
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<Map<String, List<String>>> xColumns_yColumns, List<LinkedHashMap<String, List<?>>> additionalDataTables){
        ExcelFiller defaultExcelFiller = getExcelFiller(fileFormat, table, sheetName, additionalDataTables);
        Workbook workbook = defaultExcelFiller.fillExcel();
        addCharts(fileFormat, defaultExcelFiller, xColumns_yColumns);
        return workbook;
    }

    private void addCharts(ExcelFormat fileFormat, ExcelFiller excelFiller, List<Map<String, List<String>>> xColumns_yColumns){
        int chartStartCell = 0;
        for (int i = 0; i < xColumns_yColumns.size(); i++) {
            String xColumnName = (String) xColumns_yColumns.get(i).keySet().toArray()[0];
            Coordinates coordinates_X = excelFiller.getCoordinatesOfTableColumns().get(xColumnName);
            ArrayList<Coordinates> coordinates_Y = new ArrayList<Coordinates>();
            for (String string : xColumns_yColumns.get(i).get(xColumnName)){
                coordinates_Y.add(excelFiller.getCoordinatesOfTableColumns().get(string));
            }
            ChartBuilder chartBuilder = getChartBuilder(fileFormat, excelFiller.getWorkbook(), coordinates_X, coordinates_Y);
            chartStartCell = chartBuilder.buildChart(chartStartCell) + 2;
        }
    }

    private void addChart(ExcelFormat fileFormat, ExcelFiller excelFiller, Map<String, List<String>> xColumns_yColumns){
        int chartStartCell = 0;
        for (String xColumnName: xColumns_yColumns.keySet()) {
            Coordinates coordinates_X = excelFiller.getCoordinatesOfTableColumns().get(xColumnName);
            ArrayList<Coordinates> coordinates_Y = new ArrayList<Coordinates>();
            for (String string : xColumns_yColumns.get(xColumnName)){
                coordinates_Y.add(excelFiller.getCoordinatesOfTableColumns().get(string));
            }
            ChartBuilder chartBuilder = getChartBuilder(fileFormat, excelFiller.getWorkbook(), coordinates_X, coordinates_Y);
            chartStartCell = chartBuilder.buildChart(chartStartCell) + 2;
        }
    }

    private ExcelFiller getExcelFiller(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName) {
        int tableRowStart = 1;
        int tableCellStart = 0;
        switch (fileFormat) {
            case XLS:
                return new DefaultExcelFiller(new HSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
            case XLSX:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
            default:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
        }
    }

    private ExcelFiller getExcelFiller(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<LinkedHashMap<String, List<?>>> additionalDataTable) {
        int tableRowStart = 1;
        int tableCellStart = 0;
        switch (fileFormat) {
            case XLS:
                return new DefaultExcelFiller(new HSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart, additionalDataTable);
            case XLSX:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart, additionalDataTable);
            default:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart, additionalDataTable);
        }
    }

    private ChartBuilder getChartBuilder(ExcelFormat fileFormat, Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y){
        switch (fileFormat) {
            case XLS:
                return new HSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
            case XLSX:
                return new XSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
            default:
                return new XSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
        }
    }
}
