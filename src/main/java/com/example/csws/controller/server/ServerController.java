package com.example.csws.controller.server;

import com.example.csws.entity.server.Server;
import com.example.csws.entity.server.ServerDto;
import com.example.csws.entity.server.ServerRegisterRequest;
import com.example.csws.entity.server.ServerResourceResponse;
import com.example.csws.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servers")
public class ServerController {
    private final ServerService serverService;

    @PostMapping("/register/new")
    public void registerServer(@RequestBody ServerRegisterRequest serverRegisterRequest) {
        System.out.println("Server Controller 시작");
        ServerDto serverDto = serverRegisterRequest.toServerDto();
        System.out.println("ServerDto 생성됨");
        serverService.registerServer(serverDto);
    }

    @GetMapping("/register/installing")
    public void getServerizeFile(HttpServletResponse response) {
        String filePath = "/Users/donghyeonkang/Desktop/project/csws/src/main/java/com/example/csws/Automation.tar.gz";
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

    @GetMapping("/management/list")
    public List<Server> getServerList(HttpServletRequest req) {
        return serverService.getServerList(Integer.parseInt(req.getParameter("departmentId")));
    }
    @GetMapping("/management/resources")
    public ServerResourceResponse getServerResource(HttpServletRequest req) {
        return serverService.getServerResource(Integer.parseInt(req.getParameter("serverId")));
    }
}
