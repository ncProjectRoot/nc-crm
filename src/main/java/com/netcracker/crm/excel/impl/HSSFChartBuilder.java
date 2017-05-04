package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ChartBuilder;
import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public class HSSFChartBuilder implements ChartBuilder {
    private Workbook workbook;
    private Coordinates coordinates_X;
    private List<Coordinates> coordinates_Y;

    HSSFChartBuilder(Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y) {
        this.workbook = workbook;
        this.coordinates_X = coordinates_X;
        this.coordinates_Y = coordinates_Y;
    }
    public void buildChart(){
        System.out.println("This is not finished chart");
    }
}
