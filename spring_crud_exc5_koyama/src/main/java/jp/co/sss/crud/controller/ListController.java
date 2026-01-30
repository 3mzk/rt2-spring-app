package jp.co.sss.crud.controller;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.service.SearchAllEmployeesService;
import jp.co.sss.crud.service.SearchEmployeesByConditionService;
import jp.co.sss.crud.service.SearchForEmployeesByDepartmentService;
import jp.co.sss.crud.service.SearchForEmployeesByEmpNameService;

@Controller
public class ListController {

	@Autowired
	SearchAllEmployeesService searchAllEmployeesService;

	@Autowired
	SearchForEmployeesByEmpNameService searchForEmployeesByEmpNameService;

	@Autowired
	SearchForEmployeesByDepartmentService searchForEmployeesByDepartmentService;
	
	@Autowired
	SearchEmployeesByConditionService searchEmployeesByConditionService;

	/**
	 * 社員情報を全件検索した結果を出力
	 *
	 * @param model モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
//	@Autowired
//    private SearchAllEmployeesAjaxService searchAllEmployeesAjaxService;
    @Autowired
    private EmployeeRepository repository;

    @ModelAttribute("loginUser")
    public EmployeeBean setLoginUser(HttpSession session) {
        return (EmployeeBean) session.getAttribute("loginUser");
    }


 
	
	
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public String findAll(
			@RequestParam(required = false) String address,
	        @RequestParam(required = false) Integer authority,
	        @RequestParam(required = false) String sortOrder, 
			Model model,HttpServletRequest request) {
		// ★ ここでログ出力
	    System.out.println("===== 条件検索ログ =====");
	    System.out.println("address = " + address);
	    System.out.println("authority = " + authority);
	    System.out.println("sortOrder = " + sortOrder);
	    System.out.println("=======================");


		List<EmployeeBean> allEmployeeList = null;
		//TODO SearchAllEmployeesService完成後にコメントを外す
//				allEmployeeList = searchAllEmployeesService.execute();
				
				
				// 条件検索
			    if ((address != null && !address.isEmpty())
			            || authority != null) {

			    	allEmployeeList = searchEmployeesByConditionService
			                .execute(address, authority);

			    } else {
			        // 全件検索
			    	allEmployeeList = searchAllEmployeesService.execute();
			    }

			    
			    
				if ("desc".equals(sortOrder)) {
			        allEmployeeList.sort(Comparator.comparing(EmployeeBean::getEmpId).reversed());
			    } else {
			        allEmployeeList.sort(Comparator.comparing(EmployeeBean::getEmpId));
			    }
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("employees", allEmployeeList);
		
		return "list/list";
		
	}
	

	/**
	 * 社員情報を社員名検索した結果を出力
	 *
	 * @param empName 検索対象の社員名
	 * @param model モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/list/empName", method = RequestMethod.GET)
	public String findByEmpName(String empName, Model model) {

		List<EmployeeBean> searchByEmpNameList = null;

		//TODO SearchForEmployeesByEmpNameService完成後にコメントを外す
				searchByEmpNameList = searchForEmployeesByEmpNameService.execute(empName);

		model.addAttribute("employees", searchByEmpNameList);
		return "list/list";
	}

	/**
	 * 社員情報を部署ID検索した結果を出力
	 *
	 * @param deptId 検索対象の部署ID
	 * @param model モデル
	 * @return 選先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/list/deptId", method = RequestMethod.GET)
	public String findByDeptId(Integer deptId, Model model) {

		List<EmployeeBean> searchByDepartmentList = null;

		//TODO SearchForEmployeesByDepartmentService完成後にコメントを外す
				searchByDepartmentList=searchForEmployeesByDepartmentService.execute(deptId);

		model.addAttribute("employees", searchByDepartmentList);
		return "list/list";
	}
	
	
}
