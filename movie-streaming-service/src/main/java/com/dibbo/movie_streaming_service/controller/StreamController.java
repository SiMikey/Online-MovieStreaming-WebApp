package com.dibbo.movie_streaming_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class StreamController {

    @Autowired
    private MovieCatalogService movieCatalogService;
    public static final Logger log = Logger.getLogger(StreamController.class.getName());

    private static final String VIDEO_DIRECTORY = "C:\\temp\\All\\";

    @GetMapping("/stream/{videopath}")
    public ResponseEntity<InputStreamResource> streamVideo(
            @PathVariable String videopath,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

        Path filePath = Paths.get(VIDEO_DIRECTORY, videopath).normalize();
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }

        long fileSize = file.length();
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        String contentType = "video/x-matroska"; // Set content type for MKV files

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");

        // Handle range requests for streaming
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            try {
                String[] ranges = rangeHeader.substring(6).split("-");
                long start = Long.parseLong(ranges[0]);
                long end = (ranges.length > 1 && !ranges[1].isEmpty()) ? Long.parseLong(ranges[1]) : fileSize - 1;
                long contentLength = end - start + 1;

                inputStream.skip(start); // Skip to the requested position

                headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);
                headers.setContentLength(contentLength);

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .contentType(MediaType.parseMediaType(contentType))
                        .headers(headers)
                        .body(new InputStreamResource(inputStream));

            } catch (NumberFormatException e) {
                log.log(Level.WARNING, "Invalid Range Header", e);
            }
        }

        // Return full file if no range is requested
        headers.setContentLength(fileSize);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(inputStreamResource);
    }


    @GetMapping("/check/{videopath}")
    public ResponseEntity<String> checkFile(@PathVariable String videopath) {
        Path filePath = Paths.get(VIDEO_DIRECTORY, videopath).normalize();
        File file = filePath.toFile();

        System.out.println("🛠 VIDEO_DIRECTORY: " + VIDEO_DIRECTORY);
        System.out.println("🔍 Full File Path: " + file.getAbsolutePath());

        if (file.exists()) {
            return ResponseEntity.ok("✅ File found: " + file.getAbsolutePath());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ File not found at: " + file.getAbsolutePath());
        }
    }



    @GetMapping("/stream/with-id/{videoInfoId}")
    public ResponseEntity<InputStreamResource> streamVideoById(
            @PathVariable Long videoInfoId,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

        String moviePath = movieCatalogService.getMoviePath(videoInfoId);
        log.log(Level.INFO,"Resolved Movie Path ={0}", moviePath);
        return streamVideo(moviePath, rangeHeader);
    }



}
