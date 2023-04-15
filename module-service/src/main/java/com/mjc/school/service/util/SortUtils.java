package com.mjc.school.service.util;

import org.springframework.data.domain.Sort;

public class SortUtils {
    private SortUtils() {}

    public static Sort getSort(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.unsorted();
        }
        String[] sortParameters = sortBy.trim().split("::");
        boolean isDesc = sortParameters.length > 1 && sortParameters[1].equalsIgnoreCase("desc");
        String sortName = sortParameters[0].toLowerCase();
        Sort sort = switch (sortName) {
            case "created" -> Sort.by("created");
            case "modified" -> Sort.by("modified");
            case "id" -> Sort.by("id");
            default -> Sort.unsorted();
        };

        return isDesc ? sort.descending() : sort.ascending();
    }
}
