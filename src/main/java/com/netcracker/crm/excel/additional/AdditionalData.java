package com.netcracker.crm.excel.additional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 15.05.2017.
 */
public class AdditionalData {
    private LinkedHashMap<String, List<?>> data;
    private String dataName;
    private List<String> data_firstColumn_values;
    private String data_firstColumn_name;
    private List<String> data_firstRow_values;

    public AdditionalData(LinkedHashMap<String, List<?>> data, String dataName) {
        this.data = data;
        this.dataName = dataName;
        List<String> list = new ArrayList<>(data.keySet());
        this.data_firstColumn_name = list.get(0);
        this.data_firstColumn_values = (List<String>) data.get(data_firstColumn_name);
        this.data_firstRow_values = list.subList(1, list.size());
    }

    public LinkedHashMap<String, List<?>> getData() {
        return data;
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

    public String getXaxisName(){
        return dataName;
    }

    public List<String> getYaxisesNames(){
        return data_firstColumn_values;
    }
}
