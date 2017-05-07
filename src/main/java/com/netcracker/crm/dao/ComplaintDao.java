package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.request.ComplaintRowRequest;

import java.time.LocalDate;
import java.util.List;

public interface ComplaintDao extends CrudDao<Complaint> {

    List<Complaint> findByTitle(String title);

    List<Complaint> findAllByDate(LocalDate date);

    List<Complaint> findAllByCustomerId(Long id);

    List<Complaint> findComplaintRows(ComplaintRowRequest complaintRowRequest);

    Long getComplaintRowsCount(ComplaintRowRequest complaintRowRequest);

    List<String> findProductsTitleLikeTitle(String likeTitle);

    List<String> findProductsTitleByPmgId(String likeTitle, Long pmgId);
}
