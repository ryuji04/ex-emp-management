package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員情報のrepository.
 * 
 * @author adachiryuji
 *
 */
@Repository
public class EmployeeRepository {
	private final static RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * 従業員情報の全件検索を行うメソッド.
	 * 
	 * @return 従業員の全件検索結果
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees "
				+ " ORDER BY hire_date desc";

		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return employeeList;

	}

	/**
	 * idから従業員情報を検索するメソッド.
	 * 
	 * @param id 主キー
	 * @return 従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id ";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;

	}

	/**
	 * 従業員情報を更新するメソッド
	 * 
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET name=:name,image=:image,gender=:gender,hire_date=:hireDate,mail_address=:mailAddress,zip_code=:zipCode,address=:address,telephone=:telephone,salary=:salary,characteristics=:characteristics,dependents_count=:dependentsCount WHERE id=:id";

		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		template.update(sql, param);

	}

}
