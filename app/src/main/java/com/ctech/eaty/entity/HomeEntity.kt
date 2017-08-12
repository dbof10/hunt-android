package com.ctech.eaty.entity

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class HomeEntity(@PrimaryKey var key: String = "", var value: RealmList<Product> = RealmList()) : RealmModel