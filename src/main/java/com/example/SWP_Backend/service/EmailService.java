package com.example.SWP_Backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service chuyên gửi email xác thực tài khoản & mã OTP (đăng ký, đặt lại mật khẩu).
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Gửi email xác thực tài khoản bằng liên kết.
     */
    public void sendVerificationEmail(String toEmail, String fullName, String token) {
        String subject = "Xác nhận đăng ký tài khoản";
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;

        String content = "<p>Kính gửi <strong>" + fullName + "</strong>,</p>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi.</p>"
                + "<p>Vui lòng nhấn vào liên kết bên dưới để xác minh địa chỉ email và kích hoạt tài khoản:</p>"
                + "<p><a href=\"" + verificationUrl + "\">Xác minh tài khoản</a></p>"
                + "<br><p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>"
                + "<p>Trân trọng,<br>Đội ngũ hỗ trợ khách hàng</p>";

        sendHtmlEmail(toEmail, subject, content);
    }

    /**
     * Gửi mã OTP xác thực đăng ký tài khoản.
     */
    public void sendOtpRegister(String toEmail, String otp) {
        String subject = "Mã xác thực (OTP) đăng ký tài khoản";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn vừa đăng ký tài khoản trên hệ thống.</p>"
                + "<p><strong>Mã OTP của bạn là: <span style='color:blue; font-size: 18px;'>" + otp + "</span></strong></p>"
                + "<p>Mã có hiệu lực trong vòng <strong>10 phút</strong>.</p>"
                + "<p>Nếu bạn không yêu cầu đăng ký tài khoản, vui lòng bỏ qua email này.</p>"
                + "<p>Trân trọng,<br>Đội ngũ chăm sóc khách hàng</p>";

        sendHtmlEmail(toEmail, subject, content);
    }

    /**
     * Gửi mã OTP xác thực đặt lại mật khẩu.
     */
    public void sendOtpResetPassword(String toEmail, String otp) {
        String subject = "Mã xác thực (OTP) đặt lại mật khẩu";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản của mình.</p>"
                + "<p><strong>Mã OTP của bạn là: <span style='color:blue; font-size: 18px;'>" + otp + "</span></strong></p>"
                + "<p>Mã có hiệu lực trong vòng <strong>10 phút</strong>.</p>"
                + "<p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>"
                + "<p>Trân trọng,<br>Đội ngũ chăm sóc khách hàng</p>";

        sendHtmlEmail(toEmail, subject, content);
    }

    /**
     * Hàm chung để gửi email HTML.
     */
    private void sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage(), e);
        }
    }
}
