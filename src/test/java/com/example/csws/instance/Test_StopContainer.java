package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_StopContainer {

    @Test
    void 컨테이너_중지() {

        ShRunner shRunner = new ShRunner();
        Map<Integer, String> result = shRunner.execCommand("H_StopContainer.sh test03");
        System.out.println(result.get(0) + "\n" + result.get(1));

    }

}
