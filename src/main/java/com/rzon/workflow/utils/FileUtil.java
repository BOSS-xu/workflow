package com.rzon.workflow.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public final class FileUtil {

    private FileUtil() {
    }

    public static ResponseEntity<byte[]> downloadFile(final String fileName, final byte[] bytes)
            throws UnsupportedEncodingException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        final String encodeFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + encodeFileName + ";filename*=UTF-8''" + encodeFileName);

        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    public static ResponseEntity<InputStreamResource> downloadFileResource(final InputStreamResource resource)
            throws UnsupportedEncodingException {
        final HttpHeaders headers = new HttpHeaders();
        final String encodeFileName = URLEncoder
                .encode(Objects.requireNonNull(resource.getFilename()), "UTF-8")
                .replace("+", "%20");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "inline;filename=" + encodeFileName + ";filename*=UTF-8''" + encodeFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    public static Optional<Path> ensurePath(final Path ensuringPath) {
        if (Files.exists(ensuringPath) && Files.isDirectory(ensuringPath)) {
            return Optional.of(ensuringPath);
        }

        try {
            Files.createDirectories(ensuringPath);
            return Optional.of(ensuringPath);
        } catch (IOException e) {
            log.error("ensure path error, path: {}", ensuringPath);
            return Optional.empty();
        }
    }
}
