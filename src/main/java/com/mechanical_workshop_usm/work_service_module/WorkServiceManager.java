package com.mechanical_workshop_usm.work_service_module;

import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.work_service_module.dto.GetService;

import java.time.LocalTime;
import java.util.List;
import java.time.Duration;

@org.springframework.stereotype.Service
public class WorkServiceManager {

    private final WorkServiceRepository repository;

    public WorkServiceManager(WorkServiceRepository repository) {
        this.repository = repository;
    }
    public GetService create(CreateWorkServiceRequest request) {

        if (repository.existsByServiceName(request.serviceName())) {
            throw new IllegalArgumentException("Service with name '" + request.serviceName() + "' already exists");
        }
        Duration estimated = parseDuration(request.estimatedTime());
        WorkService service = new WorkService(
            request.serviceName(),
            estimated
        );

        WorkService saved = repository.save(service);

        return new GetService(
                saved.getId(),
                saved.getServiceName(),
                saved.getEstimatedTime().toString()
        );
    }


    public List<GetService> getAll() {
        return repository.findAll()
            .stream()
            .map(service -> new GetService(
                service.getId(),
                service.getServiceName(),
                minutesToHourMinuteString(service.getEstimatedTimeMinutes())
            ))
            .toList();
    }

    public static String minutesToHourMinuteString(long minutes) {
        long hours = minutes / 60;
        long mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    public static Duration parseDuration(String input) {
        String[] parts = input.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = (parts.length == 3) ? Integer.parseInt(parts[2]) : 0;
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
}
