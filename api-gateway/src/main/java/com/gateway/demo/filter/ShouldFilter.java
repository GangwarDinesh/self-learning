package com.gateway.demo.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ShouldFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("Test", "TestSample");
		return null;
	}

	@Override
	public String filterType() {
		return "shouldFilter";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
