package com.backend.domain.store.service;

import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.store.dto.CreatePickRequest;
import com.backend.domain.store.dto.CreateStoreRequest;
import com.backend.domain.store.dto.ReadStoreDetailsDto;
import com.backend.domain.store.dto.StoreDetailsDto;
import com.backend.domain.store.entity.BusinessHour;
import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Pick;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.BusinessHourRepository;
import com.backend.domain.store.repository.PickRepository;
import com.backend.domain.store.repository.StoreRepository;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final BusinessHourRepository businessHourRepository;

    private final UserRepository userRepository;

    private final PickRepository pickRepository;

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

    public ReadStoreDetailsDto readStoreDetails(LoginUser loginUser, Long storeId) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        StoreDetailsDto storeDetail = storeRepository.findStoreDetailById(user.getId(), storeId, user.getUniversity().getLatitude(), user.getUniversity().getLongitude()).orElseThrow(RuntimeException::new);
        List<BusinessHour> businessHours = businessHourRepository.findByStoreStoreId(storeId);
        return ReadStoreDetailsDto.from(storeDetail, businessHours);
    }

    public void createPick(LoginUser loginUser, CreatePickRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(RuntimeException::new);
        pickRepository.findByUserAndStore(user, store).ifPresent(pick -> {
            throw new RuntimeException();
        });
        Pick pick = user.createPick(store);
        pickRepository.save(pick);
    }
}
