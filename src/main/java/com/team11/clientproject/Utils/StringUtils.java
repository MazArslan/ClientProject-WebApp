package com.team11.clientproject.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Contains utilities for strings
 */
public class StringUtils {
    static final Logger LOG = LoggerFactory.getLogger(StringUtils.class);

    /**
     * Converts all empty strings in an object to nulls
     *
     * @param input the object we're converting
     * @param <E>   the type of the object
     * @return the modified object
     */
    public static <E> E convertEmptyStringsIntoNulls(E input) {
        // Get the class
        var objectClass = input.getClass();
        // Iterate through fields
        Arrays.stream(objectClass.getDeclaredFields()).forEach(field -> {
            try {
                // Make the field accessible and store its previous stage
                var initialCanAccess = field.canAccess(input);
                field.setAccessible(true);
                // get the field content
                var fieldContent = field.get(input);
                // If the field is a string, and is empty
                if (fieldContent instanceof String) {
                    var stringField = (String) fieldContent;
                    if (stringField.isEmpty()) {
                        field.set(input, null);// then set it to null
                    }
                }
                field.setAccessible(initialCanAccess); // restore initial visiblity
            } catch (IllegalAccessException e) {
                LOG.error("cannot access field."); // shouldn't happen but is here just in case.
            }

        });
        return input; // return our modified object
    }

    /**
     * Converts a string to a nullable boolean
     *
     * @param input the input string
     * @return the boolean
     */
    public static Boolean stringToBoolean(String input) {
        if (input == null || input.equals("")) {
            return null;
        }
        if (input.toLowerCase().equals("true")) {
            return true;
        }
        return false;
    }

    /**
     * Converts json strings to a list of strings using jacksono
     *
     * @param json the json input
     * @return the list of strings
     */
    public static List<String> convertJsonToListOfStrings(String json) {
        var objectMapper = new ObjectMapper();
        try {
            List<String> converted = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
            return converted;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    // Reference: https://stackoverflow.com/questions/13514570/jackson-best-way-writes-a-java-list-to-a-json-array
    // Accessed 06/12/2018
    public static <T> String convertListOfTypeToJson(List<T> items) {
        final StringWriter sw = new StringWriter();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(sw, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
