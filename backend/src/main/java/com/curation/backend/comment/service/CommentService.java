package com.curation.backend.comment.service;

import com.curation.backend.comment.domain.Comment;
import com.curation.backend.comment.domain.CommentRepository;
import com.curation.backend.comment.dto.CommentRequestDto;
import com.curation.backend.comment.dto.CommentResponseDto;
import com.curation.backend.place.domain.Place;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public CommentResponseDto saveComment(Long id, CommentRequestDto commentRequestDto) throws NoUserException, NoRouteException {
        Optional<User> user = Optional.ofNullable(userRepository.findById(commentRequestDto.getId()).orElseThrow(() -> new NoUserException("존재하지 않는 사용자입니다.")));
        Optional<Route> route = Optional.ofNullable(routeRepository.findById(id).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다.")));


        Comment comment = commentRequestDto.toEntity();
//        comment.setUser(user.get());
        comment.setRoute(route.get());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
