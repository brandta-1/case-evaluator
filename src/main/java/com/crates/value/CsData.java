package com.crates.value;

import java.util.*;

import static java.util.Map.entry;

//intended to be a single source of truth for all CS2 data
public class CsData {

        public static Map<String, Double> rarityOdds = new HashMap<>(Map.ofEntries(
                entry("l", 0.032),
                entry("m",  0.1598),
                entry("r",  0.7992),
                entry("a",0.0064),
                entry("g", 0.0026)
        ));

        public static double[][] weaponWearBoundaries = {
                {0.0,0.07},
                {0.08,0.15},
                {0.16,0.38},
                {0.39,0.45},
                {0.46,1.0}
        };

        public static double statTrakOdds = 0.1;
        // public static final double[] odds = {0.03, 0.24, 0.33, 0.24, 0.16};

        public static final List<Double> odds = Arrays.asList(0.03, 0.24, 0.33, 0.24, 0.16);
    }

