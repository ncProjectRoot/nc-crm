package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Region;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class AddressProxy extends Address {

    private Long regionId;
    private RegionDao regionDao;

    public AddressProxy(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public Region getRegion() {
        if (super.getRegion() == null && regionId != null) {
            super.setRegion(regionDao.findById(regionId));
        }
        return super.getRegion();
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}
