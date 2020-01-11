package com.aidiscern.bill

class UserKt : Parent{
    var name: String = ""
    var age: Int = 0

    constructor(userName: String?) : super(userName) {}
    constructor(age: Int) : super(age) {
        this.age = age
    }
}