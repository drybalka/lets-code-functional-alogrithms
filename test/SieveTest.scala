def testSieveWith(calcPrimes: Int => List[Int], suite: munit.FunSuite)(using
    loc: munit.Location
): Unit =
  suite.test("First 10 primes"):
    suite.assertEquals(calcPrimes(10), List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29))


class SieveTestImperative extends munit.FunSuite:
  testSieveWith(Sieve.calcPrimes_imperative, this)


class SieveTestFunctional extends munit.FunSuite:
  testSieveWith(Sieve.calcPrimes_functional, this)
