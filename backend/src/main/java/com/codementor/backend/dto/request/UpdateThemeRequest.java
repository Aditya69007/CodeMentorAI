package com.codementor.backend.dto.request;

import com.codementor.backend.entity.ThemePreference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateThemeRequest {

    @NotNull(message = "Theme preference is required")
    private ThemePreference themePreference;

}