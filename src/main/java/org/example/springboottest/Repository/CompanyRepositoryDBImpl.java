package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Repository.Dao.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CompanyRepositoryDBImpl implements CompanyRepository {
    @Autowired
    private CompanyJpaRepository companyRepository;

    @Override
    public void add(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(int id) {
            return companyRepository.findById(id).get();
    }

    @Override
    public Company updateCompanyById(int id, Company updatedCompany) {
        return companyRepository.save(updatedCompany);
    }

    @Override
    public void deleteCompanyById(int id) {
        companyRepository.deleteById(id);

    }

    @Override
    public List<Company> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1,pageSize)).getContent();
    }

    @Override
    public void clear() {
        companyRepository.deleteAll();
    }
}
