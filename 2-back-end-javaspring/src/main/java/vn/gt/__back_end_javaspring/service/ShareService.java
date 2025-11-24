package vn.gt.__back_end_javaspring.service;


import vn.gt.__back_end_javaspring.DTO.ShareCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareReponse;

import java.util.List;

public interface ShareService {
    ShareReponse createShare(ShareCreateDTO dto);

    ShareReponse getShareById(String shareId);

    List<ShareReponse> getSharesByBlog(String blogId);

    List<ShareReponse> getSharesByUser(String userId);

    void softDeleteShare(String shareId, String userId);
}
