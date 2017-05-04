package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 26.04.2017.
 */
public interface ExcelBuilder {
    Workbook getWorkbook(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName);

    Workbook getWorkbookChart(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, String xColumnName, List<String> yColumnName);


}
