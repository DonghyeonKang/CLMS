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
        Map<Integer, String> result = shRunner.execCommand("CreateContainer.sh ghl 127.0.0.1 " +
                                                                "8888 88 ghl 123 2G csws_ubuntu:1.0");
        System.out.println(result.get(0));
        System.out.println(result.get(1));

    }

}
