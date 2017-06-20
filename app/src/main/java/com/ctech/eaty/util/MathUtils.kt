package com.ctech.eaty.util;

class MathUtils private constructor() {


    companion object {
        fun constrain(min: Float, max: Float, v: Float): Float {
            return Math.max(min, Math.min(max, v))
        }
    }
}