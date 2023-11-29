package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_RemoveContainer {

    @Test
    void 컨테이너_삭제() {

        ShRunner shRunner = new ShRunner();
        Map result = shRunner.execCommand("RemoveContainer.sh pika 192.168.50.49 ghlTest 123");
        System.out.println(result.get(0) + "\n" + result.get(1));

    }

}
