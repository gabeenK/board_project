package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.filler.BusinessRegistrationFiller;
import com.ukm.ssgb.decorator.filler.BusinessTypeNameFiller;
import com.ukm.ssgb.decorator.filler.RegionNameFiller;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.usermanagement.*;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.UserRepository;
import com.ukm.ssgb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementFacade {

    private final UserService userService;

    private final RegionNameFiller regionNameFiller;
    private final BusinessTypeNameFiller businessTypeNameFiller;
    private final BusinessRegistrationFiller businessRegistrationFiller;

    private final UserRepository userRepository;

    public PageContentsDto<List<GetUsersDto>> getUsers(GetUsersRequestDto requestDto) {
        Page<GetUsersDto> result =
                userRepository.findAllByQueryAndApproved(requestDto.getQuery(), requestDto.getApproved(), requestDto.getPageRequest());
        return PageContentsDto.of(result.getContent(), result.getTotalElements());
    }

    public void deleteUsers(DeleteUsersRequestDto requestDto) {
        List<Long> userIds = requestDto.getUserIds();
        userService.deleteUsers(userIds);
    }

    public void approveUsers(ApproveUsersRequestDto requestDto) {
        List<Long> userIds = requestDto.getUserIds();
        userService.approveUsers(userIds);
    }

    public GetUserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        GetUserDto result = new GetUserDto(user);

        regionNameFiller.fill(result);
        businessTypeNameFiller.fill(result);
        businessRegistrationFiller.fill(result);

        return result;
    }

    public UpdateUserDto updateUser(Long userId, UpdateUserRequestDto requestDto) {
        userService.updateUserByAdmin(userId, requestDto);
        return new UpdateUserDto(userId);
    }
}
