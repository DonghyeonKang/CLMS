package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class Test_TransferOwnership {

    private final ShRunner shRunner = new ShRunner();

    @Test
    void 컨테이너_소유자_변경() {
        Map result = shRunner.execCommand("StartContainer.sh pika 192.168.50.49 ghlTest 123" +
                                            "ghlChanged 456 2G ghlTestKey");
        System.out.println(result.get(0) + "\n" + result.get(1));
    }

}
