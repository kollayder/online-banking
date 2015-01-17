package ua.kollayder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "SessionFilter", urlPatterns = {"/clientpage.xhtml", "/clientslist.xhtml","/operations.xhtml", "/"})
public class SessionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        if (session.getAttribute("client") != null) {
            chain.doFilter(request, response);
        } else
            request.getRequestDispatcher("/login.xhtml").forward(request, response);
//        chain.doFilter(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
