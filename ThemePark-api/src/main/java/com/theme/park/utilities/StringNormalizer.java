package com.theme.park.utilities;

import java.text.Normalizer;

public class StringNormalizer {

    public static String normalize(String s){
        String normalize = Normalizer.normalize(s, Normalizer.Form.NFD);
        normalize = normalize.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        normalize = normalize.toLowerCase();
        normalize = normalize.replaceAll(" ", "-");
        return normalize;
    }

}
