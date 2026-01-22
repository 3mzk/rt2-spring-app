package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Employee findByEmpIdAndEmpPass(Integer empId, String empPass);//ログイン用に検索、１件のみ
	List<Employee> findAllByOrderByEmpIdAsc ();
	List<Employee> findByEmpNameContainingOrderByEmpIdAsc(String empName);
	List<Employee> findByDepartmentOrderByEmpIdAsc(Department department);
	

	
}

