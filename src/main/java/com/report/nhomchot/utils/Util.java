package com.report.nhomchot.utils;

import com.report.nhomchot.models.FilterOption;
import jakarta.servlet.http.HttpServletRequest;

public class Util {
    public static FilterOption getFilterOption(HttpServletRequest httpRequest) {
        FilterOption filterOption = new FilterOption();

        String iDisplayStartParam = httpRequest.getParameter("iDisplayStart");
        filterOption.setiDisplayStart(iDisplayStartParam != null && !iDisplayStartParam.isEmpty() ? Integer.parseInt(iDisplayStartParam) : 0);

        String iDisplayLengthParam = httpRequest.getParameter("iDisplayLength");
        filterOption.setiDisplayLength(iDisplayLengthParam != null && !iDisplayLengthParam.isEmpty() ? Integer.parseInt(iDisplayLengthParam) : 10);

        String sortNameParam = httpRequest.getParameter("sortName");
        if (sortNameParam != null && !sortNameParam.isEmpty()) {
            filterOption.setSortName(Character.toUpperCase(sortNameParam.charAt(0)) + sortNameParam.substring(1));
        } else {
            filterOption.setSortName("id"); // Giá trị mặc định
        }

        String sSortDir_0 = httpRequest.getParameter("sSortDir_0");
        filterOption.setsSortDir_0(sSortDir_0 != null && !sSortDir_0.isEmpty() ? sSortDir_0 : "asc"); // Giá trị mặc định

        return filterOption;
    }
}