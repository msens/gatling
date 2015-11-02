package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BasicSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())



    val uri1 = "http://computer-database.gatling.io"

	val scn = scenario("BasicSimulation")
		// search
		.exec(http("request_0")
			.get("/"))
		.pause(10)
		.exec(http("request_1")
			.get("/computers?f=macbook"))
		.pause(4)
		.exec(http("request_2")
			.get("/computers?p=1&f=macbook")
			.resources(http("request_3")
			.get(uri1 + "/computers?p=2&f=macbook"),
            http("request_4")
			.get(uri1 + "/computers?p=3&f=macbook"),
            http("request_5")
			.get(uri1 + "/computers?p=4&f=macbook")))
		.pause(4)
		.exec(http("request_6")
			.get("/computers?f=macbook+pro"))
		.pause(1)
		.exec(http("request_7")
			.get("/computers/30544"))
		.pause(23)
		// Browse
		.exec(http("request_8")
			.get("/"))
		.pause(9)
		.exec(http("request_9")
			.get("/computers?p=1")
			.resources(http("request_10")
			.get(uri1 + "/computers?p=2"),
            http("request_11")
			.get(uri1 + "/computers?p=3"),
            http("request_12")
			.get(uri1 + "/computers?p=4"),
            http("request_13")
			.get(uri1 + "/computers?p=5"),
            http("request_14")
			.get(uri1 + "/computers?p=6"),
            http("request_15")
			.get(uri1 + "/computers?p=7"),
            http("request_16")
			.get(uri1 + "/computers?p=8"),
            http("request_17")
			.get(uri1 + "/computers?p=9"),
            http("request_18")
			.get(uri1 + "/computers?p=10")))
		.pause(21)
		// Edit
		.exec(http("request_19")
			.get("/computers/new"))
		.pause(37)
		.exec(http("request_20")
			.post("/computers")
			.formParam("name", "ComputerMichiel")
			.formParam("introduced", "20151102")
			.formParam("discontinued", "20151108")
			.formParam("company", "1")
			.check(status.is(400)))
		.pause(5)
		.exec(http("request_21")
			.post("/computers")
			.formParam("name", "ComputerMichiel")
			.formParam("introduced", "20151101")
			.formParam("discontinued", "20151108")
			.formParam("company", "1")
			.check(status.is(400)))
		.pause(5)
		.exec(http("request_22")
			.post("/computers")
			.formParam("name", "ComputerMichiel")
			.formParam("introduced", "20141101")
			.formParam("discontinued", "20151108")
			.formParam("company", "1")
			.check(status.is(400)))
		.pause(12)
		.exec(http("request_23")
			.post("/computers")
			.formParam("name", "ComputerMichiel")
			.formParam("introduced", "2014-11-01")
			.formParam("discontinued", "2015-11-08")
			.formParam("company", "1"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}