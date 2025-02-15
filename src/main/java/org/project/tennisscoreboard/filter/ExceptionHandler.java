package org.project.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.project.tennisscoreboard.exception.MatchNotCreatedException;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.exception.PlayerInvalidNameException;

@WebFilter("/*")
public class ExceptionHandler implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    httpResponse.setContentType("text/html;charset=UTF-8");
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH,OPTIONS, DELETE");
    httpResponse.setHeader("Access-Control-Allow-Headers",
        "Content-Type, Authorization, X-Requested-With");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (IllegalArgumentException e) {
      httpResponse.getWriter().write("Invalid parameter");
      httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } catch (PlayerInvalidNameException e) {
      httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      httpResponse.getWriter().write(e.getMessage());
    } catch (MatchNotCreatedException | MatchNotFoundException e) {
      httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
