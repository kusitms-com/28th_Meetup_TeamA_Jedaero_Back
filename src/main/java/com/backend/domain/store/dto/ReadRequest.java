package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadRequest {

    @Schema(name = "isPicked", example = "false")
    private Boolean isPicked;

    @Schema(name = "name", example = "")
    private String name;

    @Schema(name = "category", example = "FOOD")
    private Category category;

    @Schema(name = "pageSize", example = "40")
    private int pageSize;

    @Schema(name = "pageNumber", example = "0")
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

    @JsonIgnore
    public String getStoreNameForQuery() {
        return "%" + getName() + "%";
    }

}


