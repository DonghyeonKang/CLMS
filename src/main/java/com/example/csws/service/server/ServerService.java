package com.example.csws.service.server;

import com.example.csws.common.shRunner.ParserResponseDto;
import com.example.csws.common.shRunner.ShParser;
import com.example.csws.common.shRunner.ShRunner;
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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    private final DepartmentRepository departmentRepository;
    private final ShRunner shRunner;
    private final ShParser shParser;

    // 서버 등록
    public ServerDto registerServer(ServerDto serverDto) {
        // department 객체 가져오기
        Department department = departmentRepository.getReferenceById(serverDto.getDepartmentId());
        // dto to entity
        Server server = serverDto.toEntity(department);
        Server newServer = serverRepository.save(server);
        return ServerDto.builder()
                .serverUsername(newServer.getServerUsername())
                .ipv4(newServer.getIpv4())
                .departmentId(newServer.getDepartment().getId())
                .serverName(newServer.getName())
                .build();
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
        Server baseServer = serverRepository.findById(serverId).get();
        ServerResourceResponse serverResourceResponse = new ServerResourceResponse();
        // serverId 로 쉘스크립트에 필요한 파라미터 DB에서 가져와 만들어야함

        // 쉘 실행
        try {
            // 서버 리소스 확인
            Map result = shRunner.execCommand("CheckServerResource.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4());

            // 쉘 리턴 값 파싱
            ParserResponseDto parserResponseDto = shParser.checkServerResource(result.get(1).toString());

            if(shParser.isSuccess(result.get(1).toString())) {
                return parserResponseDto.toDto();
            }

            return null;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    // 서버 삭제
    public void deleteServer(int serverId) {
        try {
            serverRepository.deleteById(serverId);  // deleteById 로
        } catch (EmptyResultDataAccessException e) {    // deleteById 가 던질 수 있는 예외 처리
            System.out.println(e.toString());   // 프린트만 하는 게 아니라 exception return 할 수 있도록 처리해야한다.
        }
    }

    // 서버의 연결 상태 확인 -> .sh 실행 후 리턴 값 편집
    private String getServerConnection(int serverId) {
        Server baseServer = serverRepository.findById(serverId).get();

        // 쉘 실행
        try {
            // 인스턴스 제거
            Map result = shRunner.execCommand("IsConnected.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4());

            if(shParser.isSuccess(result.get(1).toString())) {
                return "connected";
            }

            return "disconnected";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public ServerDto findById(Long serverId) {
        Server server = serverRepository.findById(serverId).get();

        ServerDto dto = new ServerDto();
        dto.setServerName(server.getName());
        dto.setServerUsername(server.getServerUsername());
        dto.setIpv4(server.getIpv4());
        dto.setDepartmentId(server.getDepartment().getId());

        return dto;
    }
}
