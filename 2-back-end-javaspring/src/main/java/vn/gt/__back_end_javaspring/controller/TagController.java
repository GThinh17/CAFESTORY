package vn.gt.__back_end_javaspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.TagDTO;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.entity.Tag;
import vn.gt.__back_end_javaspring.service.TagService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<?> GetAllTag() {
        return ResponseEntity.ok().body(this.tagService.GetAllTag());
    }

    @PostMapping("")
    public ResponseEntity<?> CreateTag(@RequestBody TagDTO tagDTO) {

        return ResponseEntity.ok().body(this.tagService.CreateTag(tagDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetTagById(@PathVariable("id") String id) {
        Tag tag = this.tagService.GetTagById(id);
        return ResponseEntity.ok().body(tag);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> UpdateTag(@PathVariable("id") String id, @RequestBody TagDTO tagDTO) {

        return ResponseEntity.ok().body(this.tagService.UpdateTag(id, tagDTO));
    }

}
