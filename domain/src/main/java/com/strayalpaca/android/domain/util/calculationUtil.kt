package com.strayalpaca.android.domain.util

fun getMax2Powers(number : Int) : Int {
    var result = 1
    while (number !in result..(result * 2)) {
        result *= 2
    }
    return result
}