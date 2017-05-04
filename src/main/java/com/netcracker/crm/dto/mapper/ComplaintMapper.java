package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.dto.ComplaintDto;
import org.modelmapper.PropertyMap;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2017
 */
public class ComplaintMapper extends PropertyMap<ComplaintDto, Complaint> {

    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        map().setMessage(source.getMessage());
        if (source.getStatus() != null) {
            map().setStatus(ComplaintStatus.valueOf(source.getStatus()));
        }

    }
}
