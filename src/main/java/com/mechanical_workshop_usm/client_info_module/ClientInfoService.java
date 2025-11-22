package com.mechanical_workshop_usm.client_info_module;

import com.mechanical_workshop_usm.client_info_module.dtos.Client;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoService {

    private final ClientInfoRepository clientInfoRepository;
    private final ClientInfoValidator clientInfoValidator;

    public ClientInfoService(ClientInfoRepository clientInfoRepository, ClientInfoValidator clientInfoValidator) {
        this.clientInfoRepository = clientInfoRepository;
        this.clientInfoValidator = clientInfoValidator;
    }
    public List<Client> getAllClients() {
        return clientInfoRepository.findAll().stream()
            .map(clientInfo -> new Client(
                clientInfo.getId(),
                clientInfo.getFirstName(),
                clientInfo.getRut(),
                clientInfo.getLastName(),
                clientInfo.getEmailAddress(),
                clientInfo.getAddress(),
                clientInfo.getCellphoneNumber()
            )).toList();
    }

    public void validateOnCreate(CreateClientRequest createClientRequest) {
        clientInfoValidator.validateOnCreate(createClientRequest);
    }

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        clientInfoValidator.validateOnCreate(createClientRequest);

        ClientInfo clientInfo = new ClientInfo(
                createClientRequest.firstName(),
                createClientRequest.rut(),
                createClientRequest.lastName(),
                createClientRequest.emailAddress(),
                createClientRequest.address(),
                createClientRequest.cellphoneNumber()
        );

        ClientInfo savedClientInfo = clientInfoRepository.save(clientInfo);

        return new CreateClientResponse(savedClientInfo.getId(), savedClientInfo.getCellphoneNumber());
    }


}
