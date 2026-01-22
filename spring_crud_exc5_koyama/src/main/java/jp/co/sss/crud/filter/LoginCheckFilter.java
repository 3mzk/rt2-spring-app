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
public class LoginCheckFilter extends HttpFilter {
	

	@Override
	public void doFilter(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// リクエスト URL を取得
		String requestURL = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = requestURL.substring(contextPath.length());

		 System.out.println("URL = " + requestURL);
//		    System.out.println("contextPath = " + contextPath);
//		    System.out.println("path = " + path);

		    // ログイン画面、静的ファイルは除外
		   		    
		    if (path.equals("/")
		            || path.equals("/login")
		            || path.startsWith("/css/")
		            || path.startsWith("/js/")
		            || path.startsWith("/images/")
		            || path.equals("/favicon.ico")
		            ) {

			//リクエスト URL が「ログイン画面への遷移処理」、
			//「ログイン処理」宛ての場合、ログインチェックを実施せず、
			//リクエスト対象のコントローラの処理に移る
			chain.doFilter(request, response);
			return;
		}
			//セッション情報を取得
			HttpSession session = request.getSession(false);
			//セッション情報からユーザのログイン情報(セッション属性 userId)を取得
			EmployeeBean loginuser =  (session != null)
			        ? (EmployeeBean) session.getAttribute("loginUser"):
			null;

			if (loginuser == null) {
				//ログイン情報が存在しない場合（ログイン情報(userId) が null の場合）、
				//ログイン画面にリダイレクトする
				response.sendRedirect(contextPath + "/");
				return;
			} else {//ログイン済み
				chain.doFilter(request, response);
			}
		}
		

	}



