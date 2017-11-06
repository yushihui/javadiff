package com.future.jdiff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDiff {


    private File jia, yi;

    public FileDiff() {


    }

    public static void main(String args[]) {
        new FileDiff().doDiff();
    }

    public void doDiff() {
        List<String> jLines = readFileFromPath("sample", "t1.txt");
        List<String> yLines = readFileFromPath("sample", "t2.txt");
        DifferFetcher df = new DifferFetcher(jLines, yLines);
        long now = System.currentTimeMillis();
        List<OperationDetail> operations = df.operationsFromJia2Yi();
        System.out.println(System.currentTimeMillis() - now);
        operations.forEach(o -> System.out.println(o));
    }

    private List<String> readFileFromPath(String path, String... paths) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path, paths))) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


}
