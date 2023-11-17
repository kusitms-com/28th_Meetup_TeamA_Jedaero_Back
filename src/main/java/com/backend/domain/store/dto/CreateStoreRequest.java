package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoreRequest {

    private Category category;

    private List<CreateStoreDto> data;

}
