package com.example.csws.controller.instance;

import com.example.csws.entity.instance.InstanceDto;
import com.example.csws.entity.instance.StartInstanceRequest;
import com.example.csws.service.instance.InstanceService;
import com.example.csws.service.server.ServerService;
import com.example.csws.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class InstanceController {

    // 웹 뷰 파일들의 최상위 경로
    private static final String VIEWPATH = "/~~~/";

    private final UserService userService;
    private final InstanceService instanceService;
//    private final DomainService domainService;
    private final ServerService serverService;
//    private final InboundPolicyService inboundPolicyService;

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
        // 세션에 로그인 정보 없으면 메인 페이지로 반환
//        return "redirect:/";

        return VIEWPATH + "생성 페이지 패키지 경로";
    }

    // 인스턴스 생성 후 인스턴스 목록으로 이동. 실패(오류 발생) 시 생성 페이지로 돌아가기.
    @PostMapping("/creation")
    public String createInstance(Model model) {

        InstanceDto newDto = new InstanceDto();
        newDto.setName((String) model.getAttribute("name"));
        newDto.setCode(1); // 필요없으면 향후 삭제 예정.
        newDto.setState("running");
        newDto.setStorage((Double) model.getAttribute("storage"));
        newDto.setAddress((String) model.getAttribute("address"));
        newDto.setPort((int) model.getAttribute("port"));
        newDto.setKeyName((String) model.getAttribute("keyName"));
        newDto.setOs((String) model.getAttribute("os"));
        // 현재 시간 저장(LocalDateTime을 mySQL에서 호환되도록 Timestamp로 형변환)
        Timestamp curTimestamp = Timestamp.valueOf(LocalDateTime.now());
        newDto.setCreated(curTimestamp);
//        newDto.setUserId(); // 세션의 유저id 가져오기
//        newDto.setServerId(); // 소속 서버 id 가져오기

        String result = instanceService.createInstance(newDto);
        if (result.equals("success")) { // 성공적으로 db에 insert 및 쉘 스크립트 실행
            return "redirect:/instances/listUserid";
        } else {
            return "redirect:/instances/creation";
        }

    }

    // 키페어 생성
    @PostMapping("/keypair")
    public String createKeypair(Model model) {
        return null;
    }

    // 본인 혹은 타인(관리자 권한)의 인스턴스 목록 조회(userId)
    @GetMapping("/listUserid")
    public String listByUserid(Model model) {
        return null;
    }

    // 특정 서버 소속의 인스턴스 목록 조회(serverId)
    @GetMapping("/listServerid")
    public String listByServerid(Model model) {
        return null;
    }

    // 소유자 변경 페이지 이동.
    @GetMapping("/owner")
    public String owner(Model model) {
        return null;
    }

    // 소유자 변경 로직.
    @PostMapping("/owner")
    public String changeOwner(Model model) {
        return null;
    }

    // 인스턴스 세부사항 조회(instanceId 이용)
    @GetMapping("/detail")
    public String detail(Model model) {
        return "detail";
    }

    // 특정 인스턴스의 도메인 조회(instanceId)
    @GetMapping("/domain")
    public String domainList(Model model) {
        return null;
    }

    // 특정 인스턴스의 도메인 저장(추가)
    @PostMapping("/domain")
    public String domainCreate(Model model) {
        return null;
    }

    // 특정 인스턴스의 도메인 삭제
    @DeleteMapping("/domain")
    public String domainDelete(Model model) {
        return null;
    }

    // 특정 인스턴스의 인바운드 리스트 조회
    @GetMapping("/inbounds/list")
    public String inboundList(Model model) {
        return null;
    }

    @PostMapping("/inbounds/setting")
    public String inboundSetting(Model model) {
        return null;
    }

}
