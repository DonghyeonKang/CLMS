package com.example.csws.controller.instance;

import com.example.csws.entity.instance.InstanceDto;
import com.example.csws.entity.instance.StartInstanceRequest;
import com.example.csws.entity.user.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/listUserId")  // userId랑 serverId 다 post매핑으로 바꾸는게 나을듯?
    public String listByUserId(Model model) {

        List<InstanceDto> newList = new ArrayList<>(); // 반환할 리스트
        int userId;
        // 프론트에서 userId가 넘어오면 관리자가 타 학생의 userId로 조회한다는 의미.
        if (model.containsAttribute("userId")) {
            userId = (int)model.getAttribute("userId"); // 조회할 학생의 id 가져오기.
        }
        else {  // 프론트에서 안 넘어오면 로그인 세션의 학생 본인 userId 받아오기.
            userId = 0; // 임의이 값. 추후 Authentication에서 userId 받아오게 수정 예정. 로그인 예외 처리도 할 것.
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
        model.addAttribute("instanceList", newList);   // 모델에 올리기.

        return VIEWPATH + "리스트 조회 페이지 경로";  // 뷰 페이지로 넘어가기.
    }

    // 특정 서버 소속의 인스턴스 목록 조회(serverId)
    @GetMapping("/listServerId")
    public String listByServerId(Model model) {

        List<InstanceDto> newList = new ArrayList<>(); // 반환할 리스트
        // 서버 id를 안 넘겨줬을 경우 실패 에러 띄우기
        if (!model.containsAttribute("serverId")) {
            return "/";
        }

        int serverId = (int) model.getAttribute("serverId");
        List<InstanceDto> list = instanceService.findAllByUserId(serverId);
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
    public String owner(Model model) {  // 변경하려는 인스턴스 상세 정보 반환하기.

        // 인스턴스 id 없을 경우 id 없음 에러 띄우기
        if (!model.containsAttribute("instanceId")) {
            return "/";
        }

        int instanceId = (int) model.getAttribute("instanceId");
        Optional<InstanceDto> dto = instanceService.findById(instanceId);

        if (dto.isEmpty()) {    // 반환 객체가 null일 경우 db에 인스턴스 정보 없음 에러 띄우기
            return "/";
        }

        model.addAttribute("instance", dto.get());

        return VIEWPATH + "소유자 변경을 위한 페이지 경로";
    }

    // 소유자 변경 로직.
    @PostMapping("/owner")
    public String changeOwner(Model model) {

        // instanceId, 소유권 변경할 학생의 username(email) 프론트에서 넘겨받기.
        int instanceId = (int) model.getAttribute("instanceId");
        String email = (String) model.getAttribute("email");
        Optional<InstanceDto> dto = instanceService.findById(instanceId);
        if (dto.isEmpty()) {    // 반환 객체가 null일 경우 db에 인스턴스 정보 없음 에러 띄우기
            return "/";
        }
        InstanceDto newDto = dto.get();

        User student = userService.getUser(email);
        newDto.setUserId(student.getId());

        instanceService.createInstance(newDto); // 기존에 존재하던 객체를 업데이트

        return "redirect:listUserid";
    }

    // 인스턴스 세부사항 조회(instanceId 이용)
    @GetMapping("/detail")
    public String detail(Model model) {

        if (model.containsAttribute("instanceId")) {
            return "/"; // id 없음 에러
        }
        int instanceId = (int) model.getAttribute("instanceId");
        InstanceDto dto = instanceService.findById(instanceId).get();
        model.addAttribute("instance", dto);

        return VIEWPATH + "세부사항 페이지 경로";
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
