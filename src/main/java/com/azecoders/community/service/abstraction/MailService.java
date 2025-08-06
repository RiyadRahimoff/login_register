package com.azecoders.community.service.abstraction;

public interface MailService {
    void sendVertificationCode(String to, String code,String firstname );

    void sendAccountConfirmedMessage(String to, String firstname);

}
