package com.ukm.ssgb.util;

import com.ukm.ssgb.model.Board;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BoardBestUtil {

    private static final double viewWeight = 1.0; // 조회수 가중치
    private static final double likeWeight = 2.0; // 좋아요 가중치
    private static final double timeDecayFactor = 1.5; // 시간 감쇄 정도
    private static final int timeCalculateUnit = 3; // 경과 시간 단위 (3시간 간격으로 시간 감쇄를 적용한다)

    public static double calculateScore(Board board) {
        if (board.getPin()) {
            return Double.MAX_VALUE;
        }

        // 기본 점수 계산
        double baseScore = board.getViewCount() * viewWeight + board.getLikeCount() * likeWeight;

        // 3시간 단위 경과 시간 계산
        long elapsedTime = (ChronoUnit.HOURS.between(board.getCreatedAt(), LocalDateTime.now())) / timeCalculateUnit;

        // 시간 감쇄 적용
        return baseScore / Math.pow(1 + elapsedTime, timeDecayFactor);
    }
}
