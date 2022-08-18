## **SSTTECH** - _Case Study_

This project has been created for answering the case study problems from `SSTTECH` by `ARIKBOĞA` from scratch and as a
Cucumber-Junit project.

* The 3 functional tests which are requested on [eBay](https://www.ebay.com/b/Home-Decor/10033/bn_1849733) were created
  using Selenium and Java and their automation was completed.


* Sometimes, a problem can arise due to security protections such as eBay's captcha and eventually the test case may
  fail because of this. As it is known, **captcha** feature automation is not possible using any automation tool, at
  least for now.


* For business level understanding and making the things easy to follow by the other teammates the `Cucumber Gherkin`
  files and `Cucumber report` process have been used in this project. Please check under the `target` directory for the
  reports(XML, JSON, HTML).


* The **parallel** execution infrastructure has been created using `maven-surefire` plugin in the `pom.xml` file in
  order to run the test of the project entirely and based on the gherkin(feature) files.


* As I mentioned in our first meeting, although I have more or less an idea, I have not had a **performance** test
  experience before. That's why I did not do the performance test event specified in the case study description. This is
  a subject that I would like to add to my skills in the future if I can find the opportunity.


* The maven dependency of `boni-garcia` has been used to get capability to access all drivers of the latest versions of
  browsers


* The ability to test in different browsers is provided by using the method of reading data from the file. When the
  browser name in the file is changed with the browser name in which the tests are to be run, the tests will run on that
  browser. The browser to be used **must be installed** on the PC where the tests will be run. Please check
  the `configuration.properties` file.


* The creation of the java files under the `Utilities` package and the structures in them are entirely owned by the
  creator of this project.


* In order to ensure that the project is included in the `CI/CD` process, a `Jenkins-Maven` job has been created on
  the **local** server and the tests, including pre-post operations, have been successfully run and are ready for
  presentation.


* More detailed explanation and explanation will be provided upon request.

### King Regards.