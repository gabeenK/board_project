package com.ukm.ssgb.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceConstants {
    // 게시판
    public static final int CONTENT_MAX_LENGTH = 200; // 목록의 각 게시물 내용 표시 사이즈
    public static final int CLAIM_MAX_COUNT = 20; // 누적 신고 최대 허용 횟수

    // 닉네임
    public static final int NICKNAME_MAX_COUNT = 5; // 닉네임 변경 최대 횟수
    public static final String DELETED_USER_NICKNAME = "삭제된 유저";

    // 인증 번호
    public static final long CERT_NO_VALIDATE_TIME = 1000 * 60 * 10; // 인증번호 유효시간 10분

    // 게시판
    public static final String DELETED_BOARD_TITLE = "삭제된 게시글입니다.";
    public static final String DELETED_BOARD_CONTENT = "삭제된 게시글입니다.";
    public static final String DELETED_BOARD_CONTENT_BY_ADMIN = "관리자에 의해 삭제된 게시글입니다.";
    public static final String HIDDEN_BOARD_TITLE = "누적 신고된 게시물입니다.";
    public static final String HIDDEN_BOARD_CONTENT = "누적 신고로 인해 숨김 처리된 게시글입니다.";
    public static final int MAX_DELETE_BOARD_SIZE = 15;

    // 댓글
    public static final String DELETED_COMMENT_CONTENT = "삭제된 댓글입니다.";
    public static final String DELETED_COMMENT_CONTENT_BY_ADMIN = "관리자에 의해 삭제된 댓글입니다.";
    public static final String HIDDEN_COMMENT_CONTENT = "누적 신고로 인해 숨김 처리된 댓글입니다.";
    public static final int MAX_DELETE_COMMENT_SIZE = 15;

    // 첨부 파일
    public static final int MAX_ATTACHMENT_FILE_SIZE = 1;

    // 회원 관리
    public static final int MAX_DELETE_USER_SIZE = 15;
    public static final int MAX_APPROVE_USER_SIZE = 15;

    // 베너/이벤트
    public static final LocalDateTime ALWAYS_END_DATE = LocalDateTime.of(2099, 1, 1, 0, 0, 0);
    public static final LocalDateTime ALWAYS_START_DATE = LocalDateTime.of(1, 1, 1, 0, 0, 0);

    // 게시글 관리
    public static final String DELETED_BY_USER_DISPLAY_NAME = "작성자 삭제";
    public static final String DELETED_BY_ADMIN_DISPLAY_NAME = "관리자 삭제";
}