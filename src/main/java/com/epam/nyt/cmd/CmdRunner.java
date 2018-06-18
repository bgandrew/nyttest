package com.epam.nyt.cmd;

import com.epam.nyt.service.ResultComparatorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;

/**
 * Main class to run program from cmd
 */
public class CmdRunner {

    public static void main (String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Should be at least two argumets indicating json file names");
        }
        ResultComparatorService comparatorService = new ResultComparatorService();
        List<Set<String>> result = comparatorService.compareResults(readFile(args[0]), readFile(args[1]));
        writeResults(result);
    }

    private static String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
        } catch (IOException e) {
            System.out.println("Error reading file " + fileName);
            throw new IllegalArgumentException("Error reading file " + fileName);
        }
    }

    private static void writeResults(List<Set<String>> results) throws IOException {
        Path path = Paths.get("results");
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);
        for (int i = 0; i < results.size(); i++) {
            Files.write(path,
                    ("following fields differ for response.docs[" + i + "] : "
                            + results.get(i) + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);
        }
    }

}
