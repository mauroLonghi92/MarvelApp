package com.example.data.entities

class MarvelBaseResponse<T>(

        var code: Int,
        var status: String,
        var data: T?
)
