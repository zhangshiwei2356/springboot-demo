package com.demo.business.controller;

import com.demo.business.dto.ArchiveRequest;
import com.demo.business.entity.ArchiveRecord;
import com.demo.business.service.impl.ArchiveService;
import com.demo.common.domain.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 档案管理 API（文件落盘在 demo.business.archive.upload-dir）。
 */
@RestController
@RequestMapping("/api/archives")
@Tag(name = "档案管理")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @GetMapping("/storage-path")
    public Result<String> storagePath() {
        return Result.ok(archiveService.rootDirPath());
    }

    @GetMapping
    public Result<List<ArchiveRecord>> list() throws Exception {
        return Result.ok(archiveService.list());
    }

    @GetMapping("/{id}")
    public Result<ArchiveRecord> get(@PathVariable Long id) throws Exception {
        return Result.ok(archiveService.get(id));
    }

    @PostMapping
    public Result<ArchiveRecord> create(@RequestBody ArchiveRequest request) throws Exception {
        return Result.ok(archiveService.create(request));
    }

    @PutMapping("/{id}")
    public Result<ArchiveRecord> update(@PathVariable Long id, @RequestBody ArchiveRequest request) throws Exception {
        return Result.ok(archiveService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) throws Exception {
        archiveService.delete(id);
        return Result.ok();
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<ArchiveRecord> upload(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws Exception {
        return Result.ok(archiveService.uploadFile(id, file));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception {
        ArchiveRecord record = archiveService.get(id);
        Resource resource = archiveService.loadFileResource(id);
        String filename = record.getOriginalFileName() != null ? record.getOriginalFileName() : "download";
        filename = filename.replace("\\", "_").replace("/", "_");
        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        String contentType = record.getContentType() != null
                ? record.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
