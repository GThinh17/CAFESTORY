package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.BadgeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.DTO.BadgeUpdateDTO;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.BadgeService;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @PostMapping("")
    public RestResponse<BadgeResponse> createBadge(
            @Valid @RequestBody BadgeCreateDTO badgeCreateDTO
    ) {
        BadgeResponse data = badgeService.createBadge(badgeCreateDTO);

        RestResponse<BadgeResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Create badge successfully");
        res.setData(data);

        return res;
    }
    @PatchMapping("/{id}")
    public RestResponse<BadgeResponse> updateBadge(
            @PathVariable("id") String badgeId,
            @Valid @RequestBody BadgeUpdateDTO badgeUpdateDTO
    ) {
        BadgeResponse data = badgeService.updateBadge(badgeId, badgeUpdateDTO);

        RestResponse<BadgeResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Update badge successfully");
        res.setData(data);

        return res;
    }
    @DeleteMapping("/{id}")
    public RestResponse<Object> deleteBadge(
            @PathVariable("id") String badgeId
    ) {
        badgeService.softDelete(badgeId);

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Delete badge successfully");
        res.setData(null);

        return res;
    }
    @GetMapping("/{id}")
    public RestResponse<BadgeResponse> getBadgeById(
            @PathVariable("id") String badgeId
    ) {
        BadgeResponse data = badgeService.getBadgeById(badgeId);

        RestResponse<BadgeResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get badge successfully");
        res.setData(data);

        return res;
    }

    @GetMapping("")
    public RestResponse<List<BadgeResponse>> getAllBadges(
            @RequestParam(name = "includeDeleted", defaultValue = "false") boolean includeDeleted
    ) {
        List<BadgeResponse> data = badgeService.getAllBadges(includeDeleted);

        RestResponse<List<BadgeResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get badge list successfully");
        res.setData(data);

        return res;
    }
}
