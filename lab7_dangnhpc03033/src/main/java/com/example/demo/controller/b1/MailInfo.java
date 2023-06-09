package com.example.demo.controller.b1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
    private String from;
    private String to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
    private String[] attachments;

    public MailInfo(String to, String subject, String body) {
        this.from = "FPT Polytechnic <poly@fpt.edu.vn>";
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public void setCc(String cc) {
        if (cc != null) {
            this.cc = new String[]{cc};
        } else {
            this.cc = null;
        }
    }

    public void setBcc(String bcc) {
        if (bcc != null) {
            this.bcc = new String[]{bcc};
        } else {
            this.bcc = null;
        }
    }
}

