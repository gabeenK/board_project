package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.UserDetailDecorator;
import com.ukm.ssgb.decorator.filler.FilePathFiller;
import com.ukm.ssgb.dto.user.UserDetailDto;
import com.ukm.ssgb.dto.user.UserInfoDto;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.UserRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserDetailDecorator userDetailDecorator;
    private final FilePathFiller filePathFiller;

    public UserDetailDto getUserInfo() {
        User user = userRepository.findById(TokenUtil.getIdWithThrow()).orElseThrow();
        UserDetailDto result = UserDetailDto.toDto(user);
        userDetailDecorator.decorate(result);
        return result;
    }

    public UserInfoDto getUserInfoById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        UserInfoDto result = UserInfoDto.toDto(user);
        filePathFiller.fill(result);
        return result;
    }
}
