package com.example.demo.image;

import com.example.demo.image.dto.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Duration;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frontendvue-homeat.onrender.com"
})
@RestController
@RequestMapping("/api")
public class ImageController {

    private final RecipeImageService imageService;

    public ImageController(RecipeImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "recipeId", required = false) Long recipeId
    ) {
        RecipeImage saved = (recipeId == null)
                ? imageService.save(file)
                : imageService.saveForRecipe(recipeId, file);

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/{id}")
                .buildAndExpand(saved.getId())
                .toUriString();

        return ResponseEntity.ok(
                new ImageResponse(
                        saved.getId(),
                        url,
                        saved.getFilename(),
                        saved.getContentType(),
                        saved.getSize()
                )
        );
    }

    // важливо: транзакція активна у момент доступу до LAZY поля data
    @Transactional(readOnly = true)
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> serve(@PathVariable Long id) {
        RecipeImage img = imageService.get(id);

        String cacheValue = "max-age=" + Duration.ofDays(30).getSeconds() + ", no-transform";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + (img.getFilename() == null
                                ? ("image-" + id)
                                : img.getFilename()) + "\"")
                .header(HttpHeaders.CACHE_CONTROL, cacheValue)
                .contentType(MediaType.parseMediaType(img.getContentType()))
                .body(img.getData());  // ← тепер без LazyInitializationException
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
