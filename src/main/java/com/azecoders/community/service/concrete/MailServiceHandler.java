package com.azecoders.community.service.concrete;

import com.azecoders.community.service.abstraction.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceHandler implements MailService {
    private final JavaMailSender mailSender;
    @Override
    public void sendVertificationCode(String to, String code,String firstname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Azecoders.com - Hesabınız üçün Təsdiqləmə Kodu");
        message.setText(
                "Salam " + firstname + "\n\n" +
                        "Azecoders.com platformasında qeydiyyatınız üçün 6 rəqəmli təsdiqləmə kodunuz:\n\n" +
                        "👉 " + code + " 👈\n\n" +
                        "!!! 15 DƏQİQƏ ƏRZİNDƏ TƏSDİQLƏMƏLİSİNİZ, ƏKS HALDA YENİDƏN QEYDİYYATDAN KEÇMƏLİ OLACAQSINIZ !!!\n\n" +
                        "Hesabınızı aktivləşdirmək üçün kodu vaxtında daxil etməyi unutmayın.\n\n" +
                        "Təşəkkürlər!\n" +
                        "Azecoders komandası"
        );

        try {
            mailSender.send(message);
        } catch (Exception e) {
          throw new MailSendException("Failed to send verification code",e);
        }

    }

    @Override
    public void sendAccountConfirmedMessage(String to, String firstname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Azecoders.com - Hesabınız Təsdiqləndi!");
        message.setText(
                "Salam, " + firstname + "!\n\n" +
                        "Təbriklər! Hesabınız uğurla təsdiqləndi ✅\n\n" +
                        "Artıq Azecoders.com platformasından tam şəkildə istifadə edə bilərsiniz.\n\n" +
                        "Uğurlar diləyirik!\n" +
                        "Azecoders komandası"
        );

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("Failed to send account confirmation message", e);
        }
    }
}
