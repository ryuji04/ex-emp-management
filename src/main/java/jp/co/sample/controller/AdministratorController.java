package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報のコントロールクラス.
 * 
 * @author adachiryuji
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	/**
	 * Insert用の管理者情報をリクエストスコープに格納するメソッド.
	 * 
	 * @return リクエストスコープに格納されたInsert用の管理者情報
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();
		return insertAdministratorForm;
	}
	
	/**
	 * 管理者情報入力画面へフォワードするメソッド.
	 * 
	 * @return 管理者情報入力画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return"administrator/insert";
	}
	
	/**
	 * 管理者情報フォームを管理者情報ドメインにコピーするメソッド.
	 * 
	 * @param form 管理者情報フォーム
	 * @return　管理者情報コントローラーへリダイレクト（作成途中）
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator=new Administrator();
		
		BeanUtils.copyProperties(form,administrator);
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
}














