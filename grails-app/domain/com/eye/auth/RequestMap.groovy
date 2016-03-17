package com.eye.auth

class RequestMap {
    String actionUrl
    static belongsTo = [Role]
    static hasMany = [roles : Role]

    static constraints = {
    }
}
