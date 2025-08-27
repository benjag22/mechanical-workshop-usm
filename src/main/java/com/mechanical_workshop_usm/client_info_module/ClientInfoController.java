package com.mechanical_workshop_usm.client_info_module;

import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client-info")
public class ClientInfoController {

    private final ClientInfoService clientInfoService;

    public ClientInfoController(ClientInfoService clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

    @PostMapping
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest createClientRequest) {
        CreateClientResponse response = clientInfoService.createClient(createClientRequest);
        return ResponseEntity.ok(response);
    }
}
