package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadStoresRequest {

    private Boolean isPicked;

    private String name;

    private Category category;

    private int pageSize;

    private int pageNumber;

    {
        pageSize = 40;
        pageNumber = 0;
        category = Category.NONE;
        isPicked = false;
    }

    public String getName() {
        return name == null ? "" : name;
    }

}


