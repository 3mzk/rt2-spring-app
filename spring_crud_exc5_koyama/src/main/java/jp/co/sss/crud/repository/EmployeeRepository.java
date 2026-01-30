package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Employee findByEmpIdAndEmpPass(Integer empId, String empPass);//ログイン用に検索、１件のみ
	List<Employee> findAllByOrderByEmpIdAsc (); //全従業員検索
	List<Employee> findByEmpNameContainingOrderByEmpIdAsc(String empName);
	List<Employee> findByDepartmentDeptIdOrderByEmpIdAsc(Integer deptId);
	
	List<Employee> findByAddressContainingAndAuthorityOrderByEmpIdAsc(String address, Integer authority);
	List<Employee> findByAuthorityOrderByEmpIdAsc(Integer authority);
	List<Employee> findByAddressContainingOrderByEmpIdAsc(String address);
}

