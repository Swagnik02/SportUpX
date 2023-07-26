package com.team.fantasy.resources;

import android.content.res.Resources;

import com.team.fantasy.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateCityInitializer {

    // Initialize the state list with data from the resource file
    public static List<String> getStateList(Resources resources) {
        return Arrays.asList(resources.getStringArray(R.array.state_list));
    }

    // Initialize the city list HashMap with data from the resource files
    public static Map<String, List<String>> getCityMap(Resources resources, List<String> stateList) {
        Map<String, List<String>> cityMap = new HashMap<>();

        cityMap.put(getStateList(resources).get(0), Arrays.asList(resources.getStringArray(R.array.city_list_andaman_and_nicobar)));
        cityMap.put(getStateList(resources).get(1), Arrays.asList(resources.getStringArray(R.array.city_list_andhra_pradesh)));
        cityMap.put(getStateList(resources).get(2), Arrays.asList(resources.getStringArray(R.array.city_list_arunachal_pradesh)));
        cityMap.put(getStateList(resources).get(3), Arrays.asList(resources.getStringArray(R.array.city_list_assam)));
        cityMap.put(getStateList(resources).get(4), Arrays.asList(resources.getStringArray(R.array.city_list_bihar)));
        cityMap.put(getStateList(resources).get(5), Arrays.asList(resources.getStringArray(R.array.city_list_chandigarh)));
        cityMap.put(getStateList(resources).get(6), Arrays.asList(resources.getStringArray(R.array.city_list_chhattisgarh)));
        cityMap.put(getStateList(resources).get(7), Arrays.asList(resources.getStringArray(R.array.city_list_dadra_and_nagar_haveli)));
        cityMap.put(getStateList(resources).get(8), Arrays.asList(resources.getStringArray(R.array.city_list_daman_and_diu)));
        cityMap.put(getStateList(resources).get(9), Arrays.asList(resources.getStringArray(R.array.city_list_delhi)));
        cityMap.put(getStateList(resources).get(10), Arrays.asList(resources.getStringArray(R.array.city_list_goa)));
        cityMap.put(getStateList(resources).get(11), Arrays.asList(resources.getStringArray(R.array.city_list_gujarat)));
        cityMap.put(getStateList(resources).get(12), Arrays.asList(resources.getStringArray(R.array.city_list_haryana)));
        cityMap.put(getStateList(resources).get(13), Arrays.asList(resources.getStringArray(R.array.city_list_himachal_pradesh)));
        cityMap.put(getStateList(resources).get(14), Arrays.asList(resources.getStringArray(R.array.city_list_jammu_and_kashmir)));
        cityMap.put(getStateList(resources).get(15), Arrays.asList(resources.getStringArray(R.array.city_list_jharkhand)));
        cityMap.put(getStateList(resources).get(16), Arrays.asList(resources.getStringArray(R.array.city_list_karnataka)));
        cityMap.put(getStateList(resources).get(17), Arrays.asList(resources.getStringArray(R.array.city_list_kerala)));
        cityMap.put(getStateList(resources).get(18), Arrays.asList(resources.getStringArray(R.array.city_list_lakshadweep)));
        cityMap.put(getStateList(resources).get(19), Arrays.asList(resources.getStringArray(R.array.city_list_madhya_pradesh)));
        cityMap.put(getStateList(resources).get(20), Arrays.asList(resources.getStringArray(R.array.city_list_maharashtra)));
        cityMap.put(getStateList(resources).get(21), Arrays.asList(resources.getStringArray(R.array.city_list_manipur)));
        cityMap.put(getStateList(resources).get(22), Arrays.asList(resources.getStringArray(R.array.city_list_meghalaya)));
        cityMap.put(getStateList(resources).get(23), Arrays.asList(resources.getStringArray(R.array.city_list_mizoram)));
        cityMap.put(getStateList(resources).get(24), Arrays.asList(resources.getStringArray(R.array.city_list_nagaland)));
        cityMap.put(getStateList(resources).get(25), Arrays.asList(resources.getStringArray(R.array.city_list_odisha)));
        cityMap.put(getStateList(resources).get(26), Arrays.asList(resources.getStringArray(R.array.city_list_puducherry)));
        cityMap.put(getStateList(resources).get(27), Arrays.asList(resources.getStringArray(R.array.city_list_punjab)));
        cityMap.put(getStateList(resources).get(28), Arrays.asList(resources.getStringArray(R.array.city_list_rajasthan)));
        cityMap.put(getStateList(resources).get(29), Arrays.asList(resources.getStringArray(R.array.city_list_sikkim)));
        cityMap.put(getStateList(resources).get(30), Arrays.asList(resources.getStringArray(R.array.city_list_tamil_nadu)));
        cityMap.put(getStateList(resources).get(31), Arrays.asList(resources.getStringArray(R.array.city_list_telangana)));
        cityMap.put(getStateList(resources).get(32), Arrays.asList(resources.getStringArray(R.array.city_list_tripura)));
        cityMap.put(getStateList(resources).get(33), Arrays.asList(resources.getStringArray(R.array.city_list_uttar_pradesh)));
        cityMap.put(getStateList(resources).get(34), Arrays.asList(resources.getStringArray(R.array.city_list_uttarakhand)));
        cityMap.put(getStateList(resources).get(35), Arrays.asList(resources.getStringArray(R.array.city_list_west_bengal)));


        return cityMap;
    }
}
