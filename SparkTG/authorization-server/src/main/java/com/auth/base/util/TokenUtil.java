package com.auth.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

	public String getToken(HttpServletRequest req, HttpServletResponse resp, String tokenType) {
		String token = null;
		try {
			Cookie[] cookie = req.getCookies();
			for(int i=0;i<cookie.length;i++) {
				Cookie c = cookie[i];
				if(tokenType.equals(c.getName())) {
					token = c.getValue();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public void setCookie(HttpServletRequest req, HttpServletResponse resp, String tokenType, String value) {
		boolean isExists = false;
		try {
			Cookie[] cookie = req.getCookies();
			for(int i=0;i<cookie.length;i++) {
				Cookie c = cookie[i];
				if(tokenType.equals(c.getName())) {
					c.setValue(value);
					resp.addCookie(c);
					isExists = true;
				}
			}
			if(!isExists) {
				final Cookie newCookie = new Cookie(tokenType, value);
				newCookie.setPath("/sparktg-test-app");
				resp.addCookie(newCookie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
