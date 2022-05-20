package basic;

import javax.servlet.*;
import java.io.IOException;

public class TestFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter Exec init");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter Exec doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
