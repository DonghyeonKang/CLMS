package com.example.csws.controller.instance;

import com.example.csws.common.shRunner.ParserResponseDto;
import com.example.csws.common.shRunner.ShParser;
import com.example.csws.config.auth.PrincipalDetails;
import com.example.csws.entity.server.ServerDto;
import com.example.csws.service.server.ServerService;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
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

import java.io.BufferedReader;
import java.io.FileReader;
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

    // 인스턴스 시작
    @PostMapping("/start")
    public JSONObject startInstance(@RequestBody ControlInstanceRequest request) {
        Boolean result = instanceService.startInstance(request.getInstanceId());

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "running");

        return obj;
    }

    // 인스턴스 재시작
    @PostMapping("/restart")
    public JSONObject restartInstance(@RequestBody ControlInstanceRequest request) {
        Boolean result = instanceService.restartInstance(request.getInstanceId());

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "restarting");

        return obj;
    }

    // 인스턴스 정지
    @PostMapping("/stop")
    public JSONObject stopInstance(@RequestBody ControlInstanceRequest request) {
        Boolean result = instanceService.stopInstance(request.getInstanceId());

        JSONObject obj = new JSONObject();
        obj.put("success", result);
        obj.put("instanceId", request.getInstanceId());
        obj.put("status", "stopped");

        return obj;

    }

    // 인스턴스 삭제
    @PostMapping("/delete")
    public JSONObject deleteInstance(@RequestBody ControlInstanceRequest request) {
        Boolean result = instanceService.deleteInstance(request.getInstanceId());

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
        System.out.println(principalDetails.getId());

        // request 의 데이터 dto 에 넣기
        newDto.setName(request.getName());
        newDto.setStorage(Double.parseDouble(request.getStorage().substring(0, request.getStorage().length() - 1)));
        newDto.setKeyName(request.getKeyName());
        newDto.setOs(request.getOs());
        newDto.setServerId(request.getServerId());
        newDto.setAddress(request.getAddress());

        // dto default 값 추가
        newDto.setCode(1); // 필요없으면 향후 삭제 예정.
        newDto.setState("running");
        Timestamp curTimestamp = Timestamp.valueOf(LocalDateTime.now()); // 현재 시간 저장(LocalDateTime을 mySQL에서 호환되도록 Timestamp로 형변환)
        newDto.setCreated(curTimestamp);

        // 요청에 따라 쉘 스크립트 실행
        String result = instanceService.createInstance(newDto);
        if (result.equals("success")) { // 성공적으로 db에 insert 및 쉘 스크립트 실행
            return "redirect:/instances/list/user";
        } else {
            return "redirect:/instances/creation";
        }
    }

    // 키페어 생성
    @PostMapping("/keypair")
    public ResponseEntity<Resource> createKeypair(@RequestBody Map<String, String> testName) throws IOException {
        System.out.println("keypair 생성 진입");

        // 키페어 생성
        instanceService.createKeyPair(testName.get("hostName"), testName.get("keyName"));


        // 파일 경로 지정 (여기서는 resources 디렉토리에 있는 "example.txt" 파일 사용)
        Resource resource = new ClassPathResource("a.pem");

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

    // 본인 혹은 타인(관리자 권한)의 인스턴스 목록 조회(userId)
    @GetMapping("/list/user")  // 본인의 목록을 조회하면 userId가 null로 넘어온다. null을 허용하기 위한 어노테이션.
    public JSONObject listByUserId(Authentication authentication) {
        // 로그인된 사용자 객체
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // userId로 인스턴스 리스트 받아오기.
        List<InstanceDto> list = instanceService.findAllByUserId(principalDetails.getId()); // 중복 코드는 따로 메서드로 만들어도 좋음.
        List<InstanceDto> newList = new ArrayList<>(); // 반환할 리스트

        for (InstanceDto dto : list) {      // 조회 페이지에 띄울 내용만 새 dto 리스트에 담기
            InstanceDto newDto = new InstanceDto();
            newDto.setInstanceId(dto.getInstanceId());
            newDto.setName(dto.getName());
            newDto.setState(dto.getState());
            newDto.setAddress(dto.getAddress());
            newDto.setPort(dto.getPort());
            newDto.setOs(dto.getOs());
            newDto.setKeyName(dto.getKeyName());
            newDto.setStorage(dto.getStorage());
            newList.add(newDto);
        }

        JSONObject obj = new JSONObject();
        obj.put("instances", newList);
        return obj;
    }

    // 특정 서버 소속의 인스턴스 목록 조회(serverId)
    @GetMapping("/list/server")
    public JSONObject listByServerId(@RequestParam(value = "serverId", required = false) Integer serverId) {

        List<InstanceDto> newList = new ArrayList<>(); // 반환할 리스트

        List<InstanceDto> list = instanceService.findAllByServerId(serverId);
        for (InstanceDto dto : list) {      // 조회 페이지에 띄울 내용만 새 dto 리스트에 담기
            InstanceDto newDto = new InstanceDto();
            newDto.setInstanceId(dto.getInstanceId());
            newDto.setName(dto.getName());
            newDto.setState(dto.getState());
            newDto.setAddress(dto.getAddress());
            newDto.setPort(dto.getPort());
            newDto.setOs(dto.getOs());
            newList.add(newDto);
        }

        JSONObject obj = new JSONObject();
        obj.put("instances", newList);
        return obj;
    }

    // 소유자 변경 페이지 이동.
    @GetMapping("/owner")
    public void owner(@RequestParam(value = "instanceId", required = false) Integer instanceId, Model model) {  // 변경하려는 인스턴스 상세 정보 반환하기.

        // 조회할 인스턴스 id가 프론트에서 넘어오지 않았을 경우 에러
        if (instanceId == null) {
            System.out.println("null owner");
        }
        System.out.println(instanceId + " owner");

        Optional<InstanceDto> dto = instanceService.findById(instanceId);

        if (dto.isEmpty()) {    // DB 조회 객체가 null일 경우, db에 인스턴스 정보 없음 에러
        }

        model.addAttribute("instance", dto.get());
    }

    // 소유자 변경 로직.
    @PatchMapping("/owner")
    public String changeOwner(@RequestBody OwnerInstanceRequest ownerInstanceRequest, Model model) {

        // instanceId, 소유권 변경할 학생의 username(email) 프론트에서 넘겨받기.
        Optional<InstanceDto> dto = instanceService.findById(ownerInstanceRequest.getInstanceId());

        // 반환 객체가 null일 경우 db에 인스턴스 정보 없음 에러 띄우기
        if (dto.isEmpty()) {
            return "/";
        }
        InstanceDto newDto = dto.get();

        // 새로운 user의 email로 user 엔티티 받아온 뒤, userId 설정
        User student = userService.getUser(ownerInstanceRequest.getUsername());
        Long newUserId = student.getId();
        newDto.setUserId(newUserId);

        instanceService.createInstance(newDto); // 기존에 존재하던 객체를 업데이트

        return "redirect:listUserid?userId=" + newUserId;   // 해당 학생의 userId로 인스턴스 리스트 조회하는 페이지 이동
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

        // 서버 정보를 얻기 위해 인스턴스 엔티티의 serverId 가져오기
        InstanceDto instanceDto = instanceService.findById(instanceId).get();
        // 서버 id로 서버 정보 가져오기
        ServerDto serverDto = serverService.findById(instanceDto.getServerId());
        // 유저(학생 본인) 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 호스트 계정 이름, 호스트 ip, 컨테이너 이름(유저 이름 + 인스턴스 id)
        ParserResponseDto parserResponseDto = instanceService.checkContainerResource(serverDto.getServerUsername(),
                        serverDto.getIpv4(), principalDetails.getUsername() + instanceDto.getInstanceId());

        // dto 반환
        return parserResponseDto;
    }

    // 자원 사용량 조회 - 서버(관리자)
    @GetMapping("/resource/server")
    public ParserResponseDto checkServerResource(@RequestParam Integer serverId) {

        // 서버 id로 서버 정보 가져오기
        ServerDto serverDto = serverService.findById(serverId);

        // 호스트 계정 이름, 호스트 ip
        ParserResponseDto parserResponseDto = instanceService.checkServerResource(serverDto.getServerUsername(),
                                           serverDto.getIpv4());

        // dto 반환
        return parserResponseDto;
    }

    // 모든 컨테이너 상태 출력(관리자)
    @GetMapping("/status/container/manager")
    public ParserResponseDto printStatusforManager(@RequestParam Integer serverId) {

        // 서버 id로 서버 정보 가져오기
        ServerDto serverDto = serverService.findById(serverId);

        // 호스트 계정 이름, 호스트 ip
        ParserResponseDto parserResponseDto = instanceService.printStatusforManager(serverDto.getServerUsername(),
                                           serverDto.getIpv4());

        // dto 반환
        return parserResponseDto;
    }

    // 본인 소유 컨테이너 상태 출력(학생)
    @GetMapping("/status/container/user")
    public ParserResponseDto printStatusforUser(Authentication authentication) {

        // 유저(학생 본인) 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        // 인스턴스 목록 가져오기
        List<InstanceDto> dtoList = instanceService.findAllByUserId(principalDetails.getId());
        // 서버 id 가져오기
        int serverId = dtoList.get(0).getServerId();
        // 서버 정보 가져오기
        ServerDto serverDto = serverService.findById(serverId);
        // 호스트 계정 이름, 호스트 ip, 유저 이름
        ParserResponseDto parserResponseDto = instanceService.printStatusforUser(serverDto.getServerUsername(),
                                          serverDto.getIpv4(), principalDetails.getUsername());

        // dto 반환
        return parserResponseDto;
    }


}
