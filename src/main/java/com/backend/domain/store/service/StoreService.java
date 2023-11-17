package com.backend.domain.store.service;

import com.backend.domain.store.dto.CreateStoreRequest;
import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public void createStore(CreateStoreRequest request) {
        Category category = request.getCategory();
        List<Store> stores = request.getData().stream()
                .map(dto -> dto.toEntity(category))
                .peek(store -> {
                    store.getBusinessHours()
                            .forEach(businessHour -> businessHour.setStore(store));
                })
                .toList();
        storeRepository.saveAll(stores);
    }

}
