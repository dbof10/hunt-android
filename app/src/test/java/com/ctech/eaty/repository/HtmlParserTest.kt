package com.ctech.eaty.repository

import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class HtmlParserTest {

    private lateinit var parser: HtmlParser

    @Before
    fun setup() {
        parser = HtmlParser()
    }

    @Test
    fun should_match_product_uri_when_parse_url() {
        val url = "https://www.producthunt.com/posts/luggagehero"
        val testObserver = TestObserver.create<String>()
        parser.getProductIdUri(url).subscribe(testObserver)
        testObserver.assertValue("producthunt://post/102705")
    }
}