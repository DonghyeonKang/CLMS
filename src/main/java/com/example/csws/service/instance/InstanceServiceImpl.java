package com.example.csws.service.instance;

import com.example.csws.common.shRunner.ShRunner;
import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.instance.InstanceDto;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import com.example.csws.repository.instance.InstanceRepository;
import com.example.csws.repository.server.ServerRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstanceServiceImpl implements InstanceService{

    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final InstanceRepository instanceRepository;
    private final ShRunner shRunner;

    // 컨트롤러에서 이미 인스턴스의 code 필드를 받아왔음을 가정함.
    // DB에서 프로시저를 이용하여 code를 설정할 것이라면 연관된 전체 기능 수정 필요.
    // dto를 entity로 변환 후 save(insert 혹은 update)한 결과값을 컨트롤러에 반환.
    // 1) 호스트 포트는 유저가 선택한 포트. 2) 컨테이너 포트는 22 고정.
    // 3) 유저 이름 : 유저가 입력한 이름(instance.getName())
    // 4) 유저 코드 : instanceId로 대체.
    // 5) 용량. 6) 이미지 이름
    @Override
    public String createInstance(InstanceDto instanceDto) {
        User newUser = userRepository.getReferenceById((long)instanceDto.getUserId());
        Server baseServer = serverRepository.findById(instanceDto.getServerId()).get();
        Instance entity = instanceRepository.save(instanceDto.toEntity(newUser, baseServer));

        try {
            shRunner.execCommand("CreateContainer.sh", Integer.toString(entity.getPort()), "22",
                    entity.getName(), Integer.toString(entity.getId()),
                    Double.toString(entity.getStorage()), entity.getOs());
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    // 인스턴스 id를 이용해 개별 인스턴스 검색 후 Optional에 넣어서 반환. 객체가 없으면 null이 담겨있음.
    @Override
    public Optional<InstanceDto> findById(int instanceId) {
        return Optional.ofNullable(instanceRepository.findById(instanceId).get().toDto());
    }

    // userId로 엔티티 리스트를 받아온 뒤, 각 엔티티를 dto로 변환하고 dto 리스트에 추가하여 반환.
    @Override
    public List<InstanceDto> findAllByUserId(int userId) {
        List<Instance> entityList = instanceRepository.findAllByUserId(userId);
        List<InstanceDto> dtoList = new ArrayList<>();
        for (Instance entity : entityList) {
            dtoList.add(entity.toDto());
        }
        return dtoList;
    }

    // serverId로 전체 리스트 가져오기.
    @Override
    public List<InstanceDto> findAllByServerId(int serverId) {
        List<Instance> entityList = instanceRepository.findAllByServerId(serverId);
        List<InstanceDto> dtoList = new ArrayList<>();
        for (Instance entity : entityList) {
            dtoList.add(entity.toDto());
        }
        return dtoList;
    }

    // 실행할 스크립트 파일 이름과 필요 인수를 넘겨주고 실행. 실패 시 예외 문구가 뜬다.
    // 컨테이너 이름 : instance 테이블의 name + code
    @Override
    public String startInstance(int instanceId) {
        try {
            shRunner.execCommand("StartContainer.sh", Integer.toString(instanceId));
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String stopInstance(int instanceId) {
        try {
            shRunner.execCommand("StopContainer.sh", Integer.toString(instanceId));
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String restartInstance(int instanceId) {
        try {
            shRunner.execCommand("RestartContainer.sh", Integer.toString(instanceId));
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String deleteInstance(int instanceId) {
        try {
            shRunner.execCommand("RemoveContainer.sh", Integer.toString(instanceId));
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public void createKeyPair(String hostName, String keyName) {
    // 1. 사용자가 키페어 이름 입력 후 다운로드 클릭
    // 2. 키페어 생성 - 전송 - 개인키 삭제
    // 3. 컨테이너 생성 - createContainer.sh - sendPublickey.sh
    }

    // userId가 수정되어있는 dto를 컨트롤러에서 받은 뒤 엔티티로 변환해 save(update).
    // dto 내부의 새로운 user의 email로 user 엔티티 조회 후 toEntity() 메서드 파라미터로 넘겨주기.
    @Override
    public InstanceDto changeUserid(InstanceDto instanceDto) {
        // getReferenceById 메서드에 필요한 인자는 Long인데 우리가 설정한 엔티티는 id가 int로 되어있음.
        User newUser = userRepository.getReferenceById((long) instanceDto.getUserId());
        Server baseServer = serverRepository.findById(instanceDto.getServerId()).get();
        Instance entity = instanceRepository.save(instanceDto.toEntity(newUser, baseServer));

        return entity.toDto();
    }
}
