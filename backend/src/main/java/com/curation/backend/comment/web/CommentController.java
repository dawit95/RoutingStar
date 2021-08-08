package com.curation.backend.comment.web;

import com.curation.backend.comment.dto.CommentRequestDto;
import com.curation.backend.comment.dto.CommentResponseDto;
import com.curation.backend.comment.service.CommentService;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final ResponseGenerateService responseGenerateService;

    @PostMapping("/comment/{routeId}")
    public ResponseEntity<SuccessResponseDto> saveComment(@PathVariable("routeId") Long id, @RequestBody CommentRequestDto commentRequestDto) throws NoUserException, NoRouteException {
        CommentResponseDto commentResponseDto = commentService.saveComment(id, commentRequestDto);
        HttpStatus status = HttpStatus.OK;

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(commentResponseDto);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

}
