package com.azecoders.community.model.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateCompletePhotoRequest {
    @Schema(type = "string", format = "binary")
    MultipartFile profilePhoto;
}
