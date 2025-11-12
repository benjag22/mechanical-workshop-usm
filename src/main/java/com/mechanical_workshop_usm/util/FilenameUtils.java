package com.mechanical_workshop_usm.util;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Utilidades simples para nombres de archivo.
 */
public final class FilenameUtils {

    private FilenameUtils() {}

    public static String getExtension(String filename) {
        if (filename == null) return "";
        int i = filename.lastIndexOf('.');
        if (i < 0) return "";
        return filename.substring(i + 1);
    }

    public static String getBaseName(String filename) {
        if (filename == null) return "";
        int i = filename.lastIndexOf('.');
        if (i < 0) return filename;
        return filename.substring(0, i);
    }

    /**
     * Sanitiza el nombre para que sea un token seguro de filesystem:
     * - Normaliza acentos
     * - Reemplaza espacios por guiones
     * - Elimina caracteres no alfanuméricos excepto guion y guion bajo
     * - Convierte a minúsculas
     */
    public static String sanitizeForFilename(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKD);
        // remover diacríticos
        normalized = normalized.replaceAll("\\p{M}", "");
        // convertir a ascii sustituyendo caracteres especiales restantes
        String cleaned = normalized.replaceAll("[^\\p{Alnum} _-]", "");
        // espacios a guiones y minusculas
        cleaned = cleaned.replaceAll("\\s+", "-").toLowerCase(Locale.ROOT);
        // trim posible guiones repetidos
        cleaned = cleaned.replaceAll("-{2,}", "-");
        return cleaned.replaceAll("^-|-$", "");
    }
}