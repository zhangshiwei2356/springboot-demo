package com.cloud.app.archive;

import com.cloud.common.exception.GlobalException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ArchiveService {

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ArchiveProperties properties;
    private final Path rootDir;
    private final Path filesDir;
    private final ArchiveMetaStore metaStore;

    public ArchiveService(ArchiveProperties properties) throws IOException {
        this.properties = properties;
        this.rootDir = Path.of(properties.getUploadDir()).toAbsolutePath().normalize();
        this.filesDir = rootDir.resolve("files");
        Files.createDirectories(filesDir);
        this.metaStore = new ArchiveMetaStore(rootDir);
    }

    public List<ArchiveRecord> list() throws IOException {
        return metaStore.findAll().stream()
                .sorted(Comparator.comparing(ArchiveRecord::getId).reversed())
                .toList();
    }

    public ArchiveRecord get(Long id) throws IOException {
        return metaStore.findById(id).orElseThrow(() -> new GlobalException("档案不存在"));
    }

    public ArchiveRecord create(ArchiveRequest request) throws IOException {
        validateMeta(request.getTitle(), request.getCategory());
        String now = now();
        ArchiveRecord record = new ArchiveRecord();
        record.setTitle(request.getTitle().trim());
        record.setCategory(StringUtils.hasText(request.getCategory()) ? request.getCategory().trim() : "未分类");
        record.setDescription(trimToNull(request.getDescription()));
        record.setCreateTime(now);
        record.setUpdateTime(now);
        return metaStore.insert(record);
    }

    public ArchiveRecord update(Long id, ArchiveRequest request) throws IOException {
        ArchiveRecord record = metaStore.findById(id).orElseThrow(() -> new GlobalException("档案不存在"));
        validateMeta(request.getTitle(), request.getCategory());
        record.setTitle(request.getTitle().trim());
        record.setCategory(StringUtils.hasText(request.getCategory()) ? request.getCategory().trim() : "未分类");
        record.setDescription(trimToNull(request.getDescription()));
        record.setUpdateTime(now());
        return metaStore.update(record);
    }

    public void delete(Long id) throws IOException {
        metaStore.findById(id).orElseThrow(() -> new GlobalException("档案不存在"));
        deleteFileDir(id);
        metaStore.deleteById(id);
    }

    public ArchiveRecord uploadFile(Long id, MultipartFile file) throws IOException {
        ArchiveRecord record = metaStore.findById(id).orElseThrow(() -> new GlobalException("档案不存在"));
        String originalName = file.getOriginalFilename();
        if (!StringUtils.hasText(originalName)) {
            throw new GlobalException("文件名为空");
        }
        String contentType = guessContentType(file.getContentType(), originalName);
        String storedName = UUID.randomUUID().toString().replace("-", "") + extOf(originalName);
        Path target = filePath(id, storedName);
        deleteExistingFile(record);
        Files.createDirectories(target.getParent());
        file.transferTo(target);
        long size = Files.size(target);
        if (size > properties.getMaxFileSize()) {
            Files.deleteIfExists(target);
            throw new GlobalException("文件超过大小限制 " + (properties.getMaxFileSize() / 1024 / 1024) + "MB");
        }
        record.setOriginalFileName(originalName);
        record.setStoredFileName(storedName);
        record.setContentType(contentType);
        record.setFileSize(size);
        record.setFileType(contentType.startsWith("image/") ? "IMAGE" : "FILE");
        record.setUpdateTime(now());
        return metaStore.update(record);
    }

    public Resource loadFileResource(Long id) throws IOException {
        ArchiveRecord record = metaStore.findById(id).orElseThrow(() -> new GlobalException("档案不存在"));
        if (!StringUtils.hasText(record.getStoredFileName())) {
            throw new GlobalException("尚未上传文件");
        }
        Path path = filePath(id, record.getStoredFileName());
        if (!Files.exists(path)) {
            throw new GlobalException("文件不存在或已被删除");
        }
        return new FileSystemResource(path);
    }

    public String rootDirPath() {
        return rootDir.toString();
    }

    private Path filePath(Long id, String storedName) {
        return filesDir.resolve(String.valueOf(id)).resolve(storedName);
    }

    private void deleteFileDir(Long id) throws IOException {
        Path dir = filesDir.resolve(String.valueOf(id));
        if (Files.exists(dir)) {
            try (Stream<Path> walk = Files.walk(dir)) {
                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ignored) {
                    }
                });
            }
        }
    }

    private void deleteExistingFile(ArchiveRecord record) throws IOException {
        if (StringUtils.hasText(record.getStoredFileName())) {
            Files.deleteIfExists(filePath(record.getId(), record.getStoredFileName()));
        }
    }

    private static void validateMeta(String title, String category) {
        if (!StringUtils.hasText(title)) {
            throw new GlobalException("档案标题不能为空");
        }
    }

    private static String trimToNull(String s) {
        if (!StringUtils.hasText(s)) {
            return null;
        }
        return s.trim();
    }

    private static String now() {
        return LocalDateTime.now().format(DT);
    }

    private static String extOf(String filename) {
        int i = filename.lastIndexOf('.');
        if (i < 0) {
            return "";
        }
        return filename.substring(i);
    }

    private static String guessContentType(String headerType, String filename) {
        if (StringUtils.hasText(headerType)) {
            return headerType;
        }
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png")) {
            return "image/png";
        }
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (lower.endsWith(".gif")) {
            return "image/gif";
        }
        if (lower.endsWith(".webp")) {
            return "image/webp";
        }
        if (lower.endsWith(".pdf")) {
            return "application/pdf";
        }
        return "application/octet-stream";
    }
}
