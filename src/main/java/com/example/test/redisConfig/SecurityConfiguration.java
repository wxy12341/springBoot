//package com.example.test.redisConfig;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Date;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.example.test.bean.UserBean;
//import com.example.test.filter.LoginFilter;
//import com.example.test.serviceImpl.AdminServiceImpl;
//import com.example.test.utils.HTTPResponse;
//import com.example.test.utils.UserIPUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AccountExpiredException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.CredentialsExpiredException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.session.InvalidSessionStrategy;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import cn.hutool.http.HttpStatus;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
//
//    @Autowired
//    private AdminServiceImpl adminServiceImpl;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminServiceImpl);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/captcha.jpg");//?????????security???????????????
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //?????????????????????????????????????????????????????????????????????????????????????????????????????????
//        http.addFilterBefore(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests()
//                .antMatchers("/captcha.jpg/**").permitAll()// ?????????//??????security???????????????
//                .anyRequest().authenticated()//????????????????????????
//                .and().formLogin()
//                .successHandler(new AuthenticationSuccessHandler() {
//
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                                        Authentication authentication) throws IOException, ServletException {
//                        logger.info("{}=={}=={}", UserIPUtil.getCurrentAdminName(), new Date(), UserIPUtil.getIPAddress(request));
//                        response.setContentType("application/json;charset=UTF-8");
//                        PrintWriter printWriter = response.getWriter();
//                        UserBean admin = (UserBean) authentication.getPrincipal();
//                        admin.setPassword(null);//?????????password??????
//
//                        HTTPResponse httpResponse = HTTPResponse.ok("????????????!",admin);
//                        String s = new ObjectMapper().writeValueAsString(httpResponse);
//                        printWriter.write(s);
//                        printWriter.flush();
//                        printWriter.close();
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//                                                        AuthenticationException exception) throws IOException, ServletException {
//                        logger.info("?????????????????????");
//                        response.setContentType("application/json;charset=UTF-8");
//                        PrintWriter printWriter = response.getWriter();
//                        HTTPResponse httpResponse = HTTPResponse.error("????????????", "");
//                        if(exception instanceof LockedException) {
//                            httpResponse.setMessage("???????????????!??????????????????");
//                        }else if(exception instanceof AccountExpiredException) {
//                            httpResponse.setMessage("???????????????!??????????????????");
//                        }else if(exception instanceof DisabledException) {
//                            httpResponse.setMessage("???????????????!??????????????????");
//                        }else if(exception instanceof CredentialsExpiredException) {
//                            httpResponse.setMessage("???????????????!??????????????????");
//                        }else if(exception instanceof BadCredentialsException) {
//                            httpResponse.setMessage("????????????????????????!???????????????");
//                        }
//                        printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
//                        printWriter.flush();
//                        printWriter.close();
//                    }
//                })
//                .permitAll()
//                .and()
//                .logout().logoutSuccessHandler(new LogoutSuccessHandler() {
//
//            @Override
//            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//                    throws IOException, ServletException {
//                logger.info("{}???????????????",new Date());
//                response.setContentType("application/json;charset=UTF-8");
//                PrintWriter printWriter = response.getWriter();
//
//                HTTPResponse httpResponse = HTTPResponse.ok("????????????!",null);
//                String s = new ObjectMapper().writeValueAsString(httpResponse);
//                printWriter.write(s);
//                printWriter.flush();
//                printWriter.close();
//            }
//        }).permitAll()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
//
//            @Override
//            public void commence(HttpServletRequest request, HttpServletResponse response,
//                                 AuthenticationException authException) throws IOException, ServletException {
//                response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(401);
//                PrintWriter printWriter = response.getWriter();
//                HTTPResponse httpResponse = HTTPResponse.error("????????????!","");
//                if(authException instanceof InsufficientAuthenticationException) {
//                    httpResponse.setMessage("???????????????????????????????????????,???????????????????????????!");
//                }
//                printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
//                printWriter.flush();
//                printWriter.close();
//            }
//        })
//                .and().csrf().disable()
//                .cors().disable();
//
//        http.sessionManagement().invalidSessionStrategy(new InvalidSessionStrategy() {
//
//            @Override
//            public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
//                    throws IOException, ServletException {
//                logger.info("{}???????????????",new Date());
//                response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
//                PrintWriter printWriter = response.getWriter();
//
//                HTTPResponse httpResponse = HTTPResponse.error("????????????!",null);
//                String s = new ObjectMapper().writeValueAsString(httpResponse);
//                printWriter.write(s);
//                printWriter.flush();
//                printWriter.close();
//            }
//        });
//    }
//}
//
