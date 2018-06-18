package com.epam.nyt.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ResultComparatorServiceTest {

    private String left = "";
    private String right = "";

    @Before
    public void init() throws IOException {
        left = new String(Files.readAllBytes(Paths.get("src","test","resources", "1.json")), "UTF-8");
        right = new String(Files.readAllBytes(Paths.get("src","test","resources", "2.json")), "UTF-8");
    }

    @Test
    public void compareResults() throws IOException {

        ResultComparatorService service = new ResultComparatorService();

        List<Set<String>> results = service.compareResults(left, right);

        Assert.assertEquals(results.get(0), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(1), new HashSet<>(Arrays.asList("multimedia", "keywords", "_id", "web_url")));
        Assert.assertEquals(results.get(2), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(3), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(4), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(5), new HashSet<>(Arrays.asList("multimedia", "keywords", "_id", "web_url", "headline")));
        Assert.assertEquals(results.get(6), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(7), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(8), new HashSet<>(Arrays.asList("print_page", "web_url", "_id", "news_desk")));
        Assert.assertEquals(results.get(9), new HashSet<>(Arrays.asList("multimedia", "keywords", "_id", "web_url")));
    }

    @Test
    public void computeDifference() {
        ResultComparatorService service = new ResultComparatorService();

        Map<String, Object> left = new HashMap<>();
        left.put("left", 1L);
        left.put("test", "test");
        left.put("common", "1");
        left.put("another left", null);


        Map<String, Object> right = new HashMap<>();
        right.put("right", 2L);
        right.put("test", "test");
        left.put("common", "2");


        Set<String> result = service.computeDifference(left, right);
        Assert.assertEquals(result, new HashSet<>(Arrays.asList("common", "left", "right")));
    }
}