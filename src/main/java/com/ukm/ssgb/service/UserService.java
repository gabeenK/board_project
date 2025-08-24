package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.NicknameDto;
import com.ukm.ssgb.dto.usermanagement.UpdateUserRequestDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.BusinessType;
import com.ukm.ssgb.model.FileEntity;
import com.ukm.ssgb.model.Region;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.BusinessTypeRepository;
import com.ukm.ssgb.repository.RegionRepository;
import com.ukm.ssgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ukm.ssgb.type.FolderType.BUSINESS_REGISTRATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final BusinessTypeRepository businessTypeRepository;

    private final NicknameService nicknameService;
    private final FileService fileService;

    public void validateEmailDuplication(String email) {
        boolean exists = userRepository.existsByEmailAndDeletedFalse(email);
        if (exists) {
            throw new ValidationException("이미 등록된 이메일 주소입니다.");
        }
    }

    @Transactional
    public void registerUser(String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        NicknameDto nickname = nicknameService.getRandomNickname();
        User user = userRepository.save(User.registerUser(email, encodedPassword, nickname.getNickname()));
        nicknameService.active(user.getUserId(), nickname.getNicknameId());
    }

    @Transactional
    public void registerAdmin(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        NicknameDto nickname = nicknameService.getRandomNickname();
        User user = userRepository.save(User.registerAdmin(email, encodedPassword, nickname.getNickname()));
        nicknameService.active(user.getUserId(), nickname.getNicknameId());
    }

    @Transactional
    public void registerBusinessFile(long userId, MultipartFile businessFile) {
        FileEntity fileEntity = fileService.save(BUSINESS_REGISTRATION, businessFile);
        User user = userRepository.findById(userId).orElseThrow();
        user.setBusinessFileId(fileEntity.getFileId());
    }

    @Transactional
    public void registerProfile(long userId, long regionId, long businessTypeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Region region = regionRepository.findById(regionId).orElseThrow();
        BusinessType businessType = businessTypeRepository.findById(businessTypeId).orElseThrow();

        user.setRegionId(region.getRegionId());
        user.setBusinessTypeId(businessType.getBusinessTypeId());
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        users.forEach(this::deleteUser);
    }

    private void deleteUser(User user) {
        user.delete();
        nicknameService.deleteByUserIdAndInsertAsInactive(user.getUserId());
    }

    @Transactional
    public void approveUsers(List<Long> userIds) {
        if (userIds.isEmpty()) {
            return;
        }

        List<User> users = userRepository.findAllByIdAndApprovedFalseAndDeletedFalse(userIds);
        users.forEach(User::approve);
    }

    @Transactional
    public void updateUserByAdmin(Long userId, UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setApproved(requestDto.getApproved());
    }
}
