//package com.example.test.filter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.example.test.utils.HTTPResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.code.kaptcha.Constants;
//
//
///**
// * 验证码拦截器
// * @CreationDate:2020年7月16日下午4:27:07
// * @Author:NieQiang
// * @ComputerName:Administrator
// */
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        if("POST".equalsIgnoreCase(request.getMethod()) && "/login".equals(request.getServletPath())) {
//            String code = request.getParameter("code");
//            String kaptchaCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//            logger.info("用户输入验证码：{}========session中存的验证码：{}",code,kaptchaCode);
//            if(StringUtils.isEmpty(code)) {
//                response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(401);
//                PrintWriter printWriter = response.getWriter();
//                HTTPResponse httpResponse = HTTPResponse.error("验证码不能为空！","");
//                printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
//                printWriter.flush();
//                printWriter.close();
//                return;
//            }else if(!kaptchaCode.toLowerCase().equals(code.toLowerCase())) {
//                response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(401);
//                PrintWriter printWriter = response.getWriter();
//                HTTPResponse httpResponse = HTTPResponse.error("验证码错误！","");
//                printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
//                printWriter.flush();
//                printWriter.close();
//                return;
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
//
