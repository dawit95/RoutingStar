package com.curation.backend.comment.exception;

public class NoCommentException extends Exception{
    public NoCommentException() {
        super("없는 댓글입니다.");
    }
}
