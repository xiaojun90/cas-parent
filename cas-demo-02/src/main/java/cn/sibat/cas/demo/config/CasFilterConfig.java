package cn.sibat.cas.demo.config;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CAS 过滤器
 * @author: xiaojun
 * @version: 2018/11/22
 */
@Configuration
public class CasFilterConfig {

    /**
     * 登出监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(new SingleSignOutHttpSessionListener());
        return registrationBean;
    }

    /**
     * 登出过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new SingleSignOutFilter());
        List<String> urlPatterns = new ArrayList<>();
        filterRegistrationBean.setOrder(1); //登出过滤器的优先级要最高，否则登录会不生效。
        urlPatterns.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }

    /**
     * 认证Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new AuthenticationFilter());
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("casServerLoginUrl","http://xiaojun02.sibat.cn:8761/cas/login");
        initParameters.put("serverName", "http://xiaojun02.sibat.cn:8763");
        authenticationFilter.setInitParameters(initParameters);
        authenticationFilter.setOrder(2);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");// 设置匹配的url
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }


    /**
     * 验证票据Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter = new FilterRegistrationBean();
        cas20ProxyReceivingTicketValidationFilter.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerUrlPrefix", "http://xiaojun02.sibat.cn:8761/cas");
        initParameters.put("serverName", "http://xiaojun02.sibat.cn:8763");
        cas20ProxyReceivingTicketValidationFilter.setInitParameters(initParameters);
        cas20ProxyReceivingTicketValidationFilter.setOrder(3);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");// 设置匹配的url
        cas20ProxyReceivingTicketValidationFilter.setUrlPatterns(urlPatterns);

        return cas20ProxyReceivingTicketValidationFilter;
    }

    /**
     * 一个标准的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean casHttpServletRequestWrapperFilter(){
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new HttpServletRequestWrapperFilter());
        authenticationFilter.setOrder(3);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");// 设置匹配的url
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }

}
