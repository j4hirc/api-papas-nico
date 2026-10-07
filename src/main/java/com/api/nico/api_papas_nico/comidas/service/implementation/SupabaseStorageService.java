package com.api.nico.api_papas_nico.comidas.service.implementation;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SupabaseStorageService {

    private static final Logger log =
            LoggerFactory.getLogger(SupabaseStorageService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    public String uploadFile(
            MultipartFile file,
            String folder
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selecciona una fotografía."
            );
        }

        if (file.getSize() > 5L * 1024 * 1024) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fotografía no debe superar los 5 MB."
            );
        }

        String contentType = file.getContentType();

        if (contentType == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se pudo identificar el formato de la imagen."
            );
        }

        String extension = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Utiliza una imagen JPG, PNG o WebP."
            );
        };

        String key = supabaseKey.trim();

        if (!key.startsWith("sb_secret_")) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Configura una clave secreta de Supabase "
                            + "en SUPABASE_KEY del backend."
            );
        }

        String baseUrl = supabaseUrl.trim().replaceAll("/+$", "");
        String bucket = bucketName.trim();

        String objectPath =
                folder + "/" + UUID.randomUUID() + extension;

        String uploadUrl =
                baseUrl
                        + "/storage/v1/object/"
                        + bucket
                        + "/"
                        + objectPath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", key);
        headers.setContentType(MediaType.parseMediaType(contentType));

        HttpEntity<byte[]> request =
                new HttpEntity<>(file.getBytes(), headers);

        try {
            restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (RestClientResponseException e) {
            log.error(
                    "Supabase Storage rechazó la subida. HTTP={}, bucket={}",
                    e.getStatusCode().value(),
                    bucket
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Supabase rechazó la fotografía. "
                            + "Revisa la clave del servidor, el bucket "
                            + "y sus restricciones de archivos."
            );
        } catch (RestClientException e) {
            log.error(
                    "No se pudo conectar con Supabase Storage. Tipo={}",
                    e.getClass().getSimpleName()
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "No se pudo conectar con el almacenamiento de imágenes."
            );
        }

        return baseUrl
                + "/storage/v1/object/public/"
                + bucket
                + "/"
                + objectPath;
    }
}