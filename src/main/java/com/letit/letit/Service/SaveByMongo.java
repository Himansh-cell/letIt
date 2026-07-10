package com.letit.letit.Service;

import com.letit.letit.Entity.MultiMedia;
import com.letit.letit.Repo.MultiMediaRepository;
import com.letit.letit.StoreMedia.SaveMedia;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.letit.letit.Entity.MongoMediaFile;
import com.letit.letit.Repo.MongoMediaFileRepository;
import com.letit.letit.Entity.Post;
import com.letit.letit.Repo.PostRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "media.storage", havingValue = "mongo")
public class SaveByMongo implements SaveMedia {

    private final MultiMediaRepository mediaRepository;
    private final MongoMediaFileRepository mongoMediaFileRepository;
    private final PostRepo postRepo;

    @Override
    public MultiMedia save(MultipartFile file, String postId) throws IOException {
        MongoMediaFile mongoFile = new MongoMediaFile();
        mongoFile.setFilename(file.getOriginalFilename());
        mongoFile.setContentType(file.getContentType());
        mongoFile.setSize(file.getSize());
        mongoFile.setData(file.getBytes());
        mongoFile.setUploadedAt(LocalDateTime.now());
        
        MongoMediaFile savedMongoFile = mongoMediaFileRepository.save(mongoFile);

        MultiMedia multiMedia = new MultiMedia();
        multiMedia.setMongoFileId(savedMongoFile.getId());
        multiMedia.setCreationDate(LocalDate.now());
        multiMedia.setSize(String.valueOf(file.getSize()));
        multiMedia.setType(file.getContentType());
        
        Optional<Post> postOpt = postRepo.findById(postId);
        postOpt.ifPresent(multiMedia::setPost);
        
        return mediaRepository.save(multiMedia);
    }

    @Override
    public void delete(String mediaId) throws IOException {
        Optional<MultiMedia> multiMediaOpt = mediaRepository.findById(mediaId);
        if (multiMediaOpt.isPresent()) {
            MultiMedia multiMedia = multiMediaOpt.get();
            if (multiMedia.getMongoFileId() != null) {
                mongoMediaFileRepository.deleteById(multiMedia.getMongoFileId());
            }
            mediaRepository.delete(multiMedia);
        }
    }
}