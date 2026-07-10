package com.letit.letit.Controller;

import com.letit.letit.Entity.MongoMediaFile;
import com.letit.letit.Entity.MultiMedia;
import com.letit.letit.Repo.MongoMediaFileRepository;
import com.letit.letit.Repo.MultiMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MongoMediaFileRepository mongoMediaFileRepository;

    @Autowired
    private MultiMediaRepository multiMediaRepository;

    @GetMapping("/{mediaId}")
    public ResponseEntity<byte[]> getMedia(@PathVariable String mediaId) {
        MultiMedia media = multiMediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        MongoMediaFile file = mongoMediaFileRepository.findById(media.getMongoFileId())
                .orElseThrow(() -> new RuntimeException("Media not found"));

        MediaType mediaType = file.getContentType() != null
                ? MediaType.parseMediaType(file.getContentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(mediaType)
                .body(file.getData());
    }
}
