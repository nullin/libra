package com.nm.libra.test

/**
 * Suite that contains test methods
 */
class Suite {

  /** suite name  */
  String name

  /** list of test methods  */
  static hasMany = [testMethods: TestMethod]

  static constraints = {
    name(blank: false, nullable: false, unique: true)
  }

  static mapping = {
    sort "name"
  }

  /**
   * {@inheritDoc}
   */
  String toString() {
    "Suite [name:'${name}', size:${testMethods.size()}]"
  }


}
