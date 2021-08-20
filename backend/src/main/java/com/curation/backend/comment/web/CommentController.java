package com.curation.backend.comment.web;

import com.curation.backend.comment.dto.CommentModifyRequestDto;
import com.curation.backend.comment.dto.CommentRequestDto;
import com.curation.backend.comment.dto.CommentResponseDto;
import com.curation.backend.comment.exception.NoCommentException;
import com.curation.backend.comment.service.CommentService;
import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<SuccessResponseDto> modifyComment(@PathVariable("commentId") Long id, @RequestBody CommentModifyRequestDto commentModifyRequestDto) throws NoCommentException {
        CommentResponseDto commentResponseDto = commentService.modifyComment(id, commentModifyRequestDto);
        HttpStatus status = HttpStatus.OK;

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(commentResponseDto);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<SuccessResponseDto> deleteComment(@PathVariable("commentId") Long id) throws NoCommentException {
        commentService.deleteComment(id);
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse("성공적으로 삭제되었습니다.");
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @ExceptionHandler(NoCommentException.class)
    public ResponseEntity<ExceptionResponseDto> handleNoComment() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = "없는 댓글입니다.";

        ExceptionResponseDto exceptionResponseDto = responseGenerateService.generateExceptionResponse(httpStatus, message);
        return new ResponseEntity<ExceptionResponseDto>(exceptionResponseDto, httpStatus);
    }

}
