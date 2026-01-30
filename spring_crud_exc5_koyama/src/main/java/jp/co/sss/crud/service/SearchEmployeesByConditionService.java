package jp.co.sss.crud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.EmployeeRepository;

@Service
public class SearchEmployeesByConditionService {

    @Autowired
    private EmployeeRepository repository;

    public List<EmployeeBean> execute(String address, Integer authority) {

        List<Employee> employees;

 
        if ((address != null && !address.isEmpty()) && authority != null) {
            employees = repository.findByAddressContainingAndAuthorityOrderByEmpIdAsc(address, authority);

        } else if (address != null && !address.isEmpty()) {
            employees = repository.findByAddressContainingOrderByEmpIdAsc(address);

        } else if (authority != null) {
            employees = repository.findByAuthorityOrderByEmpIdAsc(authority);

            } else {
            employees = repository.findAllByOrderByEmpIdAsc();
        }

        // Entity → Bean 変換
        List<EmployeeBean> beanList = new ArrayList<>();
        for (Employee emp : employees) {
            beanList.add(new EmployeeBean(
                    emp.getEmpId(),
                    emp.getEmpPass(),
                    emp.getEmpName(),
                    emp.getGender(),
                    emp.getAddress(),
                    emp.getBirthday(),
                    emp.getAuthority(),
                    emp.getDepartment().getDeptId()
            ));
        }

        return beanList;
    }
}