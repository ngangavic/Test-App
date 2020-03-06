package com.ngangavictor.testapp.data

class User {
    var email: String? = null
    var phone: String? = null
    var date: String? = null

    constructor(email: String?, phone: String?, date: String?) {
        this.email = email
        this.phone = phone
        this.date = date
    }
}