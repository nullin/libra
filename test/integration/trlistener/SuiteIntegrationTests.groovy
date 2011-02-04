package trlistener

import com.nm.libra.test.Suite;
import com.nm.libra.test.TestMethod;

import grails.test.*

class SuiteIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNullName() {
        def suite = new Suite(name:null)
        assertFalse suite.validate()
    }

    void testEmptyName() {
        def suite = new Suite(name:"")
        assertFalse suite.validate()
    }

    void testNonUniqueName() {
        def suite1 = new Suite(name:"X")
        assertNotNull suite1.save()
        def suite2 = new Suite(name:"X")
        assertFalse suite2.validate()
        suite1.delete()
        assertFalse Suite.exists(suite1.id)
    }

    void addMetaTests() {
        def suite = new Suite(name:'Suite1')
        assertNotNull suite.save()
        assertNotNull suite.id
        def testMethod = new TestMethod(name:'MT1')
        suite.addToTestMethods(testMethod)
        testMethod = new TestMethod(name:'MT2')
        suite.addToTestMethods(testMethod)
        testMethod = new TestMethod(name:'MT3')
        suite.addToTestMethods(testMethod)

        assertTrue 3 == Suite.get(suite.id).testMethods.size()
        testMethodNames = Suite.get(suite.id).testMethods*.name
        assertTrue ['MT1', 'MT2', 'MT3'] == testMethodNames.sort()
    }
}
