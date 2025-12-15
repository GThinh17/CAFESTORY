package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningEventResponse;

public interface EarningEventService {
    EarningEventResponse create(EarningEventCreateDTO earningEventCreateDTO);
    void deleteLikeEvent(String likeId);
    void deleteCommentEvent(String commentId);
    void deleteShareEvent(String shareId);
}

