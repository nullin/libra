package trlistener

import com.nm.libra.test.Suite;
import com.nm.libra.test.TestMethod;

import grails.test.*

class TestMethodIntegrationTests extends GroovyTestCase {

    def suite
    def metaTest

    protected void setUp() {
        super.setUp()
        suite = new Suite(name:"Suite1")
        assertNotNull suite.save()
    }

    protected void tearDown() {
        super.tearDown()
        if(Suite.exists(suite.id))
            suite.delete()
    }

    void testSave() {
        def testMethod = new TestMethod(name:"TestMethod1", suite:suite, status:0)
        assertNotNull testMethod.save()
        assertNotNull testMethod.id

        def foundTestMethod = TestMethod.get(testMethod.id)
        assertEquals "TestMethod1", foundTestMethod.name
    }

    void testSaveAndUpdate() {
        def testMethod = new TestMethod(name:"TestMethod2", suite:suite, status:0)
        assertNotNull testMethod.save()
        assertNotNull testMethod.id

        def foundTestMethod = TestMethod.get(testMethod.id)
        foundTestMethod.name = "TestMethod2-New"
        foundTestMethod.save()
        assertEquals "TestMethod2-New", foundTestMethod.name
    }

    void testSaveAndDelete() {
        def testMethod = new TestMethod(name:"TestMethod2", suite:suite, status:0)
        assertNotNull testMethod.save()
        assertNotNull testMethod.id
        assertNotNull TestMethod.get(testMethod.id)
        def foundTestMethod = TestMethod.get(testMethod.id)
        foundTestMethod.delete()
        assertNull TestMethod.get(testMethod.id)
        assertFalse TestMethod.exists(testMethod.id)
    }
}
