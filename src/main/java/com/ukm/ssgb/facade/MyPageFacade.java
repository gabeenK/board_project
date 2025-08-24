package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.MyPageProfileDecorator;
import com.ukm.ssgb.decorator.filler.FilePathFiller;
import com.ukm.ssgb.decorator.filler.NicknameGenerationRemainingFiller;
import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.myPage.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.BusinessType;
import com.ukm.ssgb.model.FileEntity;
import com.ukm.ssgb.model.Region;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.BoardRepository;
import com.ukm.ssgb.repository.BusinessTypeRepository;
import com.ukm.ssgb.repository.RegionRepository;
import com.ukm.ssgb.repository.UserRepository;
import com.ukm.ssgb.service.CacheService;
import com.ukm.ssgb.service.CertNoService;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.service.NicknameService;
import com.ukm.ssgb.type.LocalCacheType;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ukm.ssgb.type.FolderType.PROFILE;
import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;

@Service
@RequiredArgsConstructor
public class MyPageFacade {

    private final FileService fileService;
    private final NicknameService nicknameService;
    private final CacheService cacheService;
    private final CertNoService certNoService;

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final BusinessTypeRepository businessTypeRepository;
    private final BoardRepository boardRepository;

    private final MyPageProfileDecorator myPageProfileDecorator;
    private final FilePathFiller filePathFiller;
    private final NicknameGenerationRemainingFiller nicknameGenerationRemainingFiller;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MyPageProfileDto getProfile() {
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        MyPageProfileDto myPageProfileDto = MyPageProfileDto.toDto(user);
        myPageProfileDecorator.decorate(myPageProfileDto);

        return myPageProfileDto;
    }

    @Transactional
    public MyPageProfileImageDto updateProfileImage(MyPageProfileImageRequestDto requestDto) {
        FileEntity fileEntities = fileService.save(PROFILE, requestDto.getProfileImage());
        Long fileId = fileEntities.getFileId();

        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        user.setProfileImageId(fileId);

        MyPageProfileImageDto myPageProfileImageDto = MyPageProfileImageDto.toDto(user);
        filePathFiller.fill(myPageProfileImageDto);

        return myPageProfileImageDto;
    }

    @Transactional
    public void deleteProfileImage() {
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        user.deleteProfileImage();
    }

    @Transactional
    public MyPageNicknameDto updateNickname() {
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        nicknameService.deleteByUserIdAndInsertAsInactive(user.getUserId());
        nicknameService.updateUserNickname(user);

        MyPageNicknameDto myPageNicknameDto = MyPageNicknameDto.toDto(user);
        nicknameGenerationRemainingFiller.fill(myPageNicknameDto);

        return myPageNicknameDto;
    }

    @Transactional
    public void updateRegion(MyPageProfileRegionRequestDto requestDto) {
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        Region region = regionRepository.findById(requestDto.getRegionId()).orElseThrow();
        user.setRegionId(region.getRegionId());
    }

    @Transactional
    public void updateBusinessType(MyPageProfileBusinessTypeRequestDto requestDto) {
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        BusinessType businessType = businessTypeRepository.findById(requestDto.getBusinessTypeId()).orElseThrow();
        user.setBusinessTypeId(businessType.getBusinessTypeId());
    }

    public ExpiredAtDto sendPasswordMail() {
        return certNoService.sendCertNo(TokenUtil.getEmailWithThrow());
    }

    public void checkCertNo(PasswordMailCheckRequestDto requestDto) {
        String certNoToCheck = requestDto.getCertNo();

        // TODO 인증 시도 횟수, 횟수 초과 시 잠금 기능 필요
        String certNo = cacheService.getString(LocalCacheType.CERT_NO, TokenUtil.getEmailWithThrow())
                .orElseThrow(() -> new ValidationException("인증메일 재발송을 통해 새로운 인증번호를 받아주세요."));
        if (!certNoToCheck.equals(certNo)) {
            throw new ValidationException("인증번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void resetPassword(PasswordResetRequestDto requestDto) {
        if (!requestDto.getNewPassword().equals(requestDto.getConfirmPassword())) {
            throw new ValidationException("비밀번호가 다릅니다.");
        }
        User user = userRepository.findById(getIdWithThrow()).orElseThrow();
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    @Transactional(readOnly = true)
    public PageContentsDto<List<MyBoardListDto>> myBoardList(PageRequest pageRequest) {
        Page<MyBoardListDto> result = boardRepository.findMappedAllBoardAndCommentByUserId(getIdWithThrow(), pageRequest);
        return PageContentsDto.of(result.stream().toList(), result.getTotalElements());
    }
}
