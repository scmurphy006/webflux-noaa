package com.smurphy.interview.deserializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.smurphy.interview.model.Forecast;
import com.smurphy.interview.model.Period;

/**
 * Custom deserializer for Forecast objects
 */
public class ForecastDeserializer extends StdDeserializer<Forecast> {

    private static final String PROPERTIES = "properties";
    private static final String PERIODS = "periods";

    /**
     * Required explicit constructor for custom deserializers
     */
    public ForecastDeserializer() {
        this(null);
    }

    /**
     * Required constructor by StdDeserializer. Called by default constructor
     * 
     * @param vc
     */
    protected ForecastDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * "Create a new Forecast object by deserializing the periods contained in this
     * parser"
     * 
     * The first thing we do is get the JsonNode from the parser. This is the root
     * node of the JSON
     * 
     * @param parser The JsonParser that contains the JSON to deserialize.
     * @param arg1   DeserializationContext
     * @return A Forecast object.
     */
    @Override
    public Forecast deserialize(JsonParser parser, DeserializationContext arg1) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        // Verify that the JSON can represent a Forecast
        if (node.get(PROPERTIES).isNull()
                || node.get(PROPERTIES).get(PERIODS).isNull()) {
            // Make sure it has 1 or more period
            throw new IllegalArgumentException("At least one Period is neeed to create a Forecast.");
        }

        // Create a new Forecast object by deserializing the periods contained in this
        // parser
        return new Forecast(
                new ObjectMapper().readValue(
                        node.get(PROPERTIES).get(PERIODS).toString(),
                        new TypeReference<List<Period>>() {
                        }));
    }
}
