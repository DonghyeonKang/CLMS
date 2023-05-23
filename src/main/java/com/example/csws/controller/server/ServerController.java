package com.example.csws.controller.server;

import com.example.csws.entity.server.*;
import com.example.csws.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
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

@RestController // rest api 를 다루기 위함
@RequiredArgsConstructor  // final 로 선언된 필드만 생성자 주입 방식으로 DI 구현
@RequestMapping("/servers") // endpoint mapping
public class ServerController {
    private final ServerService serverService;

    // 서버 등록
    @PostMapping("/register/new")
    public void registerServer(@RequestBody ServerRegisterRequest serverRegisterRequest) {
        System.out.println("Server Controller 시작");
        ServerDto serverDto = serverRegisterRequest.toServerDto();
        System.out.println("ServerDto 생성됨");
        serverService.registerServer(serverDto);
    }

    // 서버 등록 자동화 파일 다운로드
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

    // 서버 리스트 조회
    @GetMapping("/management/list")
    public JSONObject getServerList(HttpServletRequest req) {
        List<ServerListResponse> serverList =  serverService.getServerList(Integer.parseInt(req.getParameter("departmentId")));
        System.out.println(serverList);

        JSONObject obj = new JSONObject();
        obj.put("servers", serverList);

        return obj;
    }

    // 서버 리소스 조회
    @GetMapping("/management/resources")
    public ServerResourceResponse getServerResource(HttpServletRequest req) {
        return serverService.getServerResource(Integer.parseInt(req.getParameter("serverId")));
    }

    // 서버 삭제
    @DeleteMapping("")
    public void deleteServer(HttpServletRequest req) {
        serverService.deleteServer(Integer.parseInt(req.getParameter("serverId"))); // serverId 파라미터로 받아옴
    }
}
