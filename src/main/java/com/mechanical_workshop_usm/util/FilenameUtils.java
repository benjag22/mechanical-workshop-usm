package com.mechanical_workshop_usm.util;

import java.text.Normalizer;
import java.util.Locale;

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

    public static String sanitizeForFilename(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKD);
        normalized = normalized.replaceAll("\\p{M}", "");
        String cleaned = normalized.replaceAll("[^\\p{Alnum} _-]", "");
        cleaned = cleaned.replaceAll("\\s+", "-").toLowerCase(Locale.ROOT);
        cleaned = cleaned.replaceAll("-{2,}", "-");
        return cleaned.replaceAll("^-|-$", "");
    }
}