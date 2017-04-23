package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findCompanyById(Long id);
}
