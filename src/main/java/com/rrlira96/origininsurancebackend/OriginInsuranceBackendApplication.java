package com.rrlira96.origininsurancebackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OriginInsuranceBackendApplication {
    private static Logger logger = LoggerFactory.getLogger(OriginInsuranceBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OriginInsuranceBackendApplication.class, args);
        logger.info("API is ready");
    }

}
