package loteca.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loteca.dominio.Usuario;

public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;		
		HttpSession session = req.getSession();
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if(usuarioLogado==null && !req.getRequestURL().toString().contains("/LotecaPainel/pages/login.jsf")){
		    res.sendRedirect("/LotecaPainel/pages/login.jsf");
		}else if(usuarioLogado!=null && req.getRequestURL().toString().contains("/LotecaPainel/pages/login.jsf")){
			res.sendRedirect("/LotecaPainel/pages/home.jsf");
		}else{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
