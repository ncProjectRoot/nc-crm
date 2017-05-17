package com.netcracker.crm.excel.impl.complaint;

import com.netcracker.crm.domain.model.Complaint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 17.05.2017.
 */
public class ComplaintConverter {
    LinkedHashMap<String, List<?>> convertComplaints(List<Complaint> complaints){
        List<String> customer_fullName = new ArrayList<>();
        List<Long> complaint_id = new ArrayList<>();
        List<String> complaint_title = new ArrayList<>();
        List<String> complaint_message = new ArrayList<>();
        List<LocalDateTime> complaint_date = new ArrayList<>();
        List<String> complaint_staus = new ArrayList<>();
        List<Long> order_id = new ArrayList<>();
        List<LocalDateTime> order_date_preffered = new ArrayList<>();
        List<LocalDateTime> order_date_finish = new ArrayList<>();
        List<String> order_status = new ArrayList<>();
        List<String> product_title = new ArrayList<>();

        for (Complaint complaint: complaints) {
            customer_fullName.add(getCustomerFullName(complaint));
            complaint_id.add(complaint.getId());
            complaint_title.add(complaint.getTitle());
            complaint_message.add(cutStringOnNewLines(complaint.getMessage()));
            complaint_date.add(complaint.getDate());
            complaint_staus.add(complaint.getStatus().getName());
            order_id.add(complaint.getId());
            order_date_preffered.add(complaint.getOrder().getPreferedDate());
            order_date_finish.add(complaint.getOrder().getDate());
            order_status.add(complaint.getOrder().getStatus().getName());
            product_title.add(complaint.getOrder().getProduct().getTitle());
        }

        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();

        data.put("Full_name", customer_fullName);
        data.put("Complaint_id", complaint_id);
        data.put("Complaint_title", complaint_title);
        data.put("Complaint_message", complaint_message);
        data.put("Complaint_date", complaint_date);
        data.put("Complaint_status", complaint_staus);
        data.put("Order_id", order_id);
        data.put("Order_date_preffered", order_date_preffered);
        data.put("Order_date_finish", order_date_finish);
        data.put("Order_status", order_status);
        data.put("Product_title", product_title);
        return data;
    }

    private String getCustomerFullName(Complaint complaint){
        return complaint.getCustomer().getFirstName()
        +" " + complaint.getCustomer().getMiddleName()
        +" " + complaint.getCustomer().getLastName();
    }

    private String cutStringOnNewLines(String string){
        int lineLength = 100;
        StringBuilder stringBuilder = new StringBuilder(string);
        while (lineLength < string.length()){
            stringBuilder.insert(lineLength, "\n");
            lineLength *=2;
        }
        return stringBuilder.toString();
    }
}
