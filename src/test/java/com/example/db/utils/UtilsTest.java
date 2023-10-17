package com.example.db.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void sanitize() {
        String test = "\"><script>alert(1); </script>\"@test.co.uk";
        System.out.println(Utils.sanitize(test));

        String test2 = "<p><p><a href=\"#\" onclick=\"alert(1)\">PLEASE</a></p>";
        System.out.println(Utils.sanitize(test2));
    }
}