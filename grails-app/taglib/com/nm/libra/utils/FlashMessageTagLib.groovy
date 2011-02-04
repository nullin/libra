package com.nm.libra.utils


import com.nm.libra.utils.DateUtil;

class FlashMessageTagLib {
    static namespace = "trl"

    def flashMessage = { attrs ->
        if (attrs.message) {
            out << "<div class=\"message\">${attrs.message}</div>"
        }
    }
}
