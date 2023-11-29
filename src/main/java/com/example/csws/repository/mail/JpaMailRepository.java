package com.example.csws.repository.mail;

import com.example.csws.entity.mail.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMailRepository extends JpaRepository<Mail, Integer>, MailRepository {
}
