package com.xdwolf.airlineticket.converter;


import com.xdwolf.airlineticket.dto.UserDTO;
import com.xdwolf.airlineticket.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDTO convertToDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setUsername(entity.getUsername());
        dto.setFullName(entity.getFullname());
        dto.setPassword(null);
        dto.setConfirmPassword(null);
        dto.setGender(entity.getGender());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<UserDTO> convertToDTOList(List<UserEntity> entity) {
        return entity.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserEntity convertToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setFullname(dto.getFullName());
        entity.setPassword(dto.getPassword());
        entity.setGender(dto.getGender());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }
}
