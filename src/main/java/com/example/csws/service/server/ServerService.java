package com.example.csws.service.server;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.server.ServerDto;
import com.example.csws.entity.server.ServerListResponse;
import com.example.csws.entity.server.ServerResourceResponse;
import com.example.csws.repository.department.DepartmentRepository;
import com.example.csws.repository.server.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    private final DepartmentRepository departmentRepository;

    // 서버 등록
    public void registerServer(ServerDto serverDto) {
        // department 객체 가져오기
        Department department = departmentRepository.getReferenceById(serverDto.getDepartmentId());
        // dto to entity
        Server server = serverDto.toEntity(department);
        serverRepository.save(server);
    }

    // 학과 서버 목록 조회
    public List<ServerListResponse> getServerList(int departmentId) {
        List<ServerListResponse> serverList = new ArrayList<>();

        List<Server> servers = serverRepository.findAllByDepartmentId(departmentId);
        for(Server server : servers) {
            serverList.add(
                    ServerListResponse.builder()
                            .serverId(server.getId())
                            .name(server.getName())
                            .ipv4(server.getIpv4())
                            .hostname(server.getServerUsername())
                            .build()
            );
        }

        return serverList;
    }

    // 서버의 리소스 (램, 디스크 사용량, 서버 연결 상태) 조회 -> .sh 실행 후 리턴 값 편집
    public ServerResourceResponse getServerResource(int serverId) {
        ServerResourceResponse serverResourceResponse = new ServerResourceResponse();
        // serverId 로 쉘스크립트에 필요한 파라미터 DB에서 가져와 만들어야함

        serverResourceResponse.setConnection(getServerConnection());
        serverResourceResponse.setDisk(getDiskStatus());
        serverResourceResponse.setRam(getRamStatus());
        return serverResourceResponse;
    }

    // 서버 삭제
    public void deleteServer(int serverId) {
        try {
            serverRepository.deleteById(serverId);  // deleteById 로
        } catch (EmptyResultDataAccessException e) {    // deleteById 가 던질 수 있는 예외 처리
            System.out.println(e.toString());   // 프린트만 하는 게 아니라 exception return 할 수 있도록 처리해야한다.
        }
    }

    // 디스크 사용량 조회
    private String getDiskStatus() {
        return "";
    }

    // 램 사용량 조회
    private String getRamStatus() {
        return "";
    }

    // 서버의 연결 상태 확인 -> .sh 실행 후 리턴 값 편집
    private String getServerConnection() {
        return "";
    }

    public ServerDto findById(int serverId) {
        Server server = serverRepository.findById(serverId).get();

        ServerDto dto = new ServerDto();
        dto.setServerName(server.getName());
        dto.setServerUsername(server.getServerUsername());
        dto.setIpv4(server.getIpv4());
        dto.setDepartmentId(server.getDepartment().getId());

        return dto;
    }
}
