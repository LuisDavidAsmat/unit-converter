package com.roadmap.unitconverter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class MainController
{
    private final UnitConversionService unitConversionService;

    public MainController(UnitConversionService unitConversionService)
    {
        this.unitConversionService = unitConversionService;
    }

    @GetMapping("/")
    public String homePage (Model model)
    {
        setModelAttributes(model, "/length-conversion",
                "Length Converter", getLengthUnits());

        return "convert-page.html";
    }

    @GetMapping("/length-conversion")
    public String lengthPage (Model model)
    {
        setModelAttributes(model, "/length-conversion",
                "Length Converter", getLengthUnits());

        return "convert-page.html";
    }

    @GetMapping("/temperature-conversion")
    public String temperaturePage (Model model)
    {

        setModelAttributes(model, "/temperature-conversion",
                "Temperature Converter", getTemperatureUnits());

        return "convert-page.html";
    }

    @GetMapping("/weight-conversion")
    public String weightPage (Model model)
    {

        setModelAttributes(model, "/weight-conversion",
                "Weight Converter", getWeightUnits());

        return "convert-page.html";
    }

    @PostMapping("/length-conversion")
    public String convertLength (
            @RequestParam("value") double valueToConvert,
            @RequestParam("fromUnit") String fromUnit,
            @RequestParam("toUnit") String toUnit,
            RedirectAttributes redirectAttributes
    )
    {
        return convertUnit(
                valueToConvert, fromUnit, toUnit,
                unitConversionService::convertLength, redirectAttributes,
                "length-conversion"
        );
    }

    @PostMapping("/weight-conversion")
    public String convertWeight (
            @RequestParam("value") double valueToConvert,
            @RequestParam("fromUnit") String fromUnit,
            @RequestParam("toUnit") String toUnit,
            RedirectAttributes redirectAttributes
    )
    {
        return convertUnit(
                valueToConvert, fromUnit, toUnit,
                unitConversionService::convertWeight, redirectAttributes,
                "weight-conversion"
        );
    }

    @PostMapping("/temperature-conversion")
    public String convertTemperature (
            @RequestParam("value") double valueToConvert,
            @RequestParam("fromUnit") String fromUnit,
            @RequestParam("toUnit") String toUnit,
            RedirectAttributes redirectAttributes
    )
    {
        return convertUnit(
                valueToConvert, fromUnit, toUnit,
                unitConversionService::convertTemperature, redirectAttributes,
                "temperature-conversion"
        );
    }

    private String convertUnit (
            double valueToConvert,
            String fromUnit,
            String toUnit,
            ConversionInterface conversionInterface,
            RedirectAttributes redirectAttributes,
            String redirectPath
    )
    {
        double convertedResult =
                conversionInterface.applyConversion(valueToConvert, fromUnit, toUnit);

        redirectAttributes.addFlashAttribute("fromUnit", fromUnit);
        redirectAttributes.addFlashAttribute("value", valueToConvert);
        redirectAttributes.addFlashAttribute("toUnit", toUnit);
        redirectAttributes.addFlashAttribute("convertedResult", convertedResult);

        return "redirect:/" + redirectPath;
    }

    @FunctionalInterface
    interface ConversionInterface
    {
        double applyConversion (
                double valueToConvert, String fromUnit, String toUnit
        );
    }


    private void setModelAttributes (Model model, String redirectPath,
                                     String pageTitle, List<String> units)
    {
        model.addAttribute("redirectPath", redirectPath);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("units", units);
    }

    private List<String> getLengthUnits()
    {
        return Arrays.asList("millimeter", "centimeter", "meter", "kilometer", "inch", "foot", "yard", "mile");
    }

    private List<String> getTemperatureUnits() {
        return Arrays.asList("celsius", "fahrenheit", "kelvin");
    }

    private List<String> getWeightUnits() {
        return Arrays.asList("gram", "kilogram", "ounce", "pound", "ton");
    }
}
