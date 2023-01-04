package com.example.securdemo.http.rest;

import com.example.securdemo.dto.OwnerDto;
import com.example.securdemo.dto.PageResponse;
import com.example.securdemo.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping(
    path = "/api/v1/owners",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
//    public PageResponse<OwnerDto> findAll(final OwnerFilter filter, final Pageable pageable) {
    public PageResponse<OwnerDto> findAll(final Pageable pageable) {
        Page<OwnerDto> page = this.ownerService.findAll(pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public OwnerDto findById(@PathVariable("id") final Long id) {
        return this.ownerService.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public byte[] findAvatar(@PathVariable("id") Long id) {
//        return userService.findAvatar(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

//    @GetMapping(value = "/{id}/avatar")
//    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") final Long id) {
//        return this.ownerService.findAvatar(id)
//                .map(content -> ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                        .contentLength(content.length)
//                        .body(content))
//                .orElseGet(notFound()::build);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto create(@RequestBody final OwnerDto ownerDto) {
        return this.ownerService.create(ownerDto);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public OwnerDto update(@PathVariable("id") final Long id, @RequestBody final OwnerDto ownerDto) {
        return this.ownerService.update(id, ownerDto)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        return this.ownerService.delete(id) ? noContent().build() : notFound().build();
    }

}
