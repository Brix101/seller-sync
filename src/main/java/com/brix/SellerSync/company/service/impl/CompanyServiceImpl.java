package com.brix.SellerSync.company.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public Optional<Company> getCompanyById(Long id) {
        final Optional<CompanyEntity> foundCompany = companyRepository.findById(id);
        return foundCompany.map(company -> companyEntityToCompany(company));    
    }

    @Override
    public Company save(Company company) {
        final CompanyEntity companyEntity = CompanyEntity.builder()
            .id(company.getId())
            .name(company.getName())
            .build();
        
        final CompanyEntity savedCompanyEntity = companyRepository.save(companyEntity);
        return companyEntityToCompany(savedCompanyEntity);
    }


    @Override
    public void deleteCompany(Long id) {
        try{
            companyRepository.deleteById(id);
        } catch (final EmptyResultDataAccessException ex) {
            log.error("Error deleting company with id: {}", id, ex);
        }
    }

}
