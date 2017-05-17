package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.request.ComplaintRowRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintDao extends CrudDao<Complaint> {

    List<Complaint> findByTitle(String title);

    List<Complaint> findAllByDate(LocalDate date);

    List<Complaint> findAllByCustomerId(Long id);

    List<Complaint> findComplaintRows(ComplaintRowRequest complaintRowRequest);

    Long getComplaintRowsCount(ComplaintRowRequest complaintRowRequest);

    List<String> findComplaintsTitleLikeTitle(String likeTitle);

    List<String> findComplaintsTitleByPmgId(String likeTitle, Long pmgId);

    List<String> findComplaintsTitleByCustId(String likeTitle, Long custId);

    Long checkOwnershipOfContactPerson(Long complaintId, Long custId);

    Long checkOwnershipOfCustomer(Long complaintId, Long custId);

    List<String> findComplaintsTitleForContactPerson(String likeTitle, Long custId);

    List<Complaint> findComplaintsByPmgIdAndArrayOfCustomerIdBetweenDates(Long pmg_id, List<Long> customer_id_list, LocalDateTime date_first, LocalDateTime date_last);

    List<Complaint> findComplaintsByPmgIdAndCustomerIdBetweenDates(Long pmg_id, Long customer_id, LocalDateTime date_first, LocalDateTime date_last);

    List<Complaint> findComplaintsByPmgIdBetweenDates(Long pmg_id, LocalDateTime date_first, LocalDateTime date_last);
}
