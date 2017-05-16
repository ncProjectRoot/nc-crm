package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ChartBuilder;
import com.netcracker.crm.excel.ExcelBuilder;
import com.netcracker.crm.excel.ExcelFiller;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.Coordinates;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 24.04.2017.
 */
public class DefaultExcelBuilder implements ExcelBuilder{

    public DefaultExcelBuilder(){}

    public Workbook getWorkbook
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName){
        return getExcelFiller(fileFormat, table, sheetName).fillExcel();
    }

    public Workbook getWorkbook
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, AdditionalData additionalData){
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        return getWorkbook(fileFormat, table, sheetName, additionalDataList);
    }

    public Workbook getWorkbook
            (ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<AdditionalData> additionalDataList){
        List<LinkedHashMap<String, List<?>>> additionalDataTables = getAdditionalDataTables(additionalDataList);
        List<String> additionalDataTitles = getAdditionalDataTitles(additionalDataList);
        ExcelFiller defaultExcelFiller = getExcelFiller(fileFormat, table, sheetName, additionalDataTables, additionalDataTitles);
        return defaultExcelFiller.fillExcel();
    }

    public Workbook getWorkbookChart
            (LinkedHashMap<String, List<?>> table, String sheetName, AdditionalData additionalData){
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        return getWorkbookChart(table, sheetName, additionalDataList);
    }

    public Workbook getWorkbookChart
            (LinkedHashMap<String, List<?>> table, String sheetName, List<AdditionalData> additionalDataList){
        List<LinkedHashMap<String, List<?>>> additionalDataTables = getAdditionalDataTables(additionalDataList);
        List<String> additionalDataTitles = getAdditionalDataTitles(additionalDataList);
        ExcelFiller defaultExcelFiller = getExcelFiller(ExcelFormat.XLSX, table, sheetName, additionalDataTables, additionalDataTitles);
        Workbook workbook = defaultExcelFiller.fillExcel();
        addChartsAdditionalData(defaultExcelFiller, additionalDataList);
        return workbook;
    }

    private void addChartsAdditionalData(ExcelFiller excelFiller, List<AdditionalData> additionalDataList){
        int chartStartCell = 0;
        for (int i = 0; i < additionalDataList.size(); i++) {
            String xColumnName = additionalDataList.get(i).getXaxisName_Histogram();
            Coordinates coordinates_X = excelFiller.getCoordinatesOfAddDataRow().get(i).get(xColumnName);
            ArrayList<Coordinates> coordinates_Y = new ArrayList<Coordinates>();
            for (String string : additionalDataList.get(i).getYaxisesNames_Histogram()){
                coordinates_Y.add(excelFiller.getCoordinatesOfAddDataRow().get(i).get(string));
            }
            ChartBuilder chartBuilder = getHistohramChartBuilder(excelFiller.getWorkbook(), coordinates_X, coordinates_Y);
            chartStartCell = chartBuilder.buildChart(chartStartCell) + 2;
        }
    }

    private ExcelFiller getExcelFiller(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName) {
        switch (fileFormat) {
            case XLS:
                return new DefaultExcelFiller(new HSSFWorkbook(), table, sheetName);
            case XLSX:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName);
            default:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName);
        }
    }

    private ExcelFiller getExcelFiller(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<LinkedHashMap<String, List<?>>> additionalDataTable, List<String> additionalDataTitles) {
        switch (fileFormat) {
            case XLS:
                return new DefaultExcelFiller(new HSSFWorkbook(), table, sheetName, additionalDataTable, additionalDataTitles);
            case XLSX:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, additionalDataTable, additionalDataTitles);
            default:
                return new DefaultExcelFiller(new XSSFWorkbook(), table, sheetName, additionalDataTable, additionalDataTitles);
        }
    }

    private ChartBuilder getHistohramChartBuilder(Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y){
        return new XSSFHistogramChartBuilder(workbook, coordinates_X,coordinates_Y);
    }

    private List<LinkedHashMap<String, List<?>>> getAdditionalDataTables (List<AdditionalData> additionalDataList){
        List<LinkedHashMap<String, List<?>>> additionalDataTables = new ArrayList<>();
        for(int i = 0; i < additionalDataList.size(); i++){
            additionalDataTables.add(additionalDataList.get(i).getTable());
        }
        return additionalDataTables;
    }

    private List<String> getAdditionalDataTitles (List<AdditionalData> additionalDataList){
        List<String> additionalDataTitles = new ArrayList<>();
        for(int i = 0; i < additionalDataList.size(); i++){
            additionalDataTitles.add(additionalDataList.get(i).getDataName());
        }
        return additionalDataTitles;
    }
}
