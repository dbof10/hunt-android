package com.ctech.eaty.util

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ResizeImageUrlProviderTest {


    @Test
    fun get_newUrl_with_added_proxy_and_size() {
        val url = "https://ph-files.imgix.net/c643b71b-b2bb-4220-b6d9-3118057941a1?auto=format"
        val newUrl = ResizeImageUrlProvider.getNewUrl(url, 200)
        assertEquals("http://ph-files.imgix.net.rsz.io/c643b71b-b2bb-4220-b6d9-3118057941a1?auto=format&width=200", newUrl)
    }

    @Test
    fun get_newUrl_inline_with_added_proxy_and_size(){
        val url = "http://url2png.com/v6/P53290ECB6/678522d28/png/?max_width=300&url=https%3A%2F%2Fplay.google.com?id=com.nothotdog"
        val newUrl = ResizeImageUrlProvider.getNewUrl(url, 200)
        assertEquals("http://url2png.com.rsz.io/v6/P53290ECB6/678522d28/png/?max_width=300&url=https%3A%2F%2Fplay.google.com?id=com.nothotdog&width=400", newUrl)

    }
}