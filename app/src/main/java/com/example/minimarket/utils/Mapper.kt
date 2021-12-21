package com.example.minimarket.utils

interface Mapper<S, D> {
    fun map(data: S): D
}