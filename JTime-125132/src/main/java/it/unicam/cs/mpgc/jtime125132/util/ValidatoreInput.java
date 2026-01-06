package it.unicam.cs.mpgc.jtime125132.util;

public final class ValidatoreInput {
    private ValidatoreInput() {}

    public static String nonVuota(String s, String nomeCampo) {
        if (s == null || s.trim().isEmpty()) throw new IllegalArgumentException(nomeCampo + " obbligatorio.");
        return s.trim();
    }

    public static int minutiPositivi(int m, String nomeCampo) {
        if (m <= 0) throw new IllegalArgumentException(nomeCampo + " deve essere > 0.");
        return m;
    }
}