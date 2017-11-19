package org.jozif.demo4signinauthentication.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.jozif.demo4signinauthentication.common.Helper;
import org.jozif.demo4signinauthentication.common.KeyConstant;
import org.jozif.demo4signinauthentication.common.ResultConstant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author hongyu 2017-11-18
 */
@Component
@CommonsLog
@WebFilter(filterName = "PassFilter", urlPatterns = {"/"})
public class PassFilter extends OncePerRequestFilter {

    @Autowired
    private Helper helper;

    @Value("${demo4signInAuthentication.cookieMaxAgeHour}")
    private String cookieMaxAgeHour;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String[] excludedPageKeyWordArray = new String[]{"/pass"};
        String uri = request.getRequestURI();
        boolean isPass = false;

        //判断是否在过滤url之外
        for (String keyWord : excludedPageKeyWordArray) {
            if (uri.contains(keyWord)) {
                isPass = true;
                break;
            }
        }

        //判断session
        if (!isPass && (null != request.getSession().getAttribute(KeyConstant.SESSION_USER))) {
            isPass = true;
        }

        //判断cookie
        if (!isPass) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies){
                ServletContext application = request.getServletContext();
                String ticketName = (String) application.getAttribute(KeyConstant.TICKET_NAME);
                for (Cookie cookie : cookies) {
                    if (StringUtils.equals(cookie.getName(), ticketName)) {
                        Map<Integer, String> uidAndCookieMap = (Map<Integer, String>) application.getAttribute(KeyConstant.APPLICATION_UID_AND_COOKIE_MAP);
                        for (Integer key : uidAndCookieMap.keySet()) {
                            if (StringUtils.equals(uidAndCookieMap.get(key), cookie.getValue())) {
                                helper.signInProcedure(response, request);
                                isPass = true;
                                break;
                            }
                        }
                        break;
                    }
                }
            }

        }

        if (isPass) {
            filterChain.doFilter(request, response);
        } else {
            JSONObject result = new JSONObject();
            result.put("status", ResultConstant.SIGNIN_FIRST_STATUS);
            result.put("info", ResultConstant.SIGNIN_FIRST_INFO);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        }
    }
}