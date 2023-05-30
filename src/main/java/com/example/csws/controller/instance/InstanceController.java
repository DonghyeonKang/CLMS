package com.example.csws.controller.instance;

import com.example.csws.config.auth.PrincipalDetails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;
import com.example.csws.entity.domain.Domain;
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

    // 사용자 id 받아오기
    // 컨트롤러 인수로 Authentication authentication 받기
//    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//    principalDetails.getId();

    // 웹 뷰 파일들의 최상위 경로
    private static final String VIEWPATH = "/~~~/";

    private final UserService userService;
    private final InstanceService instanceService;
    private final DomainService domainService;
//    private final ServerService serverService;
    private final InboundPolicyService inboundPolicyService;

    // return VIEWPATH + "웹 페이지 경로" : 이동할 웹 페이지 경로를 반환(사용자에게 경로 숨김).
    // return "redirect:매핑 이름" : 해당 매핑으로 리다이렉트
    
    // 인스턴스 시작
    @PostMapping("/start")
    public String startInstance(@ModelAttribute StartInstanceRequest request) {
        String result = instanceService.startInstance(Integer.parseInt(request.getInstanceId()));
        return result;
    }

    // 인스턴스 재시작
    @PostMapping("/restart")
    public String restartInstance(@ModelAttribute StartInstanceRequest request) {
        String result = instanceService.restartInstance(Integer.parseInt(request.getInstanceId()));
        return result;
    }

    // 인스턴스 정지
    @PostMapping("/stop")
    public String stopInstance(@ModelAttribute StartInstanceRequest request) {
        String result = instanceService.stopInstance(Integer.parseInt(request.getInstanceId()));
        return result;
    }

    // 인스턴스 삭제
    @PostMapping("/delete")
    public String deleteInstance(@ModelAttribute StartInstanceRequest request) {
        String result = instanceService.deleteInstance(Integer.parseInt(request.getInstanceId()));
        return result;
    }

    // 인스턴스 생성 페이지 이동.
    @GetMapping("/creation")
    public String createForm() {
        return VIEWPATH + "생성 페이지 패키지 경로";
    }

    // 인스턴스 생성 후 인스턴스 목록으로 이동. 실패(오류 발생) 시 생성 페이지로 돌아가기.
    @PostMapping("/creation")
    public String createInstance(@RequestBody CreateInstanceRequest request, Authentication authentication) {
        InstanceDto newDto = new InstanceDto();
        System.out.println(request.getName());
        System.out.println(request.getServerId());
        System.out.println(request.getStorage());
        System.out.println(request.getOs());
        System.out.println(request.getKeyName());
        System.out.println(request.getUserId());
        System.out.println(request.getAddress());

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

        // 로그인된 사용자의 userId 추가
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        newDto.setUserId(principalDetails.getId());

        // 요청에 따라 쉘 스크립트 실행
        String result = instanceService.createInstance(newDto);
        if (result.equals("success")) { // 성공적으로 db에 insert 및 쉘 스크립트 실행
            return "redirect:/instances/listUserid";
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
    @GetMapping("/listUserId")  // 본인의 목록을 조회하면 userId가 null로 넘어온다. null을 허용하기 위한 어노테이션.
    public String listByUserId(@RequestParam(value = "userId", required = false) Integer userId, Model model) {

        // 반환할 리스트
        List<InstanceDto> newList = new ArrayList<>();

        // 프론트에서 userId가 넘어오면 관리자가 타 학생의 userId로 조회한다는 의미.
        if (userId == null) { // 프론트에서 안 넘어오면 Authentication에서 학생 본인 userId 받아오기.

            userId = null;
            // 로그인 세션이 없는 경우(비정상적인 접근)에 대한 예외 처리 추가 필요.
        }

        // userId로 인스턴스 리스트 받아오기.
        List<InstanceDto> list = instanceService.findAllByUserId(userId); // 중복 코드는 따로 메서드로 만들어도 좋음.
        for (InstanceDto dto : list) {      // 조회 페이지에 띄울 내용만 새 dto 리스트에 담기
            InstanceDto newDto = new InstanceDto();
            newDto.setId(dto.getId());
            newDto.setName(dto.getName());
            newDto.setState(dto.getState());
            newDto.setAddress(dto.getAddress());
            newDto.setPort(dto.getPort());
            newDto.setOs(dto.getOs());
            newList.add(newDto);
        }
        model.addAttribute("instanceList", newList);   // 모델에 올려 뷰로 넘기기.

        return VIEWPATH + "리스트 조회 페이지 경로";  // 뷰 페이지로 넘어가기.
    }

    // 특정 서버 소속의 인스턴스 목록 조회(serverId)
    @GetMapping("/listServerId")
    public String listByServerId(@RequestParam(value = "serverId", required = false) Integer serverId, Model model) {

        List<InstanceDto> newList = new ArrayList<>(); // 반환할 리스트

        // 서버 id를 안 넘겨줬을 경우 실패 에러 띄우기
        if (serverId == null) {
            return "/";
        }

        List<InstanceDto> list = instanceService.findAllByServerId(serverId);
        for (InstanceDto dto : list) {      // 조회 페이지에 띄울 내용만 새 dto 리스트에 담기
            InstanceDto newDto = new InstanceDto();
            newDto.setId(dto.getId());
            newDto.setName(dto.getName());
            newDto.setState(dto.getState());
            newDto.setAddress(dto.getAddress());
            newDto.setPort(dto.getPort());
            newDto.setOs(dto.getOs());
            newList.add(newDto);
        }
        model.addAttribute("instanceList", newList);

        return VIEWPATH + "리스트 조회 페이지 경로";
    }

    // 소유자 변경 페이지 이동.
    @GetMapping("/owner")
    public String owner(@RequestParam(value = "instanceId", required = false) Integer instanceId, Model model) {  // 변경하려는 인스턴스 상세 정보 반환하기.

        // 조회할 인스턴스 id가 프론트에서 넘어오지 않았을 경우 에러 띄우기
        if (instanceId == null) {
            System.out.println("null owner");
            return "/";
        }
        System.out.println(instanceId + " owner");

        Optional<InstanceDto> dto = instanceService.findById(instanceId);

        if (dto.isEmpty()) {    // DB 조회 객체가 null일 경우, db에 인스턴스 정보 없음 에러 띄우기
            return "/";
        }

        model.addAttribute("instance", dto.get());

        return VIEWPATH + "소유자 변경을 위한 페이지 경로";
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

    // 인스턴스 세부사항 조회(instanceId 이용)
    @GetMapping("/detail")
    public String detail(@RequestParam Integer instanceId, Model model) {

        InstanceDto dto = instanceService.findById(instanceId).get();
        model.addAttribute("instance", dto);

        return VIEWPATH + "세부사항 페이지 경로";
    }

    // 특정 인스턴스의 도메인 조회(instanceId)
    @GetMapping("/domain")
    public String domainList(@RequestParam Integer instanceId, Model model) {

        Domain domain = domainService.findByInstanceId(String.valueOf(instanceId));
        model.addAttribute("domain", domain);

        return VIEWPATH + "도메인 페이지 경로";
    }

    // 특정 인스턴스의 도메인 저장(추가)
    @PostMapping("/domain")
    public String domainCreate(@RequestBody DomainInstanceRequest domainInstanceRequest, Model model) {

        DomainDto newDto = new DomainDto(domainInstanceRequest.getDomainName(), domainInstanceRequest.getInstanceId());

        domainService.createDomain(newDto);
        model.addAttribute("domain", newDto);

        return VIEWPATH + "도메인 페이지 경로";
    }

    // 특정 인스턴스의 도메인 삭제
    @DeleteMapping("/domain")
    public String domainDelete(@RequestBody DomainInstanceRequest domainInstanceRequest, Model model) {

        DomainDto newDto = new DomainDto(domainInstanceRequest.getDomainName(), domainInstanceRequest.getInstanceId());

        domainService.deleteDomain(newDto);

        return VIEWPATH + "도메인 페이지 경로";
    }

    // 특정 인스턴스의 인바운드 리스트 조회
    @GetMapping("/inbounds/list")
    public String inboundList(@RequestParam(required = false) Integer instanceId, Model model) {

        if (instanceId == null) {
            return "/";     // instanceId 없는 접근일 경우 메인으로 보냄
        }

        List<InboundPolicyDto> dtoList = inboundPolicyService.findAllByInstanceId(instanceId);
        model.addAttribute("inbounds", dtoList);

        return VIEWPATH + "인바운드 리스트 페이지 경로";
    }

    @PutMapping("/inbounds/setting")
    public String inboundSetting(@RequestBody List<InboundPolicyDto> inbounds, Model model) {

        if (inbounds == null) {
            return "/";     // inbound 리스트 없으면 메인으로 반환
        }

        for (InboundPolicyDto inbound : inbounds) {
            System.out.println(inbound.toString());
        }

        List<InboundPolicyDto> savedList = inboundPolicyService.saveAll(inbounds);
        model.addAttribute("inbounds", savedList);

        return VIEWPATH + "인바운드 리스트 페이지 경로";
    }

}
