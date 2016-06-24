package br.ufg.inf.fs.slum.filter;

import br.ufg.inf.fs.slum.appToken.AppToken;
import br.ufg.inf.fs.slum.util.HibernateUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lucas.campos on 6/23/2016.
 */

public class AutenticacaoFilter implements Filter {

    private static List<String> urlsPermitidas = new ArrayList<String>();

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        if (!precisaEstarAutenticado(request.getRequestURI().toLowerCase()) || podeAcessar(request)) {
            chain.doFilter(req, resp);
            return;
        }

        ((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static boolean podeAcessar(HttpServletRequest request) {
        String token = obterTokenDaRequisicao(request);
        String login = obterLoginDaRequisicao(request);

        return login != null && token != null && isTokenValido(login, token);
    }

    private static String obterLoginDaRequisicao(HttpServletRequest request) {
        String login = request.getHeader("login");
        if (login == null || login.isEmpty()) {
            login = request.getParameter("login");
        }
        return login;
    }

    private static String obterTokenDaRequisicao(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }
        return token;
    }

    private  static boolean isTokenValido(String username, String token){
        HashMap<String, Object> mapProperties = new HashMap<String,Object>();
        mapProperties.put("username",username);
        mapProperties.put("token",token);

        AppToken appToken = (AppToken) HibernateUtil.getFieldEqUnique(AppToken.class,mapProperties);

        return appToken != null && appToken.getToken().equals(token);
    }

    public static boolean precisaEstarAutenticado(String url) {
        for (String urlPermitida : getUrlsPermitidas()) {
            if (url.contains(urlPermitida)) {
                return false;
            }
        }
        return true;
    }

    synchronized public static List<String> getUrlsPermitidas() {
        if (urlsPermitidas.isEmpty()) {
            urlsPermitidas = new ArrayList<String>();
            urlsPermitidas.add("/slumserver/slum/loginservice");
            urlsPermitidas.add("/ping");
            urlsPermitidas.add("/auth/login");
        }
        return urlsPermitidas;
    }

    @Override
    public void destroy() {
    }
}