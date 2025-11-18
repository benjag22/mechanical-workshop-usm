package com.mechanical_workshop_usm.work_service_module;

import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.work_service_module.dto.GetWorkServiceResponse;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class WorkServiceManager {

    private final WorkServiceRepository repository;

    public WorkServiceManager(WorkServiceRepository repository) {
        this.repository = repository;
    }
    public GetWorkServiceResponse create(CreateWorkServiceRequest request) {

        if (repository.existsByServiceName(request.serviceName())) {
            throw new IllegalArgumentException("Service with name '" + request.serviceName() + "' already exists");
        }
        LocalTime estimated = LocalTime.parse(request.estimatedTime());
        WorkService service = new WorkService(
                request.serviceName(),
                estimated
        );

        WorkService saved = repository.save(service);

        return new GetWorkServiceResponse(
                saved.getId(),
                saved.getServiceName(),
                saved.getEstimatedTime().toString()
        );
    }


    public List<GetWorkServiceResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(service -> new GetWorkServiceResponse(
                        service.getId(),
                        service.getServiceName(),
                        service.getEstimatedTime().toString()
                ))
                .toList();
    }
}
