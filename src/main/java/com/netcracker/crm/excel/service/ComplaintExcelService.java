package com.netcracker.crm.excel.service;

import com.netcracker.crm.dto.ComplaintExcelDto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Pasha on 26.05.2017.
 */
public interface ComplaintExcelService {

    void generateCustomerComplaints(OutputStream stream, ComplaintExcelDto complaintExcelDto) throws IOException;
}
