package com.nm.libra.utils

import java.text.DateFormat;

import org.apache.tools.ant.types.selectors.DateSelector;

class DateUtil {

    //2011-01-24T12:14:34Z
    def static format = "yyyy-MM-dd'T'hh:mm:ss'Z'"

    def static getISODate(dateStr) {
        return Date.parse(format, dateStr)
    }

    def static getISODateAsString(date) {
        return date.format(format)
    }

}
