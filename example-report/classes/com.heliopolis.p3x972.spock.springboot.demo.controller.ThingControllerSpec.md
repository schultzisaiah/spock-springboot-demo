<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="x-ua-compatible" content="IE=edge"/>
<title>Test results - Class com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec</title>
<link href="../css/base-style.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<script src="../js/report.js" type="text/javascript"></script>
</head>
<body>
<div id="content">
<h1>Class com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec</h1>
<div class="breadcrumbs">
<a href="../index.md">all</a> &gt; 
<a href="../packages/com.heliopolis.p3x972.spock.springboot.demo.controller.md">com.heliopolis.p3x972.spock.springboot.demo.controller</a> &gt; ThingControllerSpec</div>
<div id="summary">
<table>
<tr>
<td>
<div class="summaryGroup">
<table>
<tr>
<td>
<div class="infoBox" id="tests">
<div class="counter">11</div>
<p>tests</p>
</div>
</td>
<td>
<div class="infoBox" id="failures">
<div class="counter">4</div>
<p>failures</p>
</div>
</td>
<td>
<div class="infoBox" id="ignored">
<div class="counter">0</div>
<p>ignored</p>
</div>
</td>
<td>
<div class="infoBox" id="duration">
<div class="counter">0.300s</div>
<p>duration</p>
</div>
</td>
</tr>
</table>
</div>
</td>
<td>
<div class="infoBox failures" id="successRate">
<div class="percent">63%</div>
<p>successful</p>
</div>
</td>
</tr>
</table>
</div>
<div id="tabs">
<ul class="tabLinks">
<li>
<a href="#tab0">Failed tests</a>
</li>
<li>
<a href="#tab1">Tests</a>
</li>
</ul>
<div id="tab0" class="tab">
<h2>Failed tests</h2>
<div class="test">
<a name="getThing() throws exception - FAILURE DEMO"></a>
<h3 class="failures">getThing() throws exception - FAILURE DEMO</h3>
<span class="code">
<pre>Expected exception of type 'java.lang.NullPointerException', but got 'java.lang.RuntimeException'
	at org.spockframework.lang.SpecInternals.checkExceptionThrown(SpecInternals.java:80)
	at org.spockframework.lang.SpecInternals.thrownImpl(SpecInternals.java:67)
	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.getThing() throws exception - FAILURE DEMO(ThingControllerSpec.groovy:82)
Caused by: java.lang.RuntimeException
	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.getThing() throws exception - FAILURE DEMO(ThingControllerSpec.groovy:75)
</pre>
</span>
</div>
<div class="test">
<a name="getThing(0) - FAILURE DEMO"></a>
<h3 class="failures">getThing(0) - FAILURE DEMO</h3>
<span class="code">
<pre>Condition not satisfied:

result == expected
|      |  |
|      |  Mock for type 'ThingAndStuff' (com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff$SpockMock$608992178@a589267)
|      false
Mock for type 'ThingAndStuff' (com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff$SpockMock$608992178@57a482f3)

	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.getThing(#i) - FAILURE DEMO(ThingControllerSpec.groovy:52)
</pre>
</span>
</div>
<div class="test">
<a name="getThing(1) - FAILURE DEMO"></a>
<h3 class="failures">getThing(1) - FAILURE DEMO</h3>
<span class="code">
<pre>Condition not satisfied:

result == expected
|      |  |
|      |  ThingAndStuff(super=Thing(id=1, name=null, updateDate=null), listOfStuff=[Stuff(value=bar)])
|      false
ThingAndStuff(super=Thing(id=0, name=null, updateDate=null), listOfStuff=[Stuff(value=foo)])

	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.getThing(#i) - FAILURE DEMO(ThingControllerSpec.groovy:52)
</pre>
</span>
</div>
<div class="test">
<a name="saveThing() - FAILURE DEMO - handling both happy-path and exception in the same test"></a>
<h3 class="failures">saveThing() - FAILURE DEMO - handling both happy-path and exception in the same test</h3>
<span class="code">
<pre>Condition not satisfied:

result == ex
|      |  |
|      |  java.lang.RuntimeException
|      |  	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.$spock_feature_1_5(ThingControllerSpec.groovy:112)
|      false
Mock for type 'ThingAndStuff' named 'thing'

	at com.heliopolis.p3x972.spock.springboot.demo.controller.ThingControllerSpec.saveThing() - FAILURE DEMO - handling both happy-path and exception in the same test(ThingControllerSpec.groovy:122)
</pre>
</span>
</div>
</div>
<div id="tab1" class="tab">
<h2>Tests</h2>
<table>
<thead>
<tr>
<th>Test</th>
<th>Duration</th>
<th>Result</th>
</tr>
</thead>
<tr>
<td class="success">getThing() throws exception</td>
<td class="success">0.025s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="failures">getThing() throws exception - FAILURE DEMO</td>
<td class="failures">0.003s</td>
<td class="failures">failed</td>
</tr>
<tr>
<td class="success">getThing(-999)</td>
<td class="success">0s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="success">getThing(0)</td>
<td class="success">0.083s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="failures">getThing(0) - FAILURE DEMO</td>
<td class="failures">0.132s</td>
<td class="failures">failed</td>
</tr>
<tr>
<td class="success">getThing(1)</td>
<td class="success">0.001s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="failures">getThing(1) - FAILURE DEMO</td>
<td class="failures">0.011s</td>
<td class="failures">failed</td>
</tr>
<tr>
<td class="success">getThing(10)</td>
<td class="success">0.001s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="success">getThing(999)</td>
<td class="success">0.014s</td>
<td class="success">passed</td>
</tr>
<tr>
<td class="failures">saveThing() - FAILURE DEMO - handling both happy-path and exception in the same test</td>
<td class="failures">0.008s</td>
<td class="failures">failed</td>
</tr>
<tr>
<td class="success">saveThing() - handling both happy-path and exception in the same test</td>
<td class="success">0.022s</td>
<td class="success">passed</td>
</tr>
</table>
</div>
</div>
<div id="footer">
<p>
<div>
<label class="hidden" id="label-for-line-wrapping-toggle" for="line-wrapping-toggle">Wrap lines
<input id="line-wrapping-toggle" type="checkbox" autocomplete="off"/>
</label>
</div>Generated by 
<a href="http://www.gradle.org">Gradle 6.6.1</a> at Nov 1, 2020, 10:30:17 AM</p>
</div>
</div>
</body>
</html>
