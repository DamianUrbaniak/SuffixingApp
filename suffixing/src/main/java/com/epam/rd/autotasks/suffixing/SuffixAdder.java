package com.epam.rd.autotasks.suffixing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuffixAdder {

    public String add(String file, String suffix) {
        int lastDotIndex = file.lastIndexOf(".");

        String fileName = file.substring(0, lastDotIndex);
        String extension = file.substring(lastDotIndex);
        return fileName + suffix + extension;
    }
}
