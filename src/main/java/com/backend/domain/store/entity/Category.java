package com.backend.domain.store.entity;

import lombok.Getter;

@Getter
public enum Category {

    FOOD("음식점"), CAFE("카페"), BEAUTY("미용"), CULTURE("문화"), ETC("기타"), NONE("없음");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
