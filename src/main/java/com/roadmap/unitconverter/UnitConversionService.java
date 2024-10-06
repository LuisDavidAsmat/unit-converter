package com.roadmap.unitconverter;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UnitConversionService
{
    private final Map<String, Double> lengthConversions = Map.of(
            "millimeter", 1.0,
            "centimeter", 10.0,
            "meter", 1000.0,
            "kilometer", 1_000_000.0,
            "inch", 25.4,
            "foot", 304.8,
            "yard", 914.4,
            "mile", 1_609_344.0
    );

    private final Map<String, Double> weightConversions = Map.of(
            "milligram", 1.0,
            "gram", 1000.0,
            "kilogram", 1_000_000.0,
            "ounce", 28_349.5,
            "pound", 453_592.0
    );

    // map of string and map of string and double. yes
    private final Map<String, Map<String, Double>> temperatureConversions = Map.of(
            "celsius", Map.of(
                    "fahrenheitFactor", 9.0 / 5.0,
                    "fahrenheitOffset", 32.0,
                    "kelvinOffset", 273.15
            ),
            "fahrenheit", Map.of(
                    "celsiusFactor", 5.0 / 9.0,
                    "celsiusOffset", -32.0,
                    "kelvinOffset", 253.372
            ),
            "kelvin", Map.of(
                    "celsiusOffset", -273.15,
                    "fahrenheitFactor", 9.0 / 5.0,
                    "fahrenheitOffset", -459.67
            )
    );


    public double convertUnit(double valueToConvert, String fromUnit, String toUnit, Map<String, Double> conversionMap) {
        return Math.round(
                valueToConvert * conversionMap.get(fromUnit) / conversionMap.get(toUnit)
        );
    }

    public double convertLength(double valueToConvert, String fromUnit, String toUnit) {
        return convertUnit(valueToConvert, fromUnit, toUnit, lengthConversions);
    }

    public double convertWeight(double valueToConvert, String fromUnit, String toUnit) {
        return convertUnit(valueToConvert, fromUnit, toUnit, weightConversions);
    }

    public double convertTemperature(double valueToConvert, String fromUnit, String toUnit)
    {
        if (fromUnit.equals(toUnit))
        {
            return valueToConvert;
        }

        Map<String, Double> conversionFactors =
                temperatureConversions.get(fromUnit);

        if (conversionFactors == null)
        {
            throw new IllegalArgumentException("Invalid from unit" + fromUnit);
        }

        switch (fromUnit)
        {
            case "celsius":
            {
                if (toUnit.equals("fahrenheit"))
                {
                    return Math.round(valueToConvert * conversionFactors.get("fahrenheitFactor")
                            + conversionFactors.get("fahrenheitOffset"));
                }
                else if (toUnit.equals("kelvin"))
                {
                    return valueToConvert + conversionFactors.get("kelvinOffset");
                }

                break;
            }
            case "fahrenheit":
            {
                if (toUnit.equals("celsius"))
                {
                    return Math.round((valueToConvert + conversionFactors.get("celsiusOffset"))
                            * conversionFactors.get("celsiusFactor"));

                }
                else if (toUnit.equals("kelvin"))
                {
                    return Math.round(
                            (valueToConvert + conversionFactors.get("celsiusOffset"))
                            * conversionFactors.get("celsiusFactor")
                            + temperatureConversions.get("celsius").get("kelvinOffset")
                    );
                }
                break;

            }
            case "kelvin":
            {
                if (toUnit.equals("celsius"))
                {
                    return valueToConvert + conversionFactors.get("celsiusOffset");
                }
                else if (toUnit.equals("fahrenheit"))
                {
                    return Math.round(
                            valueToConvert * conversionFactors.get("fahrenheitFactor") + conversionFactors.get("fahrenheitOffset")
                    );
                }
                break;

            }
            default:
            {
                throw new IllegalArgumentException("Invalid to unit: " + toUnit);
            }

        }

        throw new IllegalArgumentException("Invalid to unit: " + toUnit);
    }
}
