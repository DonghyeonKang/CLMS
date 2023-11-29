package com.example.csws.instance;

import com.example.csws.common.shRunner.ShParser;
import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_StartContainer {
    @Test
    void 컨테이너_시작() {
        // 쉘 스크립드 실행하기
        ShRunner shRunner = new ShRunner();
        Map result = shRunner.execCommand("StartContainer.sh pika 192.168.50.49 ghlTest123");
        System.out.println(result.get(0) + "\n" + result.get(1));

        // 실행 후 실행 성공 여부 체크
        ShParser shParser = new ShParser();
        System.out.println(shParser.isSuccess(result.get(1).toString()));
    }
}
