package com.backend.domain.store.dto;


import com.backend.domain.store.entity.Category;

public interface StoreDetailsDto {

    Long getStoreId();

    String getStoreName();

    String getDescription();

    Boolean getIsPicked();

    Category getCategory();

    String getAddress();

    String getPhoneNumber();

    Double getDistance();

    String getMapUrl();

}
