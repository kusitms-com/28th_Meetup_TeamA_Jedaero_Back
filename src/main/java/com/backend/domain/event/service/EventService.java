package com.backend.domain.event.service;

import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.contract.entity.Contract;
import com.backend.domain.contract.repository.ContractRepository;
import com.backend.domain.event.dto.CreateEventRequest;
import com.backend.domain.event.dto.ReadCouponsDto;
import com.backend.domain.event.dto.ReadEventsDto;
import com.backend.domain.event.dto.ReadEventsRequest;
import com.backend.domain.event.entity.Event;
import com.backend.domain.event.repository.EventRepository;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.StoreRepository;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final ContractRepository contractRepository;

    public void createEvent(LoginUser loginUser, CreateEventRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(RuntimeException::new);
        Contract contract = contractRepository.findByUniversityAndStore(user.getUniversity(), store).orElseThrow(RuntimeException::new);
        Event event = request.toEntity();
        contract.add(event);
    }

    public ReadCouponsDto readCoupons(LoginUser loginUser, ReadEventsRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        List<Contract> contracts = contractRepository.findAllByUniversity(user.getUniversity());
        Page<Event> coupons = eventRepository.findAllByTypeAndContractIn(request.getType(), contracts, LocalDate.now(), request.getPage());
        return ReadCouponsDto.from(coupons);
    }

    public ReadEventsDto readEvents(LoginUser loginUser, ReadEventsRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        List<Contract> contracts = contractRepository.findAllByUniversity(user.getUniversity());
        Page<Event> events = eventRepository.findAllByTypeAndContractIn(request.getType(), contracts, LocalDate.now(), request.getPage());
        return ReadEventsDto.from(events);
    }

}
