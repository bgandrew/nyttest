package com.epam.nyt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for comparing to search results
 */
public class ResultComparatorService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * compares responce.docs of two Json files. Returns list, where ith element represent
     * set of different fields in responce.docs[i] for left and right files.
     * @param left left json
     * @param right right json
     * @return returns list of sets of field that have different values.
     */
    public List<Set<String>> compareResults (String left, String right) throws IOException {
        JsonNode leftResultsArray = objectMapper.readTree(left).path("response").path("docs");
        JsonNode rightResultsArray = objectMapper.readTree(right).path("response").path("docs");

        Iterator<JsonNode> rightIterator = rightResultsArray.iterator();

        List<Set<String>> result = new ArrayList<>();



        for (JsonNode leftNode: leftResultsArray) {
            if(rightIterator.hasNext()) {
                // compare left and right result elements
                JsonNode rightNode = rightIterator.next();
                result.add(computeDifference(objectMapper.convertValue(leftNode, Map.class)
                        , objectMapper.convertValue(rightNode, Map.class)));

            } else {
                // no more right results
                result.add(new HashSet<>(Arrays.asList("Right result is empty")));
            }
        }

        while (rightIterator.hasNext()) {
            // no more right results
            result.add(new HashSet<>(Arrays.asList("Left result is empty")));
            rightIterator.next();
        }

        return result;
    }

    /**
     * Utility method for finding all keys such that left.get(key) != right.get(key).
     * If keysets are different for argument maps, their symmetric difference also included into result
     */
    Set<String> computeDifference( Map<String, Object> left, Map<String, Object> right) {
        Set<String> allKeys = new HashSet<>(left.keySet());
        allKeys.addAll(right.keySet());

        return allKeys.stream()
                .filter(key -> !jsonNodeEquals(left.get(key), right.get(key)))
                .collect(Collectors.toSet());

    }

    /**
     * Compares JsonNodes returning true if they have the same string representation
     */
    private static boolean jsonNodeEquals(Object o1, Object o2) {
        if (!Objects.equals(o1, o2)) {
            if (o1 != null && o2 != null) {
                return  o1.toString().equals(o2.toString());
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     *  maybe add a method for printing different field values too
     */
}
