package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Region;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class AddressProxy extends Address {

    private long regionId;

    private RegionDao regionDao;

    public AddressProxy(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public Region getRegion() {
        if (super.getRegion() == null && regionId != 0) {
            super.setRegion(regionDao.findById(regionId));
        }
        return super.getRegion();
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }
}
