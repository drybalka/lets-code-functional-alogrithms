def testBalanceWith(isBalanced: String => Boolean, suite: munit.FunSuite)(using
    loc: munit.Location
): Unit =
  suite.test("empty"):
    suite.assert(isBalanced(""), "empty string is balanced")

  suite.test("balanced"):
    suite.assert(isBalanced("()(()())"), "'()(()())' is balanced")

  suite.test("unbalanced by counting"):
    suite.assert(!isBalanced("(()"), "'(()' is missing a closing bracket")

  suite.test("counting is not enough"):
    suite.assert(!isBalanced("())("), "'())(' has unmatched closing bracket")

  suite.test("not only brackets"):
    suite.assert(isBalanced("(2+(2*2))"), "Input can include other symbols")

  suite.test("large example"):
    suite.assert(
      isBalanced(
        """(((((Scala) (is) (a) (((modern))) (multi()-(()paradigm)) ((programming) 
          |(language)) (designed) (to) (express) (common)) (programming) (patterns) 
          |(in a) ((concise), (elegant), and) (type-safe) (way).)) 
          |((It) (seamlessly)( )(integrates) ((features) (of) (object-oriented)) 
          |(and) (functional) (languages)).)""".stripMargin
      ),
      "Input is large"
    )


class BalanceTestImperative extends munit.FunSuite:
  testBalanceWith(Balance.isBalanced_imperative, this)


class BalanceTestFunctional extends munit.FunSuite:
  testBalanceWith(Balance.isBalanced_functional, this)


class BalanceTestParallel extends munit.FunSuite:
  testBalanceWith(Balance.isBalanced_parallel, this)
