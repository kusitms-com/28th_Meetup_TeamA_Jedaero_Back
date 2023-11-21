package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Category;

public interface StoresDto {

    Long getStoreId();

    String getStoreName();

    String getDescription();

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    Category getCategory();

    Double getDistance();

    Boolean getIsPicked();

}
