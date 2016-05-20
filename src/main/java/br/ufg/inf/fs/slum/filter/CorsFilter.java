package br.ufg.inf.fs.slum.filter;

import com.sun.jersey.core.util.Priority;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Priority(Integer.MIN_VALUE)
public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
		String origin = req.getHeader("Origin");

		boolean options = "OPTIONS".equals(req.getMethod());
		if (options) {
			if (origin == null) return;
			resp.addHeader("Access-Control-Allow-Headers", "origin, authorization, accept, content-type, x-requested-with, login, accept-language");
			resp.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");
			resp.addHeader("Access-Control-Max-Age", "3600");
		}

		resp.addHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
		resp.addHeader("Access-Control-Expose-Headers", "acoesPermitidas");
		resp.addHeader("Access-Control-Allow-Credentials", "true");

		if (!options) chain.doFilter(req, resp);
	}
}