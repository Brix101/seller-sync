package com.brix.SellerSync.company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brix.SellerSync.company.service.CompanyService;

import lombok.extern.java.Log;

@RestController
@Log
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(path = "/companies")
    public ResponseEntity<List<Company>> listCompanies() {
        return new ResponseEntity<List<Company>>(companyService.listCompanies(), HttpStatus.OK);
    }

    @GetMapping(path = "/companies/{id}")
    public ResponseEntity<Company> retrieveCompany(@PathVariable Long id) {
        final Optional<Company> foundCompany = companyService.getCompanyById(id);

        return foundCompany
        .map(company -> new ResponseEntity<Company>(company, HttpStatus.OK))
        .orElse(new ResponseEntity<Company>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/companies")
    public ResponseEntity<Company> createCompany(@RequestBody final Company company) {
        final Company savedCompany = companyService.save(company);
        return new ResponseEntity<Company>(savedCompany, HttpStatus.CREATED);
    }

    @PutMapping(path = "/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody final Company company) {
        final Optional<Company> foundCompany = companyService.getCompanyById(id);

        return foundCompany
        .map(existingCompany -> {
            company.setId(id);
            final Company updatedCompany = companyService.save(company);
            return new ResponseEntity<Company>(updatedCompany, HttpStatus.OK);
        })
        .orElse(new ResponseEntity<Company>(HttpStatus.NOT_FOUND));
    }
}
