package com.nm.libra.test

class Suite {
    String name
    static hasMany = [ testMethods : TestMethod ]
    static constraints = {
        name(blank:false, nullable:false, unique:true)
    }

    String toString() {
        "Suite [name:'${name}', size:${testMethods.size()}]"
    }

    static mapping = {
        sort "name"
    }
}
