package com.lethanh98.archdemo.actuator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "status-server")
public class StatusServerEndpoint {
    @Autowired
    ObjectMapper objectMapper;


    @ReadOperation
    public Map<String, Object> statusServer() {
        Map<String, Object> map = new HashMap<>();
        map.put("Available processors (cores): {} ", Runtime.getRuntime().availableProcessors());
        long maxMemory = Runtime.getRuntime().maxMemory();
        map.put(" Maximum memory (bytes): {}", (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        map.put(" Total memory (bytes): {} ", Runtime.getRuntime().totalMemory());
        return map;
    }
}
