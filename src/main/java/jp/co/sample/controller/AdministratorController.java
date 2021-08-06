package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
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
	private HttpSession session;

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
		return "administrator/insert";
	}

	/**
	 * 管理者情報フォームを管理者情報ドメインにコピーするメソッド.
	 * 
	 * @param form 管理者情報フォーム
	 * @return 管理者情報コントローラーへリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();

		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);

		

		return "redirect:/";
	}

	/**
	 * ログインフォームをインスタンス化するメソッド.
	 * 
	 * @return ログインフォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}

	/**
	 * ログイン画面へフォワードするメソッド
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * 管理者情報を登録するメソッド
	 * 
	 * @param form  管理者情報のフォーム
	 * @param model リクエストスコープ
	 * @return 管理者情報（メールアドレスorパスワード）が不正⇒エラーが発生
	 *         管理者情報（メールアドレスorパスワード）が適当⇒従業員情報リストへフォワード
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {

		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());

		if (administrator == null) {
			model.addAttribute("failure", "メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		}

		session.setAttribute("administratorName", administrator);
		return "forward:/employee/showList";

	}

	/**
	 * ログアウトするメソッド.
	 * 
	 * @return ログアウト画面へリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();

		return "redirect:/";
	}
}
