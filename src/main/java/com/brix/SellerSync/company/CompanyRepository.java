package com.brix.SellerSync.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {

}
