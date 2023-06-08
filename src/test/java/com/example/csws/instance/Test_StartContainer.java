package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_StartContainer {

    @Test
    void 컨테이너_시작() {

        ShRunner shRunner = new ShRunner();
        Map<Integer, String> result = shRunner.execCommand("H_StartContainer.sh test03");
        System.out.println(result.get(0) + "\n" + result.get(1));

    }

}
