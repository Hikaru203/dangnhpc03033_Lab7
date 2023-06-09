package com.example.demo.controller.b1;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ParamService;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

@Controller
public class MailerController {

	@Autowired
	private MailerService mailerService;

	@GetMapping("send-mail")
	String sendMail(Model model) {
		model.addAttribute("mailInfo", new MailInfo());
		return "Mail/SendMail";
	}

	@PostMapping("send-mail")
	public String sendEmail(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam(value = "cc", required = false) String cc,
			@RequestParam(value = "bcc", required = false) String bcc, @RequestParam("subject") String subject,
			@RequestParam("body") String body,
			@RequestParam(value = "attachments", required = false) MultipartFile[] attachments, Model model) {
		System.out.println(from);
		System.out.println(to);
		System.out.println(cc);
		System.out.println(bcc);
		System.out.println(subject);
		System.out.println(body);
		System.out.println(attachments);

		try {
			MailInfo mailInfo = new MailInfo();
			mailInfo.setFrom(from);
			mailInfo.setTo(to);

			// Kiểm tra cc
			if (cc != null && !cc.isEmpty()) {
				try {
					InternetAddress.parse(cc, true);
					mailInfo.setCc(cc);
				} catch (AddressException e) {
					// Xử lý lỗi khi địa chỉ cc không hợp lệ
				}
			}

			// Kiểm tra bcc
			if (bcc != null && !bcc.isEmpty()) {
				try {
					InternetAddress.parse(bcc, true);
					mailInfo.setBcc(bcc);
				} catch (AddressException e) {
					// Xử lý lỗi khi địa chỉ bcc không hợp lệ
				}
			}

			mailInfo.setSubject(subject);
			mailInfo.setBody(body);
			System.out.println(attachments.length);

			if (attachments != null && attachments.length > 0) {
				String[] attachmentPaths = new String[attachments.length];
				for (int i = 0; i < attachments.length; i++) {
					MultipartFile attachment = attachments[i];
					String filename = attachment.getOriginalFilename();
					String filePath = "/path/send/attachments/" + filename;

					File savedFile = ParamService.save(attachment, filePath); // Sử dụng ParamService để lưu file đính
																				// kèm

					if (savedFile != null) {
						attachmentPaths[i] = savedFile.getAbsolutePath();
					} else {
						// Xử lý lỗi không thể lưu file
					}
				}
				mailInfo.setAttachments(attachmentPaths);
			}

			mailerService.send(mailInfo);

			model.addAttribute("message", "Email sent successfully!");
			System.out.println("Email sent successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while sending the email: " + e.getMessage());
			System.out.println(e);
		}

		return "redirect:/send-mail";
	}
}
