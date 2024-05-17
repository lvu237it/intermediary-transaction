/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.User;

/**
 *
 * @author Khuong Hung
 */
public class Authentication implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public Authentication() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        String url = request.getRequestURI() + "?" + request.getQueryString();

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (user != null) {
            // Nếu người dùng đã đăng nhập

            // Kiểm tra nếu đang truy cập vào trang đăng nhập, chuyển hướng đến trang chính
            if (url.contains("/login") || url.contains("/signup") || url.contains("/forget-password")) {
                response.sendRedirect(request.getContextPath() + "/homepage");
                return;
            }

            // Kiểm tra quyền truy cập của người dùng dựa trên vai trò của họ
            if (user.getRole() == 1) {
                // Nếu là admin, cho phép truy cập mọi trang
                filterchain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                // Nếu là user, kiểm tra truy cập vào các trang đặc biệt
                if (url.contains("/withdraw-manage")||url.contains("/reports-manage")) {
                    response.sendRedirect(request.getContextPath() + "/401");
                    return;
                }
            }
        } else {
            // Nếu người dùng chưa đăng nhập
            // Kiểm tra nếu truy cập vào các trang đặc biệt
            if (url.contains("/withdraw-manage")||url.contains("/reports-manage") || url.contains("/TransactionManagement.jsp")
                    || url.contains("/profile") || url.contains("/MyPurchaseOrder.jsp")
                    || url.contains("/sell") || url.contains("/purchase") || url.contains("/ordercreated")
                    || url.contains("/withdraw")) {
                response.sendRedirect(request.getContextPath() + "/401");
                return;
            }
        }

        // Nếu không phải trường hợp đặc biệt nào, tiếp tục chuyển tiếp yêu cầu
        filterchain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("Authorization:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Authorization()");
        }
        StringBuffer sb = new StringBuffer("Authorization(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
