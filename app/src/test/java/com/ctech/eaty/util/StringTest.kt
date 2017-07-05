package com.ctech.eaty.util

import org.junit.Test
import org.junit.Assert.assertEquals

class StringTest {


    @Test
    fun test_subStringBetween_two_characters(){
        val str1 = "product 'product//123'"
        assertEquals("product//123", str1.substringBetween(str1, "\'", "\'"))

        val str2 = "product 'product//123)"
        assertEquals("", str2.substringBetween(str2, "\'", "\'"))
    }
}