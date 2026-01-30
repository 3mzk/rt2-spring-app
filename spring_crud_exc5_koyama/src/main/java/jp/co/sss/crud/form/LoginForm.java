package jp.co.sss.crud.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.co.sss.crud.example.Groups1;
import jp.co.sss.crud.example.Groups2;

public class LoginForm {
	
	@NotNull
	@Max(value = 99999)
	@Min(value = 1)
	/** 社員ID */
	private Integer empId;
	
	@NotBlank(groups = Groups1.class)
	@Size(max=16, groups = Groups2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$" , groups = Groups2.class)
	/** パスワード */
	private String empPass;

	/**
	 * 社員IDの取得
	 *
	 * @return 社員ID
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * 社員IDのセット
	 *
	 * @param empId 社員ID
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * パスワードの取得
	 *
	 * @return パスワード
	 */
	public String getEmpPass() {
		return empPass;
	}

	/**
	 * パスワードのセット
	 *
	 * @param empPass パスワード
	 */
	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}
}
