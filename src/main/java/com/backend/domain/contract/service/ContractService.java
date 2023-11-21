package com.backend.domain.contract.service;

import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.contract.dto.CreateContractRequest;
import com.backend.domain.contract.dto.ReadContractDetailsDto;
import com.backend.domain.contract.dto.ReadContractsDto;
import com.backend.domain.contract.dto.UpdateContractRequest;
import com.backend.domain.contract.entity.Contract;
import com.backend.domain.contract.repository.ContractRepository;
import com.backend.domain.store.dto.ReadRequest;
import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.repository.StoreRepository;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    public void createContract(LoginUser loginUser, CreateContractRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(RuntimeException::new);
        Contract contract = request.toEntity(user, store);
        contract.getBenefits().forEach(benefit -> benefit.add(contract));
        contractRepository.save(contract);
    }

    public ReadContractsDto readContracts(LoginUser loginUser, ReadRequest request) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Sort sort = Sort.by("createdTime").descending();
        PageRequest page = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        page.withSort(sort);
        if (request.getCategory().equals(Category.NONE)) {
            return ReadContractsDto.from(contractRepository.findAllByUniversityAndStoreName(user.getUniversity(), request.getStoreNameForQuery(), page));
        }
        return ReadContractsDto.from(contractRepository.findAllByUniversityAndStoreNameAndCategory(user.getUniversity(), request.getStoreNameForQuery(), request.getCategory(), page));
    }

    public ReadContractDetailsDto readContractDetails(LoginUser loginUser, Long storeId) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(RuntimeException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(RuntimeException::new);
        Contract contract = contractRepository.findByUniversityAndStore(user.getUniversity(), store).orElseThrow(RuntimeException::new);
        return ReadContractDetailsDto.from(contract);
    }

}
