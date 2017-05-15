package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ChartBuilder;
import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;

import java.util.List;

/**
 * Created by AN on 09.05.2017.
 */
public class XSSFHistogramChartBuilder implements ChartBuilder {
    private Workbook workbook;
    private Sheet sourceSheet;
    private Sheet chartSheet;
    private CTChart ctChart;
    private CTPlotArea ctPlotArea;
    private CTBarChart ctBarChart;
    private Coordinates coordinates_X;
    private List<Coordinates> coordinates_Y;

    public XSSFHistogramChartBuilder(Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y) {
        this.workbook = workbook;
        this.coordinates_X = coordinates_X;
        this.coordinates_Y = coordinates_Y;
    }

    public int buildChart(int startRow){
        int endRow = createChart(startRow);
        addData();
        plotChart();
        return endRow;
    }

    private int createChart(int startRow){
        sourceSheet = workbook.getSheetAt(0);
        workbook.createSheet();
        chartSheet = workbook.getSheetAt(1);
        workbook.setSheetName(1, "Charts");
        Drawing drawing = chartSheet.createDrawingPatriarch();
        Coordinates coordinates = calculatePlotCoordinates(startRow);
        int leftTopCell = coordinates.getStartColumn();
        int leftTopRow = coordinates.getStartRow();
        int rightBottomCell = coordinates.getEndColumn();
        int rightBottomRow = coordinates.getEndRow();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, leftTopCell, leftTopRow, rightBottomCell, rightBottomRow);
        Chart chart = drawing.createChart(anchor);

        ctChart = ((XSSFChart)chart).getCTChart();

        CTTitle title = ctChart.addNewTitle();
        CTTx tx = title.addNewTx();
        CTTextBody rich = tx.addNewRich();
        rich.addNewBodyPr();  // body properties must exist, but can be empty
        CTTextParagraph para = rich.addNewP();
        CTRegularTextRun r = para.addNewR();
        Row row = sourceSheet.getRow(coordinates_X.getStartRow()-1);
        Cell cellTitle = row.getCell(coordinates_X.getStartColumn()-2);
        String chartTitle = cellTitle.getStringCellValue();
        r.setT(chartTitle);

        ctPlotArea = ctChart.getPlotArea();
        ctBarChart = ctPlotArea.addNewBarChart();
        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
        ctBoolean.setVal(true);
        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
        return rightBottomRow;
    }

    private void addData(){
        int valuesStartRow = coordinates_X.getStartRow()+ 2;
        Cell cell = sourceSheet.getRow(coordinates_X.getStartRow()+1).getCell(coordinates_X.getStartColumn()-1);
        String initSerieColumnAdress = cell.getAddress().toString().substring(0, 1);
        Cell cellStart = sourceSheet.getRow(coordinates_X.getStartRow()).getCell(coordinates_X.getStartColumn());
        Cell cellEnd = sourceSheet.getRow(coordinates_X.getEndRow()).getCell(coordinates_X.getEndColumn());
        String cellStartAdress = cellStart.getAddress().toString();
        String cellEndAdress = cellEnd.getAddress().toString();
        String headerReference = makeReference(cellStartAdress,cellEndAdress);

        for (int r = valuesStartRow; r < coordinates_Y.size() + valuesStartRow ; r++) {
            CTBarSer ctBarSer = ctBarChart.addNewSer();
            CTSerTx ctSerTx = ctBarSer.addNewTx();
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            ctStrRef.setF(sourceSheet.getSheetName()+"!$"+initSerieColumnAdress+"$"+ r);
            ctBarSer.addNewIdx().setVal(r-valuesStartRow);
            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            ctStrRef.setF(headerReference);
            CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            String cellAdress1 = cellStartAdress.substring(0, 1)
                    + String.valueOf(r);
            String cellAdress2 = cellEndAdress.substring(0, 1)
                    + String.valueOf(r);
            ctNumRef.setF(makeReference(cellAdress1, cellAdress2));

            //at least the border lines in Libreoffice Calc ;-)
            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] {0,0,0});
        }
    }

    private String makeReference(String cellAddess1, String cellAdress2){
        StringBuilder reference = new StringBuilder(sourceSheet.getSheetName()+"!$");
        reference.append(cellAddess1.substring(0, 1));
        reference.append('$');
        reference.append(cellAddess1.substring(1, cellAddess1.length()));
        reference.append(":$");
        reference.append(cellAdress2.substring(0, 1));
        reference.append('$');
        reference.append(cellAdress2.substring(1, cellAdress2.length()));
        return reference.toString();
    }

    private void plotChart(){
        //telling the BarChart that it has axes and giving them Ids
        ctBarChart.addNewAxId().setVal(123456);
        ctBarChart.addNewAxId().setVal(123457);

        //cat axis
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); //id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewDelete().setVal(false);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); //id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

        //val axis
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); //id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewDelete().setVal(false);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); //id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

        //legend
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
    }

    private Coordinates calculatePlotCoordinates(int startRow){
        int modifier = 2 + (coordinates_X.getEndColumn() - coordinates_X.getStartColumn())
                *coordinates_Y.size();
        int startCol = 0;
        int endCol = startCol + modifier;
        int endRow = startRow + 15 + coordinates_Y.size();
        return new Coordinates(startCol,startRow,endCol,endRow);
    }
}
