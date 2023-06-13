package com.example.csws.repository.mail;

import com.example.csws.entity.mail.Mail;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository {
    public Mail findByEmail(String email);

    public Mail save(Mail mail);
    public void delete(Mail mail);
}
