package com.ukm.ssgb.scheduler;

import com.ukm.ssgb.service.NicknameService;
import com.ukm.ssgb.service.WebHookService;
import com.ukm.ssgb.type.BatchType;
import com.ukm.ssgb.util.StackTraceUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final WebHookService webHookService;
    private final NicknameService nicknameService;

    @Scheduled(cron = "0 0 5 1 * ?")
    public void deleteNickname() {
        sendFunction(nicknameService::deleteNicknameCount, BatchType.DELETE_NICKNAME_COUNT);
    }

    void sendFunction(FailableRunnable<Exception> failableRunnable, BatchType batchType) {
        LocalDateTime now = LocalDateTime.now();
        try {
            // TODO : 락 설정 필요
            failableRunnable.run();
            send(true, now, batchType, null);
        } catch (Throwable e) {
            send(false, now, batchType, e);
        }
    }

    void send(boolean success, LocalDateTime now, BatchType batchType, Throwable e) {
        String title = String.format("%s (소톡) %s - %s",
                getTextBySuccess(success),
                now.toLocalDate(),
                batchType.getSubject());

        String contents = title + System.lineSeparator() + StackTraceUtil.getPrintStackTrace(e);

        webHookService.sendMessage(contents);
    }

    private String getTextBySuccess(boolean success) {
        return success ? "✅ 성공" : "⛔ 실패";
    }
}
