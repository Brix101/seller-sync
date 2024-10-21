package com.brix.SellerSync.company.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brix.SellerSync.company.Company;
import com.brix.SellerSync.company.CompanyEntity;
import com.brix.SellerSync.company.CompanyRepository;
import com.brix.SellerSync.company.service.CompanyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private Company companyEntityToCompany(CompanyEntity companyEntity) {
        return Company.builder()
            .id(companyEntity.getId())
            .name(companyEntity.getName())
            .build();
    }

    @Override
    public List<Company> listCompanies() {
        final List<CompanyEntity> foundCompanies = companyRepository.findAll();
        return foundCompanies.stream().map(company -> companyEntityToCompany(company)).collect(Collectors.toList());
    }

    

}
