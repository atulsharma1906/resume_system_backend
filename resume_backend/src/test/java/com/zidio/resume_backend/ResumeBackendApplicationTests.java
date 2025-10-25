package com.zidio.resume_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = {"jwt.secret=zidioSecretKey", "jwt.expiration-ms=86400000"})
class ResumeBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
