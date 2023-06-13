package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_InboundNginx {

    private final ShRunner shRunner = new ShRunner();

    @Test
    void 인바운드_추가() {
        Map result = shRunner.execCommand("AddInbound.sh pika 192.168.50.49" +
                                            "ghlTest123 10004:8090 2G jamesclerkmaxwell/csws_ubuntu:0.71");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 인바운드_삭제() {
        Map result = shRunner.execCommand("DeleteInbound.sh pika 192.168.50.49" +
                                            "ghlTest123 10004:8090 2G jamesclerkmaxwell/csws_ubuntu:0.71");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 엔진X_추가() {
        Map result = shRunner.execCommand("AddNginx.sh pika 192.168.50.49" +
                                            "domainTest domainTest.co.kr 10004");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

    @Test
    void 엔진X_삭제() {
        Map result = shRunner.execCommand("AddNginx.sh pika 192.168.50.49 domainTest");
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

}
