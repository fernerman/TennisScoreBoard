package org.project.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.project.tennisscoreboard.exception.PlayerInvalidNameException;
import org.project.tennisscoreboard.util.JspHelper;

@WebFilter("/*")
public class ExceptionHandler implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    httpResponse.setContentType("text/html;charset=UTF-8");
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH,OPTIONS, DELETE");
    httpResponse.setHeader("Access-Control-Allow-Headers",
        "Content-Type, Authorization, X-Requested-With");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (PlayerInvalidNameException e) {
      var session = httpRequest.getSession();
      session.setAttribute("error", "Error: " + e.getMessage());
      httpRequest.getRequestDispatcher(JspHelper.getPath("new-match"))
          .forward(httpRequest, httpResponse);
    } catch (IllegalArgumentException e) {
      httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
      httpRequest.getRequestDispatcher(JspHelper.getPath("error-page"))
          .forward(httpRequest, httpResponse);
    } catch (Exception e) {
      httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
