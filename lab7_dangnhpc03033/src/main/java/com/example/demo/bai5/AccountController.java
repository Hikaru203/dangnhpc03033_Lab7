package com.example.demo.bai5;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dao.AccountDAO;
import com.example.demo.Model.Account;
import com.example.demo.service.SessionService;



@Controller
public class AccountController {
	@Autowired
	AccountDAO dao;
	@Autowired
	SessionService session;

	@GetMapping("/account/login")
	public String login() {
		return "account/login";
	}

	@ResponseBody
	@GetMapping("/account/edit")
	public String edit() {
		return "edit";
	}

	@ResponseBody
	@GetMapping("/admin/index")
	public String admin() {
		return "admin";
	}

	@PostMapping("/account/login")
	public String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		try {
			Account account = dao.findAccount(username);
			if (!account.getPassword().equals(password)) {
				model.addAttribute("message", "Mật khẩu không đúng");
			} else {
				session.set("userSession", account);
				model.addAttribute("message", "Đăng nhập thành công");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Tên tài khoản không đúng");
		}
		return "account/login";
	}
}
