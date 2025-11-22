package com.mechanical_workshop_usm.client_info_module;

import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import org.springframework.stereotype.Service;

@Service
public class ClientInfoService {

    private final ClientInfoRepository clientInfoRepository;
    private final ClientInfoValidator clientInfoValidator;

    public ClientInfoService(ClientInfoRepository clientInfoRepository, ClientInfoValidator clientInfoValidator) {
        this.clientInfoRepository = clientInfoRepository;
        this.clientInfoValidator = clientInfoValidator;
    }

    public void validateOnCreate(CreateClientRequest createClientRequest) {
        clientInfoValidator.validateOnCreate(createClientRequest);
    }

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        clientInfoValidator.validateOnCreate(createClientRequest);

        ClientInfo clientInfo = new ClientInfo(
                createClientRequest.firstName(),
                createClientRequest.lastName(),
                createClientRequest.emailAddress(),
                createClientRequest.address(),
                createClientRequest.cellphoneNumber(),
                createClientRequest.rut()
        );

        ClientInfo savedClientInfo = clientInfoRepository.save(clientInfo);

        return new CreateClientResponse(savedClientInfo.getId(), savedClientInfo.getCellphoneNumber());
    }


}
