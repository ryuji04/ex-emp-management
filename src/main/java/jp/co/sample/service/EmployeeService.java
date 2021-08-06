package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員情報のサービスクラス.
 * 
 * @author adachiryuji
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報リポジトリから全件検索メソッドを呼び出す.
	 * 
	 * @return 全件検索（従業員情報）の結果
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();

		return employeeList;
	}
	
	/**
	 * 従業員情報リポジトリからid検索メソッドを呼び出す.
	 * 
	 * @param id ID
	 * @return id検索（従業員情報）の結果
	 */
	public Employee showDetail(Integer id) {
		Employee employee=employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新する.
	 * 
	 * @param employee　従業員情報
	 */
	public  void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	
}







































