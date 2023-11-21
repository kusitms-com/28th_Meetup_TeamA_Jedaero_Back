package com.backend.domain.popup.service;

import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.contract.entity.Contract;
import com.backend.domain.contract.repository.ContractRepository;
import com.backend.domain.popup.domain.EndDateType;
import com.backend.domain.popup.domain.Popup;
import com.backend.domain.popup.domain.ReservationType;
import com.backend.domain.popup.dto.request.PopupCreateRequest;
import com.backend.domain.popup.dto.response.PopupGetResponseDto;
import com.backend.domain.popup.repository.PopupRepository;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.StoreRepository;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PopupService {

    private final PopupRepository popupRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    public Page<PopupGetResponseDto> getPopups(LoginUser loginUser, int pageNumber) {
        User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Popup> popups = popupRepository.findPopupsPageByUserId(user.getId(), pageable);
        return popups.map(PopupGetResponseDto::from);
    }

    @Transactional
    public void createPopup(LoginUser loginUser, PopupCreateRequest popupCreateRequest) {

        EndDateType endDate = EndDateType.create(popupCreateRequest.endDate());

        Store store = storeRepository.findById(popupCreateRequest.storeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        LocalDateTime reservation = determineReservation(popupCreateRequest.reservation());

        User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Contract contract = contractRepository.findContractByUserIdAndStoreId(user.getId(), store.getStoreId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Popup popup = popupCreateRequest.toEntity(LocalDate.now().plusDays(endDate.getPlusDate()), reservation, store, contract);
        popupRepository.save(popup);
    }

    @Transactional
    public void deletePopup(LoginUser loginUser, Long popupId) {
        User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Popup deletePopup = popupRepository.findById(popupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POPUP_NOT_FOUND));

        List<Popup> popups = popupRepository.findPopupsByUserId(user.getId());

        if (!popups.contains(deletePopup)) {
            throw new BusinessException(ErrorCode.INVALID_POPUP);
        }
        popupRepository.deleteById(popupId);
    }

    private LocalDateTime determineReservation(String reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (ReservationType.isNow(reservation)) {
            String formattedDateTimeString = LocalDateTime.now().format(formatter);
            return LocalDateTime.parse(formattedDateTimeString, formatter);
        }

        try {
            return LocalDateTime.parse(reservation, formatter);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCode.INVALID_DATE);
        }
    }
}