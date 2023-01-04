package com.example.securdemo.service;

import com.example.securdemo.dto.OwnerDto;
import com.example.securdemo.mapper.OwnerMapper;
import com.example.securdemo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public Page<OwnerDto> findAll(final Pageable pageable) {
        return this.ownerRepository.findAll(pageable).map(this.ownerMapper::toDto);
    }

    public List<OwnerDto> findAll() {
        return this.ownerRepository.findAll().stream().map(this.ownerMapper::toDto).toList();
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER_OWNER')")
//    @PostAuthorize("returnObject")
    public Optional<OwnerDto> findById(final Long id) {
        return this.ownerRepository.findById(id).map(this.ownerMapper::toDto);
    }

    @Transactional
    public OwnerDto create(final OwnerDto userDto) {
        return Optional.of(userDto)
                .map(this.ownerMapper::toEntity)
                .map(this.ownerRepository::save)
                .map(this.ownerMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<OwnerDto> update(final Long id, final OwnerDto ownerDto) {
        return this.ownerRepository.findById(id)
                .map(entity -> this.ownerMapper.partialUpdate(ownerDto, entity))
                .map(this.ownerRepository::saveAndFlush)
                .map(this.ownerMapper::toDto);
    }

    @Transactional
    public boolean delete(final Long id) {
        return this.ownerRepository.findById(id)
                .map(entity -> {
                    this.ownerRepository.delete(entity);
                    this.ownerRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}

