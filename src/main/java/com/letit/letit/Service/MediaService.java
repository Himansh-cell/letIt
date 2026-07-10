package com.letit.letit.Service;


import com.letit.letit.Entity.MultiMedia;
import com.letit.letit.StoreMedia.SaveMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final SaveMedia saveMedia;
    public MultiMedia upload(String postId, MultipartFile file) throws IOException {
        return saveMedia.save(file, postId);
    }

    public void delete(String mediaId) throws IOException {
        saveMedia.delete(mediaId);
    }
}
