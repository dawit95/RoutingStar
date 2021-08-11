package com.curation.backend.user.web;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.dto.FFResponseDto;
import com.curation.backend.user.exception.NoUserException;
import com.curation.backend.user.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
@RestController
public class ReactionController {
    Logger logger = LoggerFactory.getLogger(ReactionController.class);

    private final ReactionService reactionService;
    private final ResponseGenerateService responseGenerateService;

    @GetMapping("/like/{userId}/{routeId}")
    public ResponseEntity<SuccessResponseDto> setLikeReaction(@PathVariable Long userId, @PathVariable Long routeId) throws NoUserException, NoRouteException {

        String message = reactionService.setLike(userId, routeId);

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/store/{userId}/{routeId}")
    public ResponseEntity<SuccessResponseDto> setStoreReaction(@PathVariable Long userId, @PathVariable Long routeId) throws NoUserException, NoRouteException {

        String message = reactionService.setStore(userId, routeId);

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/followlist/{userId}")
    public ResponseEntity<SuccessResponseDto> countOfFollow(@PathVariable Long userId) throws NoUserException, NoRouteException {

        FFResponseDto ffResponseDto =  reactionService.countOfFollow(userId);

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(ffResponseDto);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/follow/{userId}/{targetId}")
    public ResponseEntity<SuccessResponseDto> setFollowReaction(@PathVariable Long userId,@PathVariable Long targetId) throws NoUserException, NoRouteException {

        String message = reactionService.setFollow(userId, targetId);

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

}
