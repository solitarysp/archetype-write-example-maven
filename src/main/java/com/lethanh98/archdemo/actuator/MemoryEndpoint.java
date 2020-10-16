package com.lethanh98.archdemo.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "custom-memory")
public class MemoryEndpoint {
    String version10 = "** Version 1.0 ** \n\n"
            + "* Homepage added \n"
            + "* Item creation form added \n"
            + "* View the watchlsit page added \n";

    @ReadOperation
    public Map<String, String> testGet() {
        Map<String, String> map = new HashMap<>();
        map.put("processors (cores)", String.valueOf(Runtime.getRuntime().availableProcessors()));
        map.put("Free memory (bytes)", String.valueOf(Runtime.getRuntime().freeMemory()));
        long maxMemory = Runtime.getRuntime().maxMemory();
        map.put("Maximum memory (bytes)", String.valueOf(maxMemory));
        map.put("memory (bytes)", String.valueOf(Runtime.getRuntime().totalMemory()));
        return map;
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
