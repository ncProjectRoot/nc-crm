package com.netcracker.crm.dto.mapper.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.real.RealAddress;
import com.netcracker.crm.domain.real.RealOrganization;
import com.netcracker.crm.domain.real.RealRegion;
import com.netcracker.crm.domain.real.RealUser;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.Mapper;
import com.netcracker.crm.dto.row.UserRowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Component
public class UserMapper {

    private OrganizationDao organizationDao;
    private AddressDao addressDao;
    private RegionDao regionDao;

    @Autowired
    public UserMapper(OrganizationDao organizationDao, AddressDao addressDao, RegionDao regionDao) {
        this.organizationDao = organizationDao;
        this.addressDao = addressDao;
        this.regionDao = regionDao;
    }

    public Mapper<UserDto, RealUser> dtoToModel() {
        return (dto, model) -> {
            model.setId(dto.getId());
            model.setFirstName(dto.getFirstName());
            model.setMiddleName(dto.getMiddleName());
            model.setLastName(dto.getLastName());
            model.setEmail(dto.getEmail());
            model.setPhone(dto.getPhone());
            model.setContactPerson(dto.isContactPerson());
            model.setEnable(dto.isEnable());
            model.setAccountNonLocked(dto.isAccountNonLocked());
            if (dto.getUserRole() != null) {
                model.setUserRole(UserRole.valueOf(dto.getUserRole()));
            }
            Organization organization = dto.getOrgId() != null ? organizationDao.findById(dto.getOrgId()) : null;
            model.setOrganization(organization);
            Address address = dto.getAddressId() != null ? addressDao.findById(dto.getAddressId()) : null;
            model.setAddress(address);
        };
    }

    public Mapper<UserDto, RealUser> dtoToModelForCreate() {
        return (dto, model) -> {
            dtoToModel().configure(dto, model);
            model.setPassword(dto.getPassword());
            if (model.getUserRole().equals(UserRole.ROLE_CUSTOMER)) {
                Address address = new RealAddress();
                address.setLatitude(dto.getAddressLatitude());
                address.setLongitude(dto.getAddressLongitude());
                address.setDetails(dto.getAddressDetails());
                address.setFormattedAddress(dto.getFormattedAddress());
                Region region = regionDao.findByName(dto.getAddressRegionName());
                if (region == null) {
                    region = new RealRegion();
                    region.setName(dto.getAddressRegionName());
                }
                address.setRegion(region);
                model.setAddress(address);

                Organization organization = organizationDao.findByName(dto.getOrganizationName());
                if (organization == null) {
                    organization = new RealOrganization();
                    organization.setName(dto.getOrganizationName());
                }
                model.setOrganization(organization);
            } else {
                model.setAddress(null);
                model.setOrganization(null);
            }
        };
    }

    public Mapper<User, UserRowDto> modelToRowDto() {
        return (model, rowDto) -> {
            rowDto.setId(model.getId());
            rowDto.setFirstName(model.getFirstName());
            rowDto.setMiddleName(model.getMiddleName());
            rowDto.setLastName(model.getLastName());
            rowDto.setEmail(model.getEmail());
            rowDto.setPhone(model.getPhone());
            rowDto.setContactPerson(model.isContactPerson());
            rowDto.setUserRole(model.getUserRole().getFormattedName());
            rowDto.setAccountNonLocked(model.isAccountNonLocked());
            rowDto.setEnable(model.isEnable());

            Organization organization = model.getOrganization();
            if (organization != null) {
                rowDto.setOrganizationName(organization.getName());
            }

            Address address = model.getAddress();
            if (address != null) {
                rowDto.setFormattedAddress(address.getFormattedAddress());
            }
        };
    }

    public Mapper<String, AutocompleteDto> modelLastNameToAutocomplete() {
        return (modelLastName, autocompleteDto) -> {
            autocompleteDto.setValue(modelLastName);
        };
    }

}
