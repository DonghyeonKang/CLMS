package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_ResourceConnectivityPrint {

    private final ShRunner shRunner = new ShRunner();

    @Test
    void 체크_컨테이너_리소스() {
        Map result = shRunner.execCommand("CheckContainerResource.sh pika 192.168.50.49" +
                                                            "ghlTest123");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 체크_서버_리소스() {
        Map result = shRunner.execCommand("CheckServerResource.sh pika 192.168.50.49");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 연결_확인() {
        Map result = shRunner.execCommand("IsConnected.sh pika 192.168.50.49");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 컨테이너_전체_목록_관리자() {
        Map result = shRunner.execCommand("PrintContainerforManager.sh pika 192.168.50.49");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 컨테이너_IP_전체_목록_관리자() {
        Map result = shRunner.execCommand("PrintIPforManager.sh pika 192.168.50.49");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 컨테이너_IP_목록_학생() {
        Map result = shRunner.execCommand("PrintIPforUser.sh pika 192.168.50.49 ghlTest123");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 컨테이너_전체_상태_관리자() {
        Map result = shRunner.execCommand("PrintStatusforManager.sh pika 192.168.50.49");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 컨테이너_상태_학생() {
        Map result = shRunner.execCommand("PrintStatusforUser.sh pika 192.168.50.49 ghlTest123");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

}
