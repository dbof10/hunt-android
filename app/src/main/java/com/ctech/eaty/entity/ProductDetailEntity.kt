package com.ctech.eaty.entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ProductDetailEntity(@PrimaryKey var key: String = "",
                               var value: ProductDetailRealm = ProductDetailRealm.EMPTY) : RealmModel