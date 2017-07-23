package com.tdanylchuk.roulette.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.stream.Stream;

@UtilityClass
public class FileUtils {

    @SneakyThrows
    public static Stream<String> loadClassPathFileContent(String fileName) {
        InputStream inputStream = new ClassPathResource(fileName).getInputStream();
        return loadFileContent(inputStream);
    }

    @SneakyThrows
    public static Stream<String> loadFileContent(String filePath) {
        InputStream inputStream = new FileInputStream(filePath);
        return loadFileContent(inputStream);
    }

    @SneakyThrows
    private static Stream<String> loadFileContent(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        return bufferedReader.lines().onClose(() -> {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Failed to close!");
            }
        });
    }
}
