package com.example.csws.service.instance;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.common.shRunner.ParserResponseDto;
import com.example.csws.common.shRunner.ShParser;
import com.example.csws.common.shRunner.ShRunner;
import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.instance.InstanceDto;
import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import com.example.csws.repository.boundPolicy.InboundPolicyRepository;
import com.example.csws.repository.instance.InstanceRepository;
import com.example.csws.repository.lecture.LectureRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class InstanceServiceImpl implements InstanceService{

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final InstanceRepository instanceRepository;
    private final InboundPolicyRepository inboundPolicyRepository;
    private final EntityManager entityManager;
    private final ShRunner shRunner;
    private final ShParser shParser;

    @Override
    public int findMyInstanceId(Long userId, Long lectureId) {
        try {
            int instanceId = instanceRepository.findIdByUserIdAndLectureId(userId, lectureId)
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
            return instanceId;
        } catch (Exception e) {
            System.out.println("애러 발생");
            System.out.println(lectureId);
            System.out.println(userId);
            return -1;  // 없으면 -1
        }

    }

    // 컨트롤러에서 이미 인스턴스의 code 필드를 받아왔음을 가정함.
    // DB에서 프로시저를 이용하여 code를 설정할 것이라면 연관된 전체 기능 수정 필요.
    // dto를 entity로 변환 후 save(insert 혹은 update)한 결과값을 컨트롤러에 반환.
    // 1) 호스트 포트는 유저가 선택한 포트. 2) 컨테이너 포트는 22 고정.
    // 3) 유저 이름 : 유저가 입력한 이름(instance.getName())
    // 4) 유저 코드 : instanceId로 대체.
    // 5) 용량. 6) 이미지 이름
    @Transactional
    @Override
    public String createInstance(InstanceDto instanceDto, String username) {
        // 사용자 데이터 가져오기
        User newUser = userRepository.getReferenceById(instanceDto.getUserId());

        // lecture 에 연결된 server 가져오기
        Lecture lecture = lectureRepository.findById(instanceDto.getLectureId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        Server baseServer = lecture.getServer();
        instanceDto.setAddress(baseServer.getIpv4());

        // db에 인스턴스 정보 저장
        Instance entity = instanceRepository.save(instanceDto.toEntity(newUser, lecture));
        username = username.replaceAll("[@.]", "");


        /*
        *  db 저장 후 원격 쉘 실행
        * */
        // ssh port 값 생성
        int instanceId = entity.getId();
        int port = 2000 + instanceId;

        // ssh port 값 저장
        entity.updateInstancePort(port);

        // 1 서버에서 사용하는 계정명(serverUsername), 2 서버 ip 주소, 3 호스트 포트, 4 컨테이너 포트(항상 22),
        // 5 인스턴스 name, 6 인스턴스 id, 7 용량, 8 OS
        try {
            Map result = shRunner.execCommand("CreateContainer.sh", baseServer.getServerUsername(), baseServer.getIpv4(),
                    Integer.toString(entity.getPort()), "22",
                    username, Integer.toString(entity.getId()),
                    Double.toString(entity.getStorage()), entity.getOs());

            if (!shParser.isSuccess(result.get(1).toString())) { // TODO: 실패시 엔티티 삭제해야함
                instanceRepository.deleteById(entity.getId());
                return "failure";
            }
        } catch (Exception e) {
            return e.toString();
        }

        // 인바운드 추가
        InboundPolicy inboundPolicy = InboundPolicy.builder()
                .instance(entity)
                .instancePort(22)
                .hostPort(entity.getPort())
                .build();
        inboundPolicyRepository.save(inboundPolicy);

        // TODO: 퍼블릭 키 호스트 서버로 전송
        try {
            Map result = shRunner.execCommand("SendPublickey.sh", baseServer.getServerUsername(), baseServer.getIpv4(),
                    username + Integer.toString(entity.getId()),
                    entity.getKeyName());

            if (shParser.isSuccess(result.get(1).toString())) {
                return "success";
            }
            instanceRepository.deleteById(entity.getId());
            return "failure";
        } catch (Exception e) {
            return e.toString();
        }
    }

    // 인스턴스 id를 이용해 개별 인스턴스 검색 후 Optional에 넣어서 반환. 객체가 없으면 null이 담겨있음.
    @Override
    public InstanceDto findById(int instanceId) {
        try {
            InstanceDto instanceDto = instanceRepository.findById(instanceId)
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND))
                    .toDto();
            return instanceDto;
        } catch (Exception e) {
            e.printStackTrace();
            return new InstanceDto();
        }
    }

    // serverId로 전체 리스트 가져오기.
    @Override
    public List<InstanceDto> findAllByLectureId(Long lectureId) {
        List<Instance> entityList = instanceRepository.findAllByLectureId(lectureId);
        List<InstanceDto> dtoList = new ArrayList<>();
        for (Instance entity : entityList) {
            dtoList.add(entity.toDto());
        }
        return dtoList;
    }

    // 실행할 스크립트 파일 이름과 필요 인수를 넘겨주고 실행. 실패 시 예외 문구가 뜬다.
    // 쉘에서 인수로 받는 컨테이너 이름 : instance 테이블의 name + instanceId
    @Transactional
    @Override
    public String startInstance(int instanceId, String username) {
        // instanceId를 이용해서 인스턴스와 서버 정보 가져옴
        Instance entity = instanceRepository.findById(instanceId).get();
        Server baseServer = entity.getLecture().getServer();

        // db 에서 상태 업데이트
        entityManager.persist(entity);
        entity.updateStatus("running");

        // 쉘 실행
        // 1 서버에서 사용하는 계정명(serverUsername), 2 서버 ip 주소,
        // 3 컨테이너_이름(인스턴스 name + 인스턴스 id)을 인수로 넘겨준다.
        try {
            Map result = shRunner.execCommand("StartContainer.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), username + entity.getId());

            if(shParser.isSuccess(result.get(1).toString())) {
                System.out.println("success");
                return "success";
            }

            System.out.println("failure");
            return "failure";
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
    }
    @Transactional
    @Override
    public String stopInstance(int instanceId, String username) {
        Instance entity = instanceRepository.findById(instanceId).get();
        Server baseServer = entity.getLecture().getServer();

        // db 에서 상태 업데이트
        entityManager.persist(entity);
        entity.updateStatus("stopped");

        // 쉘 실행
        try {
            Map result = shRunner.execCommand("StopContainer.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), username + entity.getId());

            if(shParser.isSuccess(result.get(1).toString())) {
                return "success";
            }

            return "failure";
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
    }
    @Transactional
    @Override
    public String restartInstance(int instanceId, String username) {
        Instance entity = instanceRepository.findById(instanceId).get();
        Server baseServer = entity.getLecture().getServer();

        // db 에서 상태 업데이트
        entityManager.persist(entity);
        entity.updateStatus("running");

        // 쉘 실행
        try {
            Map result = shRunner.execCommand("RestartContainer.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), username + entity.getId());


            if(shParser.isSuccess(result.get(1).toString())) {
                return "success";
            }

            return "failure";
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
    }
    @Transactional
    @Override
    public String deleteInstance(int instanceId, String username) {
        Instance entity = instanceRepository.findById(instanceId).get();
        Server baseServer = entity.getLecture().getServer();

        // 쉘 실행
        try {
            // 인스턴스 제거
            Map result = shRunner.execCommand("RemoveContainer.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), username + entity.getId());

            if(shParser.isSuccess(result.get(1).toString())) {
                // db 에서 제거
                instanceRepository.deleteById(instanceId);
                return "success";
            }

            return "failure";
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
    }

    // csws에 파일이 생성되는 경로 : ~/keys/서버_계정명/사용자_입력_키_이름.pem, pub
    @Override
    public String createKeyPair(String hostName, String keyName) {
        try {
            Map result = shRunner.execCommand("CreateKeypairs.sh", hostName, keyName);
            if(shParser.isSuccess(result.get(1).toString())) {
                return "success";
            }
            return "failure";
        } catch (Exception e) {
            return e.toString();
        }
    }

    // 에러 발생시 에러 출력 및 false 값 담긴 responseDto 반환
    // 성공시 데이터 담긴 dto 반환
    @Override
    public ParserResponseDto checkContainerResource(String hostName, String hostIp, String containerName) {

        try {
            Map result = shRunner.execCommand("CheckContainerResource.sh", hostName, hostIp, containerName);
            return shParser.checkContainerResource(result.get(1).toString());
        } catch (Exception e) {
            ParserResponseDto responseDto = new ParserResponseDto();
            responseDto.setSuccess(false);
            System.out.println(e.toString());
            return responseDto;
        }
    }

    @Override
    public ParserResponseDto checkServerResource(String hostName, String hostIp) {

        try {
            Map result = shRunner.execCommand("CheckServerResource.sh", hostName, hostIp);
            return shParser.checkServerResource(result.get(1).toString());
        } catch (Exception e) {
            ParserResponseDto responseDto = new ParserResponseDto();
            responseDto.setSuccess(false);
            System.out.println(e.toString());
            return responseDto;
        }
    }
}
