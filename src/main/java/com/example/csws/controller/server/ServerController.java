package com.example.csws.controller.server;

import com.example.csws.entity.server.*;
import com.example.csws.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController // rest api 를 다루기 위함
@RequiredArgsConstructor  // final 로 선언된 필드만 생성자 주입 방식으로 DI 구현
@RequestMapping("/servers") // endpoint mapping
public class ServerController {
    private final ServerService serverService;

    // 서버 등록
    @PostMapping("/register/new")
    public ServerDto registerServer(@RequestBody ServerRegisterRequest serverRegisterRequest) {
        System.out.println("Server Controller 시작");
        ServerDto serverDto = serverRegisterRequest.toServerDto();
        System.out.println("ServerDto 생성됨");
        ServerDto result = serverService.registerServer(serverDto);
        return result;
    }

    // 서버 등록 자동화 파일 다운로드
    // wget http://clms.kro.kr/servers/register/clmsPackage.tar 로 다운로드 할 수 있도록 함
    @GetMapping("/register/clmsPackage.tar")
    public void getServerizeFile(HttpServletResponse response) throws IOException {
        ClassPathResource resource = new ClassPathResource("clmsPackage.tar");
        File file = resource.getFile();

        if (file.exists()) {
            response.setContentType("application/x-tar");
            response.setHeader("Content-Disposition", "attachment; filename=\"clmsPackage.tar\"");
            try {
                 FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ServletOutputStream so = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(so);

                int data;
                while ((data = bis.read()) != -1) {
                    bos.write(data);
                }

                bos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 파일이 존재하지 않을 때 처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // 서버 리스트 조회
    @GetMapping("/management/list")
    public JSONObject getServerList(@RequestParam(value = "departmentId") Long departmentId) {
        List<ServerListResponse> serverList =  serverService.getServerList(departmentId);

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