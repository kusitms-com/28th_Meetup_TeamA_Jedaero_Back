package com.backend.domain.store.dto;

import com.backend.domain.user.dto.RepresentativeDto;
import com.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadStoresDto {

    private RepresentativeDto user;

    private List<StoresDto> stores;

    private int pageNumber;

    private Long totalCount;

    private boolean hasNext;

    public static ReadStoresDto from(Page<StoresDto> stores, User user) {
        return ReadStoresDto.builder()
                .user(RepresentativeDto.from(user))
                .stores(stores.getContent())
                .pageNumber(stores.getNumber())
                .totalCount(stores.getTotalElements())
                .hasNext(stores.hasNext())
                .build();
    }

}
