import com.nm.libra.test.TestRun;

class BootStrap {

  def parseService
  def init = { servletContext ->
//        if (!TestRun.findByName("run0")) {
//            parseService.parse('/Users/nullin/Downloads/testng/testng-results-long.xml', "run0")
//        }
    if (!TestRun.findByName("run1")) {
      parseService.parse('/Users/nullin/Downloads/testng/testng-results-1.xml', "run1")
    }
    if (!TestRun.findByName("run2")) {
        parseService.parse('/Users/nullin/Downloads/testng/testng-results-2.xml', "run2")
    }
    if (!TestRun.findByName("run4")) {
        parseService.parse('/Users/nullin/Downloads/testng/testng-results-4.xml', "run4")
    }
  }
  def destroy = {
  }
}
