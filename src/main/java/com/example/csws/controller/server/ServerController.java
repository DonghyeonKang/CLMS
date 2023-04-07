package com.example.csws.controller.server;

import com.example.csws.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servers")
public class ServerController {
    RegisterService registerService;

    @GetMapping("/register/installing")
    public void getServerizeFile(HttpServletResponse response) {
        String filePath = "/Users/donghyeonkang/Desktop/project/csws/src/main/java/com/example/csws/download.tar.gz";
        response.setContentType("application/gzip");
        response.setHeader("Content-Disposition", "attachment; filename==\"Automation.tar.gz\"");

        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ServletOutputStream so = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(so);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
