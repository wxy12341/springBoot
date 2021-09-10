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
//        web.ignoring().antMatchers("/captcha.jpg");//不通过security过滤连放行
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //这里是在校验密码之前先校验验证码，如果验证码错误就不检验用户名和密码了
//        http.addFilterBefore(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests()
//                .antMatchers("/captcha.jpg/**").permitAll()// 验证码//通过security过滤连放行
//                .anyRequest().authenticated()//其他访问必须登录
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
//                        admin.setPassword(null);//不返回password字段
//
//                        HTTPResponse httpResponse = HTTPResponse.ok("登录成功!",admin);
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
//                        logger.info("登录失败的日志");
//                        response.setContentType("application/json;charset=UTF-8");
//                        PrintWriter printWriter = response.getWriter();
//                        HTTPResponse httpResponse = HTTPResponse.error("登录失败", "");
//                        if(exception instanceof LockedException) {
//                            httpResponse.setMessage("账户被锁定!请联系管理员");
//                        }else if(exception instanceof AccountExpiredException) {
//                            httpResponse.setMessage("账户已过期!请联系管理员");
//                        }else if(exception instanceof DisabledException) {
//                            httpResponse.setMessage("账户被禁用!请联系管理员");
//                        }else if(exception instanceof CredentialsExpiredException) {
//                            httpResponse.setMessage("密码已过期!请联系管理员");
//                        }else if(exception instanceof BadCredentialsException) {
//                            httpResponse.setMessage("用户名或密码有误!请重新输入");
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
//                logger.info("{}退出成功！",new Date());
//                response.setContentType("application/json;charset=UTF-8");
//                PrintWriter printWriter = response.getWriter();
//
//                HTTPResponse httpResponse = HTTPResponse.ok("退出成功!",null);
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
//                HTTPResponse httpResponse = HTTPResponse.error("访问失败!","");
//                if(authException instanceof InsufficientAuthenticationException) {
//                    httpResponse.setMessage("尚未登录或者登录身份已过期,请登录或者重新登录!");
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
//                logger.info("{}退出成功！",new Date());
//                response.setContentType("application/json;charset=UTF-8");
//                response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
//                PrintWriter printWriter = response.getWriter();
//
//                HTTPResponse httpResponse = HTTPResponse.error("身份过期!",null);
//                String s = new ObjectMapper().writeValueAsString(httpResponse);
//                printWriter.write(s);
//                printWriter.flush();
//                printWriter.close();
//            }
//        });
//    }
//}
//
