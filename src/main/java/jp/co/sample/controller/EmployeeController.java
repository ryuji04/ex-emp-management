package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報のコントローラークラス.
 * 
 * @author adachiryuji
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public UpdateEmployeeForm setUpdateEmployee() {
		UpdateEmployeeForm  updateEmployeeForm=new UpdateEmployeeForm ();
		return updateEmployeeForm; 
	}
	
	
	/**
	 * 従業員情報を全件検索するメソッド.
	 * 
	 * @param model　リクエストスコープ　＊全件検索結果（従業員情報）を格納する
	 * @return　従業員情報一覧へフォワードする
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		
		List<Employee>employeeList=employeeService.showList();
		
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 従業員の詳細情報を表示するメソッド.
	 * 
	 * @param id ID
	 * @param model リクエストスコープ(IDから検索された従業員情報を格納)
	 * @return　従業員詳細ページへフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model) {
		Employee employee= employeeService.showDetail(Integer.valueOf(id));
		model.addAttribute("employee", employee);
		
		return "employee/detail";
	}
	
	/**
	 * 従業員情報を更新するメソッド
	 * 
	 * @param form　従業員情報のフォーム
	 * @return　従業員一覧ページへリダイレクト
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee=employeeService.showDetail(Integer.valueOf(form.getId()));
		employee.setDependentsCount(Integer.valueOf(form.getDependentsCount()));
		employeeService.update(employee);
		
		return "redirect:/employee/showList";
	}
	
	
	
}
