package com.brix.SellerSync.company.service;

import java.util.List;
import java.util.Optional;

import com.brix.SellerSync.company.Company;

public interface CompanyService {

    List<Company> listCompanies();

    Optional<Company> getCompanyById(Long id);

    Company save(Company company);

    void deleteCompany(Long id);
}
