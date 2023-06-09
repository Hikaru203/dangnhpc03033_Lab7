package com.example.demo.bai5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.Model.Account;
import com.example.demo.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	SessionService session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Account user = session.get("userSession"); // lấy từ session
		if(user == null) {
			response.sendRedirect("/account/login");
			return false;
		}else if (user.isAdmin() == false) {
			response.sendRedirect("/account/login");
			return false;
		}else if (user.isAdmin()) {
			return true;
		}
		return true;
	}
}
