package com.light.graduation.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Light
 * @Date 2020/2/16 13:10
 */
@Component
public class CORSFilter implements Filter {
	private Boolean isCross = false;
	
	@Override
	public void init ( @NotNull FilterConfig filterConfig ) throws ServletException {
		String isCrossStr = filterConfig.getInitParameter ( "IsCross" );
		isCross = "true".equals ( isCrossStr );
		System.out.println ( isCrossStr );
	}
	
	@Override
	public void doFilter ( ServletRequest servletRequest , ServletResponse servletResponse , FilterChain filterChain ) throws IOException, ServletException {
		if ( isCross ) {
			HttpServletRequest httpServletRequest = ( HttpServletRequest ) servletRequest;
			HttpServletResponse httpServletResponse = ( HttpServletResponse ) servletResponse;
			System.out.println ( "拦截请求" + httpServletRequest.getServletPath ( ) );
			httpServletResponse.setHeader ( "Access-Control-Allow-Origin" , httpServletRequest.getHeader("Origin") );
			httpServletResponse.setHeader ( "Access-Control-Allow-Methods" , "POST,GET,OPTIONS,DELETE,PUT" );
			httpServletResponse.setHeader("Access-Control-Max-Age", "0");
			httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
			httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpServletResponse.setHeader("XDomainRequestAllowed", "1");
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	@Override
	public void destroy ( ) {
		isCross = false;
	}
}
