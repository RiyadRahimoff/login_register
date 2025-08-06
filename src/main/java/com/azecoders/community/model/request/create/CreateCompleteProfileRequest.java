package com.azecoders.community.model.request.create;

import com.azecoders.community.model.enums.Profession;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCompleteProfileRequest {
    String bio;
    Profession profession;
}
