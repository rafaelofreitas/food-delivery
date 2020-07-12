package br.com.fooddelivery.api.dto.entry;

import br.com.fooddelivery.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PhotoProductEntry {
    @NotNull
    @FileSize(max = "500KB")
    private MultipartFile file;

    @NotBlank
    private String description;
}
