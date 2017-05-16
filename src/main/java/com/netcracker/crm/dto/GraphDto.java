package com.netcracker.crm.dto;

import java.util.List;

/**
 * @author Karpunets
 * @since 16.05.2017
 */
public class GraphDto {

    private List<Long> elementIds;
    private String fromDate;
    private String toDate;
    private List<String> labels;
    private List<List<Long>> series;

    public List<Long> getElementIds() {
        return elementIds;
    }

    public void setElementIds(List<Long> elementIds) {
        this.elementIds = elementIds;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<List<Long>> getSeries() {
        return series;
    }

    public void setSeries(List<List<Long>> series) {
        this.series = series;
    }

}
