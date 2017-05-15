package com.netcracker.crm.excel.additional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 15.05.2017.
 */
public class AdditionalData {
    private LinkedHashMap<String, List<?>> table;
    private String dataName;
    private List<String> data_firstColumn_values;
    private String data_firstColumn_name;
    private List<String> data_firstRow_values;

    public AdditionalData() {
    }

    public AdditionalData(LinkedHashMap<String, List<?>> table, String dataName) {
        setTable(table);
        setDataName(dataName);
    }

    public AdditionalData(LinkedHashMap<String, List<?>> table) {
        setTable(table);
    }

    public AdditionalData(String dataName) {
        setDataName(dataName);
    }

    public void setTable(LinkedHashMap<String, List<?>> table) {
        this.table = table;
        List<String> list = new ArrayList<>(table.keySet());
        this.data_firstColumn_name = list.get(0);
        this.data_firstColumn_values = (List<String>) table.get(data_firstColumn_name);
        this.data_firstRow_values = list.subList(1, list.size());
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public LinkedHashMap<String, List<?>> getTable() {
        return table;
    }

    public String getDataName() {
        return dataName;
    }

    public List<String> getData_firstColumn_values() {
        return data_firstColumn_values;
    }

    public String getData_firstColumn_name() {
        return data_firstColumn_name;
    }

    public List<String> getData_firstRow_values() {
        return data_firstRow_values;
    }

    public String getXaxisName_Histogram(){
        return dataName;
    }

    public List<String> getYaxisesNames_Histogram(){
        return data_firstColumn_values;
    }

    public String getXaxisName_LineChart(){
        return dataName;
    }

    public List<String> getYaxisesNames_LineChart(){
        return data_firstColumn_values;
    }
}
