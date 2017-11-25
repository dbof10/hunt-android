package com.ctech.eaty.util

import org.junit.Assert
import org.junit.Test

class LinkMatcherTest {


    @Test
    fun testMatchCollections(){
        val url = "https://www.producthunt.com/@moyicat/collections/mailbox-alternatives"
        Assert.assertTrue(LinkMatcher.matchCollections(url))
    }

    @Test
    fun testMatchUser(){
        val url1 = "https://www.producthunt.com/@moyicat"
        val url2 = "https://www.producthunt.com/@moyicat/collections/mailbox-alternatives"
        Assert.assertTrue(LinkMatcher.matchUser(url1))
        Assert.assertFalse(LinkMatcher.matchUser(url2))
    }
}