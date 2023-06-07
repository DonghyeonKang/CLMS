package com.example.csws.instance;

import com.example.csws.common.shRunner.ShRunner;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Test_CreateKeypairs {

    @Test
    void 키페어_생성() {

        ShRunner shRunner = new ShRunner();
        shRunner.execCommand("CreateKeypairs.sh pika ghlkey");

    }

}
