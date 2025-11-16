package com.mechanical_workshop_usm.image_module.image;

import com.mechanical_workshop_usm.config.ImageConfig;
import com.mechanical_workshop_usm.image_module.car_image.CarImage;
import com.mechanical_workshop_usm.image_module.car_image.CarImageRepository;
import com.mechanical_workshop_usm.image_module.image.dto.CreateImageRequest;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    private static final int MAX_FILE_SIZE = 5_000_000;
    private static final String MAX_FILE_SIZE_STR = MAX_FILE_SIZE / 1_000_000 + " MB";

    private static final List<String> SUPPORTED_IMAGE_TYPES = List.of("image/gif", "image/png", "image/jpeg");

    private static final Tika TIKA = new Tika();

    private final ImageRepository imageRepository;
    private final CarImageRepository carImageRepository;
    private final ImageConfig imageConfig;
    private final WorkOrderRepository workOrderRepository;

    public ImageService(ImageRepository imageRepository,
                        CarImageRepository carImageRepository,
                        ImageConfig imageConfig,
                        WorkOrderRepository workOrderRepository) {
        this.imageRepository = imageRepository;
        this.carImageRepository = carImageRepository;
        this.imageConfig = imageConfig;
        this.workOrderRepository = workOrderRepository;
    }

    public List<CreateImageRequest> findAllDashboardLights() {
        return imageRepository.findAll().stream()
            .filter(img -> img.getPath().startsWith(ImageCategory.ICONS.dir + "/"))
            .map(img -> new CreateImageRequest(img.getId(), buildPublicUrl(img.getPath()), img.getAlt()))
            .toList();
    }



    public String saveSignatureFile(MultipartFile signature) throws IOException {
        validateFile(signature);

        String ext = extractExtensionWithDot(signature.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + ext;

        // directorio relativo donde queremos guardar las firmas
        String relativeDir = "images/work-orders/signature";
        Path targetDir = resolveFullPath(relativeDir);

        // crea directorios si faltan
        Files.createDirectories(targetDir);

        Path target = targetDir.resolve(filename);

        try (InputStream in = signature.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        return filename;
    }

    private static String extractExtensionWithDot(String filename) {
        if (filename == null || filename.isBlank()) return "";
        int idx = filename.lastIndexOf('.');
        return (idx >= 0) ? filename.substring(idx) : "";
    }


    public String saveSignature(MultipartFile signature, int workOrderId) {
        validateFile(signature);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String sanitized = sanitizeFilename(signature.getOriginalFilename());

        String relativePath = String.format(
                "images/work-orders/%d/signature/%s_%s",
                workOrderId,
                timestamp,
                sanitized
        );

        Path fullPath = resolveFullPath(relativePath);

        try {
            createDirectoriesIfMissing(fullPath.getParent());
            Files.write(fullPath, signature.getBytes());
        } catch (IOException e) {
            LOGGER.error("Could not save signature file", e);
            throw new RuntimeException("Could not save signature file", e);
        }

        return relativePath;
    }

    public CarImage saveCarImage(int workOrderId, MultipartFile file, String alt) {
        validateFile(file);

        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
            .orElseThrow(() -> new RuntimeException("Work order not found"));

        final String relativePath = generateCarImagePath(workOrderId, file.getOriginalFilename());
        final Path fullPath = resolveFullPath(relativePath);

        try {
            createDirectoriesIfMissing(fullPath.getParent());
            Files.write(fullPath, file.getBytes());
        } catch (IOException e) {
            LOGGER.error("Could not save car image file", e);
            throw new RuntimeException("Could not save car image file", e);
        }

        Image imageModel = Image.builder()
            .path(relativePath)
            .alt(alt)
            .build();
        imageModel = imageRepository.save(imageModel);

        CarImage carImageModel = CarImage.builder()
            .workOrder(workOrder)
            .image(imageModel)
            .build();

        return carImageRepository.save(carImageModel);
    }

    public List<CreateImageRequest> getCarImagesByWorkOrderId(int workOrderId) {
        List<CarImage> carImages = carImageRepository.findByWorkOrder_Id(workOrderId);

        return carImages.stream()
            .map(carImage -> {
                Image img = carImage.getImage();
                return new CreateImageRequest(img.getId(), buildPublicUrl(img.getPath()), img.getAlt());
            })
            .toList();
    }

    public void deleteCarImage(int carImageId) {
        CarImage carImage = carImageRepository.findById(carImageId)
            .orElseThrow(() -> new RuntimeException("Car image not found with id: " + carImageId));

        Image image = carImage.getImage();

        carImageRepository.delete(carImage);

        if (!carImageRepository.existsCarImageModelByImage_Id(image.getId())) {
            deleteImageFile(image);
        }
    }

    private void deleteImageFile(Image image) {
        final Path fullPath = resolveFullPath(image.getPath());

        try {
            Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            LOGGER.error("Could not delete image file", e);
            throw new RuntimeException("Could not delete image file", e);
        }

        imageRepository.delete(image);
    }

    private String generateCarImagePath(int workOrderId, String originalFilename) {
        final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        final String sanitizedFilename = sanitizeFilename(originalFilename);

        return String.format("images/work-order/%d/car_images/%s_%s", workOrderId, timestamp, sanitizedFilename);
    }

    private String sanitizeFilename(String filename) {
        return filename != null ? filename.replaceAll("[^a-zA-Z0-9._-]", "_") : "file.jpg";
    }

    public String buildPublicUrl(String relativePath) {
        String normalizedPath = relativePath.replaceFirst("^/", "");

        if (!normalizedPath.startsWith("images/")) {
            normalizedPath = "images/" + normalizedPath;
        }

        final String baseUrl = imageConfig.getBaseUrl().replaceFirst("/$", "");

        return baseUrl + "/" + normalizedPath;
    }

    private Path resolveFullPath(String relativePath) {
        final String normalizedPath = relativePath.replaceFirst("^images/", "");
        return Paths.get(imageConfig.getBasePath(), normalizedPath);
    }

    private void createDirectoriesIfMissing(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File cannot be empty");
        }

        try (final InputStream inputStream = file.getInputStream()) {
            final String mimeType = TIKA.detect(inputStream);

            if (mimeType == null || !SUPPORTED_IMAGE_TYPES.contains(mimeType)) {
                throw new RuntimeException("File must be an image");
            }
        } catch (IOException e) {
            LOGGER.error("Could not analyze file", e);
            throw new RuntimeException("Could not analyze file", e);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size cannot exceed " + MAX_FILE_SIZE_STR);
        }
    }


    public enum ImageCategory {
        ICONS("icons"),
        WORK_ORDERS("work_orders"),
        CAR_PHOTOS("car_photos");

        public final String dir;

        ImageCategory(String dir) {
            this.dir = dir;
        }
    }
}