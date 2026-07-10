package com.letit.letit.StoreMedia;

import com.letit.letit.Entity.MultiMedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface SaveMedia {

    MultiMedia save(MultipartFile file, String postId) throws IOException;

    void delete(String mediaId) throws IOException;}

