package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.ShareRequestDTO;
import vn.gt.__back_end_javaspring.DTO.ShareResponseDTO;

public interface ShareService {

    ShareResponseDTO createShare(ShareRequestDTO request);

    boolean hasShared(String userId, String blogId);
}
