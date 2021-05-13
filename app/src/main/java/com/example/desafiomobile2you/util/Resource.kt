package com.example.desafiomobile2you.util

class Resource<T, TError:kotlin.Exception> {

    var error: String? = null
    var data: T? = null
    var success: Boolean = false

    public constructor(data: T) {
        this.success = true
        this.data = data
    }
    public constructor(error: TError) {
        this.success = false
        this.error = error.message
    }

}