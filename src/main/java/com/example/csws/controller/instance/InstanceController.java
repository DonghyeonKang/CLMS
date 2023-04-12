package com.example.csws.controller.instance;

import com.example.csws.entity.instance.StartInstanceRequest;
import com.example.csws.service.instance.InstanceService;
import com.example.csws.service.server.ServerService;
import com.example.csws.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class InstanceController {

    // 웹 뷰 파일들의 최상위 경로
    private static final String VIEWPATH = "";

    private final UserService userService;
    private final InstanceService instanceService;
//    private final DomainService domainService;
    private final ServerService serverService;
//    private final InboundPolicyService inboundPolicyService;

    // return VIEWPATH + "웹 페이지 경로" : 이동할 웹 페이지 경로를 반환.
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
    public String createInstance(/*InstanceDto instanceDto*/) {

        return "redirect:/";
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

    // 본인 혹은 타인(관리자 권한)의 인스턴스 목록 조회(serverId)
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
