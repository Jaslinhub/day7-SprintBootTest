package org.example.springboottest.Repository.Dao;

import org.example.springboottest.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer> {
}
