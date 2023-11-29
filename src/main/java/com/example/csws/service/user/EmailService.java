package com.example.csws.service.user;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.mail.Mail;
import com.example.csws.entity.mail.MailDto;
import com.example.csws.repository.mail.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@SessionAttributes("AuthNumSessionDto")
public class EmailService {

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    private final MailRepository mailRepository;

    //랜덤 인증 코드 생성
    private String authNum; //랜덤 인증 코드

    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }
    //메일 양식 작성

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        System.out.println("createEmailForm 진입");
        String setFrom = "cswebservice@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String title = "CSWS 회원가입 인증 번호"; //제목
        createCode(); // 인증번호 생성
        MimeMessage message = emailSender.createMimeMessage();

        System.out.println(email);
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText("이메일 인증 코드 " + this.authNum);
        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        System.out.println("sendEmail 진입");
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);
        return authNum; //인증 코드 반환
    }

    public void saveAuthNum(String email, String authNum) {
        System.out.println("SaveAuthNum 진입");

        // mail 객체 생성 후 저장
        MailDto mailDto = MailDto.builder()
                .email(email)
                .authNum(authNum)
                .build();

        mailRepository.save(mailDto.toEntity());
    }

    public boolean checkAuthNum(String email, String authNum) {
        System.out.println("checknum 진입");
        Mail mail = mailRepository.findByEmail(email);
        System.out.println(authNum);
        System.out.println(mail.getAuthNum());

        if(mail == null) {
            throw new EntityNotFoundException(ErrorCode.USER_NOT_FOUND);
        } else {
            if (authNum.equals(mail.getAuthNum())) {
                mailRepository.delete(mail);
                return true;
            }
        }

        return false;
    }
}
