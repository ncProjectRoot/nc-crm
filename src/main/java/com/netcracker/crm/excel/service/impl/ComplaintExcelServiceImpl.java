package com.netcracker.crm.excel.service.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.dto.ComplaintExcelDto;
import com.netcracker.crm.excel.ExcelMapKey;
import com.netcracker.crm.excel.converter.ExcelConverter;
import com.netcracker.crm.excel.drawer.ExcelDrawer;
import com.netcracker.crm.excel.service.AbstractExcelService;
import com.netcracker.crm.excel.service.ComplaintExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 26.05.2017.
 */
@Service
public class ComplaintExcelServiceImpl extends AbstractExcelService<Complaint> implements ComplaintExcelService{
    private final ComplaintDao complaintDao;
    private final ExcelConverter converter;

    private String[] titles = {
            "№",
            "Customer full name",
            "Complaint title",
            "Product title",
            "Complaint status",
            "Created date"
    };

    @Autowired
    public ComplaintExcelServiceImpl(ExcelConverter converter, ExcelDrawer drawer, ComplaintDao complaintDao) {
        super(drawer);
        this.converter = converter;
        this.complaintDao = complaintDao;
    }


    @Override
    public void generateCustomerComplaints(OutputStream stream, ComplaintExcelDto complaintExcelDto) throws IOException {
        LocalDate from = convertString(complaintExcelDto.getDateFrom());
        LocalDate to = convertString(complaintExcelDto.getDateTo());
        complaintExcelDto.setIdProduct(checkId(complaintExcelDto.getIdProduct()));
        List<Complaint> complaints = complaintDao.findAllByProductIds(Arrays.asList(complaintExcelDto.getIdProduct()), from, to, complaintExcelDto.getOrderByIndex());
        LocalDate[] range = calculateRange(from, to);
        Workbook workbook = parseData(complaints, range);
        workbook.write(stream);
        stream.close();
    }


    @Override
    public Map<ExcelMapKey, List<?>> convertToMap(List<Complaint> objects) {
        return converter.convertComplaintsToMap(objects);
    }

    @Override
    public Map<LocalDate, Map<String, Integer>> prepareDataChart(LocalDate[] range, List<Complaint> objects) {
        Map<LocalDate, Map<String, Integer>> result = new LinkedHashMap<>();
        LocalDate temp = null;
        for (LocalDate date : range) {
            Map<String, Integer> products = new LinkedHashMap<>();
            for (Complaint complaint : objects) {
                LocalDate complaintDate = complaint.getDate().toLocalDate();
                int value = 0;
                if ((temp == null && date.isAfter(complaintDate))
                        || (temp != null && date.isAfter(complaintDate) && temp.isBefore(complaintDate))) {
                    value = 1;
                }
                products.merge(complaint.getOrder().getProduct().getTitle(), value, (a, b) -> a + b);
            }
            temp = LocalDate.from(date);
            result.put(date, products);
        }
        return result;
    }

    @Override
    public String[] getTitles() {
        return titles;
    }
}
