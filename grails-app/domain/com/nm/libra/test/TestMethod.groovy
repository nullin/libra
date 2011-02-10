package com.nm.libra.test

/**
 * A test or configuration method
 */
class TestMethod {

  /** package of the class containing this method  */
  String packageName
  /** simple name of class containing this method  */
  String className
  /** method's name  */
  String methodName
  /** in case of data-driven tests, this is the unique name for this instance  */
  String instanceName
  /** true, if this is a test and false if it's a configuration method  */
  boolean isTest
  /** complete name. Internal, generated before validation (#beforeValidate())  */
  String name //internal

  /** suite that contains this test  */
  static belongsTo = [suite: Suite]
  /** test results associated with this method  */
  static hasMany = [testResults: TestResult]
  /** used for displaying test name in UI  */
  static transients = ['displayName']

  static constraints = {
    packageName(blank: false, nullable: false)
    className(blank: false, nullable: false)
    methodName(blank: false, nullable: false)
    name(blank: false, nullable: false)
    //TODO: Add check to ensure suitename + test name is unique
  }

  static mapping = {
    sort "name"
  }

  /**
   * Used to generate the internal test name before validation
   * @return
   */
  def beforeValidate() {
    name = generateName()
  }

  /**
   * Generates the internal test name
   * @return the internal test name
   */
  def generateName() {
    def name = packageName + "." + className + "." + methodName
    if (instanceName) {
      name += "@" + instanceName
    }
    return name
  }

  /**
   * The displayable name for this test method
   * @return
   */
  def getDisplayName() {
    return name.replace("@", "<br/>@")
  }

  /**
   * {@inheritDoc}
   */
  String toString() {
    "TestMethod [name:'${getName()}', suite:'${suite.name}', isTest:${isTest}]"
  }
}
