package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.dto.GraphDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2017
 */
public interface ComplaintService {

    Complaint persist(ComplaintDto dto);

    List<Complaint> findByTitle(String title);

    List<Complaint> findByDate(LocalDate date);

    List<Complaint> findByCustomerId(Long id);

    Complaint findById(Long id);

    boolean checkAccessToComplaint(User customer, Long id);

    boolean changeStatusComplaint(Long id, String type, User pmg);

    Map<String, Object> getComplaintRow(ComplaintRowRequest complaintRowRequest, User user, boolean individual);

    List<AutocompleteDto> getAutocompleteDto(String pattern, User user, boolean individual);

    GraphDto getStatisticalGraph(GraphDto graphDto);
}
