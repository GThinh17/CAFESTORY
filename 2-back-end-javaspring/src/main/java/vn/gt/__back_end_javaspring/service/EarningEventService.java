package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningEventResponse;
import vn.gt.__back_end_javaspring.entity.EarningEvent;

import java.util.List;

public interface EarningEventService {
    EarningEventResponse create(EarningEventCreateDTO earningEventCreateDTO);
    void deleteLikeEvent(String likeId);
    void deleteCommentEvent(String commentId);
    void deleteShareEvent(String shareId);

}

