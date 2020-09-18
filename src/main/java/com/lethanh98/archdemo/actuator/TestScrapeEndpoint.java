package com.lethanh98.archdemo.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "test")
public class TestScrapeEndpoint {
    String version10 = "** Version 1.0 ** \n\n"
            + "* Homepage added \n"
            + "* Item creation form added \n"
            + "* View the watchlsit page added \n";

    @ReadOperation
    public String testGet() {

        return version10;
    }

    @WriteOperation
    public String testPost() {

        return version10;
    }

    @DeleteOperation
    public String testDelete() {

        return version10;
    }
}
