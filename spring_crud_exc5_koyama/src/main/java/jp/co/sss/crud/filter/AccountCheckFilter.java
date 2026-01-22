package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;

@Component
public class AccountCheckFilter extends HttpFilter {
	@Override
	public void doFilter(
		HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		String requestURL = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = requestURL.substring(contextPath.length());
		
		
		 if (path.equals("/")
		            || path.equals("/login")
		            || path.startsWith("/css/")
		            || path.startsWith("/js/")
		            || path.startsWith("/images/")
		            || path.equals("/favicon.ico")
		           ){
			 chain.doFilter(request, response);
				return;
		 }
		
		HttpSession session = request.getSession();
		EmployeeBean loginUser =  (EmployeeBean) session.getAttribute("loginUser");
		if (loginUser == null) {
		    response.sendRedirect(contextPath + "/");
		    return;
		}


		
		System.out.println("loginUser = " + loginUser);
		System.out.println("authority = " + loginUser.getAuthority());

		//権限チェック
		Integer authority = loginUser.getAuthority();
		Integer loginEmpId = loginUser.getEmpId();

		if (authority == null) {
		    response.sendRedirect(contextPath + "/");
		    return;
		}

		if (authority == 2) {//管理者は２
		    chain.doFilter(request, response);
		    return;
		}
		
		if (authority !=2) {//社員登録は管理者のみ
		    if (path.startsWith("/regist")) {
		        response.sendRedirect(contextPath + "/");
		        return;
		    }
		    //社員削除は管理者のみ
		    if (path.startsWith("/delete")) {
		        response.sendRedirect(contextPath + "/");
		        return;
		    }

		 //自分以外の社員情報を変更できない
		
		    if (path.startsWith("/update")) {
		    	
                if ("POST".equalsIgnoreCase(request.getMethod())) {

                    // hidden の empId を取得
                    String formEmpId = request.getParameter("empId");
                    if (formEmpId != null) {
                        try {Integer formId = Integer.parseInt(formEmpId);

                            // 自分以外なら拒否
                            if (!formId.equals(loginEmpId)) {
                                response.sendRedirect(contextPath + "/");
                                return;
                            }

                        } catch (NumberFormatException ex) {
                            // ID が不正なら拒否
                            response.sendRedirect(contextPath + "/");
                            return;
                        }
                    }
                    //権限を変更しようとしたら拒否
                    String formAuthority = request.getParameter("authority");
                    if (formAuthority != null && !formAuthority.equals("1")) {
                        response.sendRedirect(contextPath + "/");
                        return;
                    }
                }
                }
		}
        // ここまで来たら許可
        chain.doFilter(request, response);
    }
}

	