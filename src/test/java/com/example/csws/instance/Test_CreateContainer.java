package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_CreateContainer {

    @Test
    void 컨테이너_생성() {
        ShRunner shRunner = new ShRunner();
        Map result = shRunner.execCommand("CreateContainer.sh pika 192.168.50.49 " +
                                            "10004 22 ghlTest 123 2G jamesclerkmaxwell/csws_ubuntu:0.71");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 퍼블릭키_전송() {
        ShRunner shRunner = new ShRunner();
        Map result = shRunner.execCommand("SendPublickey.sh pika 192.168.50.49 ghlTest123 ghlTestKey");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 호스트_퍼블릭키_전송() {
        ShRunner shRunner = new ShRunner();
        Map result = shRunner.execCommand("H_SendPublickey.sh ghlTest123 ghlTestKey");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

}
