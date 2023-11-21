package com.backend.domain.store.service;

import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.store.dto.*;
import com.backend.domain.store.entity.BusinessHour;
import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Pick;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.BusinessHourRepository;
import com.backend.domain.store.repository.PickRepository;
import com.backend.domain.store.repository.StoreRepository;
import com.backend.domain.university.entity.University;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void createPick(LoginUser loginUser, PickRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(RuntimeException::new);
        pickRepository.findByUserAndStore(user, store).ifPresent(pick -> {
            throw new RuntimeException();
        });
        Pick pick = user.createPick(store);
        pickRepository.save(pick);
    }

    public void deletePick(LoginUser loginUser, PickRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(RuntimeException::new);
        Pick pick = pickRepository.findByUserAndStore(user, store).orElseThrow(RuntimeException::new);
        user.delete(pick);
        pickRepository.delete(pick);
    }

    public ReadStoresDto readStores(LoginUser loginUser, ReadRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        University university = user.getUniversity();
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        String keyword = getKeyword(request);
        if (request.getIsPicked()) {
            if (request.getCategory().equals(Category.NONE)) {
                Page<StoresDto> stores = storeRepository.findAllContainsNameAndPicked(user.getId(), university.getLongitude(), university.getLatitude(), keyword, pageRequest);
                return ReadStoresDto.from(stores);
            }
            Page<StoresDto> stores = storeRepository.findAllContainsNameAndPickedAndCategory(user.getId(), university.getLongitude(), university.getLatitude(), keyword, request.getCategory().name(), pageRequest);
            return ReadStoresDto.from(stores);
        }
        if (request.getCategory().equals(Category.NONE)) {
            Page<StoresDto> stores = storeRepository.findAllContainsName(user.getId(), university.getLongitude(), university.getLatitude(), keyword, pageRequest);
            return ReadStoresDto.from(stores);
        }
        Page<StoresDto> stores = storeRepository.findAllContainsNameAndCategory(user.getId(), university.getLongitude(), university.getLatitude(), keyword, request.getCategory().name(), pageRequest);
        return ReadStoresDto.from(stores);
    }

    private String getKeyword(ReadRequest request) {
        return "%" + request.getName() + "%";
    }

}
