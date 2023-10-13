package com.example.csws.controller.instance;

import com.example.csws.common.shRunner.ShRunner;
import com.example.csws.config.auth.PrincipalDetails;
import com.example.csws.common.shRunner.ParserResponseDto;
import com.example.csws.entity.lecture.LectureDto;
import com.example.csws.entity.server.ServerDto;
import com.example.csws.service.lecture.LectureService;
import com.example.csws.service.server.ServerService;
import org.json.simple.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;
import com.example.csws.entity.domain.DomainDto;
import com.example.csws.entity.instance.*;
import com.example.csws.entity.user.User;
import com.example.csws.service.boundPolicy.InboundPolicyService;
import com.example.csws.service.domain.DomainService;
import com.example.csws.service.instance.InstanceService;
import com.example.csws.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class InstanceController {
    private final UserService userService;
    private final InstanceService instanceService;
    private final DomainService domainService;
    private final InboundPolicyService inboundPolicyService;
    private final ServerService serverService;
    private final LectureService lectureService;
    private final ShRunner shRunner;

    // 인스턴스 시작
    @PostMapping("/start")
    public JSONObject startInstance(@RequestBody ControlInstanceRequest request, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String result = instanceService.startInstance(request.getInstanceId(), principalDetails.getUsername());
        JSONObject obj = new JSONObject();
        if (result.equals("success")) {
            obj.put("success", true);
        } else {
            obj.put("success", false);
        }
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "running");

        return obj;
    }

    // 인스턴스 재시작
    @PostMapping("/restart")
    public JSONObject restartInstance(@RequestBody ControlInstanceRequest request, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String result = instanceService.restartInstance(request.getInstanceId(), principalDetails.getUsername());

        // 쉘 스크립트 실행
        shRunner.execCommand("H_RestartContainer.sh");

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "restarting");

        return obj;
    }

    // 인스턴스 정지
    @PostMapping("/stop")
    public JSONObject stopInstance(@RequestBody ControlInstanceRequest request, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String result = instanceService.stopInstance(request.getInstanceId(), principalDetails.getUsername());

        // 쉘 스크립트 실행
        shRunner.execCommand("H_StopContainer.sh");

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "stopped");

        return obj;

    }

    // 인스턴스 삭제
    @PostMapping("/delete")
    public JSONObject deleteInstance(@RequestBody ControlInstanceRequest request, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String result = instanceService.deleteInstance(request.getInstanceId(), principalDetails.getUsername());

        // 쉘 스크립트 실행
        shRunner.execCommand("H_StopContainer.sh");

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "deleted");

        return obj;
    }

    // 인스턴스 생성 후 인스턴스 목록으로 이동. 실패(오류 발생) 시 생성 페이지로 돌아가기.
    @PostMapping("/creation")
    public String createInstance(@RequestBody CreateInstanceRequest request, Authentication authentication) {
        // 로그인된 사용자의 userId 추가
        InstanceDto newDto = new InstanceDto();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        newDto.setUserId(principalDetails.getId());
        // request 의 데이터 dto 에 넣기
        newDto.setName(request.getName());
        newDto.setStorage(Double.parseDouble(request.getStorage().substring(0, request.getStorage().length() - 1)));
        newDto.setKeyName(request.getKeyPair());
        newDto.setOs(request.getOs());
        newDto.setLectureId(request.getLectureId());

        // dto default 값 추가
        newDto.setCode(1); // 필요없으면 향후 삭제 예정.
        newDto.setState("running");
        Timestamp curTimestamp = Timestamp.valueOf(LocalDateTime.now()); // 현재 시간 저장(LocalDateTime을 mySQL에서 호환되도록 Timestamp로 형변환)
        newDto.setCreated(curTimestamp);

        // 요청에 따라 쉘 스크립트 실행
        String result = instanceService.createInstance(newDto, principalDetails.getUsername());

        if (result.equals("success")) { // 성공적으로 db에 insert 및 쉘 스크립트 실행
            return "success";
        } else {
            return "failed";
        }
    }

    // 키페어 생성
    @PostMapping("/keypair")
    public ResponseEntity<Resource> createKeypair(@RequestBody Map<String, String> testName) throws IOException {
        // 키페어 생성
        instanceService.createKeyPair(testName.get("hostname"), testName.get("name"));

        // 파일 경로 지정 (여기서는 resources 디렉토리에 있는 "example.txt" 파일 사용)
        String fileName = testName.get("name") + ".pem";
        Resource resource = new FileSystemResource("/Users/donghyeonkang/Keys/" + testName.get("hostname") + "/" + fileName);

        // 다운로드할 파일의 MIME 타입 설정
        String mimeType = Files.probeContentType(resource.getFile().toPath());

        // 파일 다운로드를 위한 HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=a.pem");
        headers.setContentType(MediaType.parseMediaType(mimeType));

        // 파일을 ResponseEntity에 포함하여 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/detail")
    public InstanceDto instanceDetail(@RequestParam Integer instanceId) {
        InstanceDto instanceDto = instanceService.findById(instanceId).get();
        return instanceDto;
    }

    // 강의 내 인스턴스 목록 조회
    @GetMapping("/list")
    public JSONObject listByLectureId(@RequestParam(value = "lectureId", required = false) Integer lectureId) {

        List<InstanceListResponse> newList = new ArrayList<>(); // 반환할 리스트

        List<InstanceDto> list = instanceService.findAllByLectureId(lectureId.longValue());
        for (InstanceDto dto : list) {      // 조회 페이지에 띄울 내용만 새 dto 리스트에 담기
            InstanceListResponse newDto = new InstanceListResponse();
            newDto.setInstanceId(dto.getInstanceId());
            newDto.setName(dto.getName());
            newDto.setUserName("dong");
            newList.add(newDto);
        }
        System.out.println(newList);
        JSONObject obj = new JSONObject();
        obj.put("instances", newList);
        return obj;
    }

    // 특정 인스턴스의 도메인 조회(instanceId)
    @GetMapping("/domain")
    public JSONObject getDomainName(@RequestParam Integer instanceId) {

        return domainService.findByInstanceId(instanceId);
    }

    // 특정 인스턴스의 도메인 저장(추가)
    @PostMapping("/domain")
    public JSONObject domainCreate(@RequestBody DomainInstanceRequest domainInstanceRequest) {
        System.out.println(domainInstanceRequest.getDomainName());
        System.out.println(domainInstanceRequest.getInstanceId());

        // 새로운 도메인 추가
        CreateDomainDto createDomainDto = domainService.createDomain(
                new DomainDto(domainInstanceRequest.getDomainName(), domainInstanceRequest.getInstanceId()));

        // 저장된 도메인 명 응답
        JSONObject obj = new JSONObject();
        obj.put("success", createDomainDto.getSuccess());
        obj.put("domainName", createDomainDto.getDomainName());
        return obj;
    }

    // 특정 인스턴스의 도메인 삭제
    @PostMapping("/domain/remove")
    public JSONObject domainDelete(@RequestBody(required = false) DomainInstanceRequest domainInstanceRequest) {
        System.out.println(domainInstanceRequest.getInstanceId());
        domainService.deleteDomain(
                DomainDto.builder()
                        .instanceId(domainInstanceRequest.getInstanceId())
                        .build());

        // 저장된 도메인 명 응답
        JSONObject obj = new JSONObject();
        obj.put("success", true);
        return obj;
    }

    // 특정 인스턴스의 인바운드 리스트 조회
    @GetMapping("/inbounds/list")
    public JSONObject inboundList(@RequestParam(required = false) Integer instanceId) {
        // 인바운드 정책 조회
        List<InboundPolicyDto> dtoList = inboundPolicyService.findAllByInstanceId(instanceId);

        // json 화
        JSONObject obj = new JSONObject();
        obj.put("inbounds", dtoList);
        return obj;
    }

    @PutMapping("/inbounds/setting")
    public JSONObject inboundSetting(@RequestBody List<InboundPolicyDto> inbounds) {
        // request 값 출력
        System.out.println("inbounds setting 진입");
        for (InboundPolicyDto inbound : inbounds) {
            System.out.println(inbound.getId());
            System.out.println(inbound.getHostPort());
            System.out.println(inbound.getInstancePort());
        }

        // 저장
        List<InboundPolicyDto> savedList = inboundPolicyService.saveAll(inbounds);

        // json 화
        JSONObject obj = new JSONObject();
        obj.put("inbounds", savedList);
        return obj;
    }


    // 자원 사용량 조회 - 컨테이너(학생)
    @GetMapping("/resource/container")
    public ParserResponseDto checkContainerResource(Authentication authentication, @RequestParam Integer instanceId) {
        // 서버 정보를 얻기 위해 인스턴스 엔티티의 lecture 가져오기
        InstanceDto instanceDto = instanceService.findById(instanceId).get();

        // 서버 id로 서버 정보 가져오기
        LectureDto lectureDto = lectureService.findById(instanceId.longValue());
        ServerDto serverDto = serverService.findById(lectureDto.getServerId());

        // 유저(학생 본인) 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 호스트 계정 이름, 호스트 ip, 컨테이너 이름(유저 이름 + 인스턴스 id)
        ParserResponseDto parserResponseDto = instanceService.checkContainerResource(serverDto.getServerUsername(), serverDto.getIpv4(),
                principalDetails.getUsername() + instanceDto.getInstanceId());

        // dto 반환
        return parserResponseDto;
    }

    // 자원 사용량 조회 - 서버(관리자)
    @GetMapping("/resource/server")
    public ParserResponseDto checkServerResource(@RequestParam Long serverId) {

        // 서버 id로 서버 정보 가져오기
        ServerDto serverDto = serverService.findById(serverId);

        // 호스트 계정 이름, 호스트 ip
        ParserResponseDto parserResponseDto = instanceService.checkServerResource(serverDto.getServerUsername(),
                serverDto.getIpv4());

        // dto 반환
        return parserResponseDto;
    }

    // 강의 내 본인 인스턴스 id 조회
    @GetMapping("/id")
    public JSONObject findMyInstanceId(@RequestParam Long lectureId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        int myInstanceId = instanceService.findMyInstanceId(principalDetails.getId(), lectureId);
        JSONObject obj = new JSONObject();
        obj.put("instanceId", myInstanceId);
        return obj;
    }
}