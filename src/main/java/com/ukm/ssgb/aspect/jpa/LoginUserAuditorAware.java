package com.ukm.ssgb.aspect.jpa;

import com.ukm.ssgb.dto.TokenDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.ukm.ssgb.util.TokenUtil.getUserInfo;

@Component
public class LoginUserAuditorAware implements AuditorAware<Long> {

    private static final Long SYSTEM_ID = -1L;


    @Override
    public Optional<Long> getCurrentAuditor() {
        TokenDto userInfo = getUserInfo();
        if (userInfo == null) {
            return Optional.of(SYSTEM_ID);
        }

        return Optional.of(userInfo.getUserId());
    }
}
