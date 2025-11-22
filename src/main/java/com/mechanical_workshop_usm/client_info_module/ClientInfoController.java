package com.mechanical_workshop_usm.client_info_module;

import com.mechanical_workshop_usm.client_info_module.dtos.Client;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-info")
public class ClientInfoController {
    private final ClientInfoService clientInfoService;
    private final ClientInfoRepository clientInfoRepository;

    public ClientInfoController(ClientInfoService clientInfoService, ClientInfoRepository clientInfoRepository) {
        this.clientInfoService = clientInfoService;
        this.clientInfoRepository = clientInfoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> response = clientInfoService.getAllClients();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest createClientRequest) {
        CreateClientResponse response = clientInfoService.createClient(createClientRequest);
        return ResponseEntity.ok(response);
    }
}