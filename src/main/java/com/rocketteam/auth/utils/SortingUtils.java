package com.rocketteam.auth.utils;

import org.springframework.data.domain.Sort;

public final class SortingUtils {

    /**
     * Sorting utility method
     * @param sortBy
     * @param order
     * @return Sorted object
     */
    public static Sort generateSort(String sortBy, String order) {
        Sort sorting = Sort.by(sortBy);

        if (order.equals("desc"))
            sorting = sorting.descending();
        else
            sorting = sorting.ascending();

        return sorting;
    }

}