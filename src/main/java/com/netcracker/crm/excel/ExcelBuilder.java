package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 26.04.2017.
 */
public interface ExcelBuilder {
    Workbook getWorkbook(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName);

    Workbook getWorkbook(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, AdditionalData additionalData);

    Workbook getWorkbook(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, List<AdditionalData> additionalDataList);

    Workbook getWorkbookChart(LinkedHashMap<String, List<?>> table, String sheetName, AdditionalData additionalData);

    Workbook getWorkbookChart(LinkedHashMap<String, List<?>> table, String sheetName, List<AdditionalData> additionalDataList);


}
