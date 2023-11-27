def testCountChangeWith(
    countChange: (Int, List[Int]) => Int,
    suite: munit.FunSuite
)(using
    loc: munit.Location
): Unit =

  suite.test("trivial"):
    suite.assertEquals(
      countChange(0, List(1)),
      1,
      "There is a single way to collect no money"
    )

  suite.test("single coin"):
    suite.assertEquals(
      countChange(5, List(1)),
      1,
      "There is a single way to collect money with 1 coin"
    )

  suite.test("4 cents with coins 1 and 2"):
    suite.assertEquals(
      countChange(4, List(1, 2)),
      3,
      "There are 3 ways to collect 4 money with 1 and 2 coins"
    )

  suite.test("10 cents with eurocents"):
    suite.assertEquals(
      countChange(10, List(5, 2, 1)),
      10,
      "There are 10 ways to collect 10 cents with eurocents"
    )

  suite.test("1 euro with eurocents"):
    suite.assertEquals(
      countChange(100, List(1, 2, 5, 10, 20, 50, 100, 200)),
      4563,
      "There are 4563 ways to assemble an euro with eurocents"
    )


class CountChangeTestImperative extends munit.FunSuite:
  testCountChangeWith(CountChange.countChange_imperative, this)


class CountChangeTestFunctional extends munit.FunSuite:
  testCountChangeWith(CountChange.countChange_functional, this)


class CountChangeTestParallel extends munit.FunSuite:
  testCountChangeWith(CountChange.countChange_parallel, this)
