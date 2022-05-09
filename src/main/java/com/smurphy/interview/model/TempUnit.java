package com.smurphy.interview.model;

/**
 * Enum to represent tempurature units. Could be expanded to have more types or
 * replaced by a library depending on requirements.
 */
public enum TempUnit {
    F,
    C;

    /**
     * Convert between fahrenheit and celsius
     * 
     * @param oldUnit The unit of the temperature you want to convert.
     * @param temp    The temperature to convert
     * @return The temperature in the new unit.
     */
    public static double convertTemp(TempUnit oldUnit, double temp) {
        if (oldUnit.equals(F)) {
            return (temp - 32) * 5 / 9;
        }
        return (temp * 9 / 5) + 32;
    }
}
