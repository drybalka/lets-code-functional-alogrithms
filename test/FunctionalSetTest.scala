def testEmptySet(emptySet: FunctionalSet, suite: munit.FunSuite)(using
    loc: munit.Location
): Unit =
  suite.test("Empty set contains nothing"):
    suite.assert(!emptySet.contains(0))
    suite.assert(!emptySet.contains(1))
    suite.assert(!emptySet.contains(-1))

  suite.test("Adding to empty set works"):
    suite.assert(emptySet.add(0).contains(0))
    suite.assert(emptySet.add(1).add(0).contains(1))

  suite.test("Removing from empty set does not change it"):
    suite.assert(emptySet.remove(0) == emptySet)

  suite.test("Removing from empty set works"):
    suite.assert(!emptySet.remove(0).contains(0))
    suite.assert(!emptySet.add(0).remove(0).contains(0))

  suite.test("Filtering"):
    suite.assert(!emptySet.remove(0).contains(0))


def testSingleElementSet(set: FunctionalSet, suite: munit.FunSuite)(using
    loc: munit.Location
): Unit =
  suite.test("SingleElementSet contains only 0"):
    suite.assert(set.contains(0))
    suite.assert(!set.contains(1))
    suite.assert(!set.contains(-1))

  suite.test("Adding to SingleElementSet is indepotent"):
    suite.assert(set.add(0) == set)

  suite.test("Adding to SingleElementSet works"):
    suite.assert(set.add(1).contains(1))

  suite.test("Removing from SingleElementSet works"):
    suite.assert(!set.remove(0).contains(0))

  suite.test("Filtering in SingleElementSet works"):
    suite.assert(!set.filter(_ > 1).contains(0))
    suite.assert(set.filter(_ < 1).contains(0))


def testPairOfSets(
    positiveSet: FunctionalSet,
    negativeSet: FunctionalSet,
    suite: munit.FunSuite
)(using
    loc: munit.Location
): Unit =
  suite.test("PositiveSet contains only 0 and 1"):
    suite.assert(positiveSet.contains(0))
    suite.assert(positiveSet.contains(1))
    suite.assert(!positiveSet.contains(-1))

  suite.test("Negative set contains only 0 and -1"):
    suite.assert(negativeSet.contains(0))
    suite.assert(!negativeSet.contains(1))
    suite.assert(negativeSet.contains(-1))

  suite.test("Filtering in non-trivial sets works"):
    suite.assert(positiveSet.filter(_ != 1).contains(0))
    suite.assert(!positiveSet.filter(_ != 1).contains(1))

  suite.test("Union of non-trivial sets works"):
    val unionSet = positiveSet.union(negativeSet)
    suite.assert(unionSet.contains(0))
    suite.assert(unionSet.contains(1))
    suite.assert(unionSet.contains(-1))
    suite.assert(!unionSet.remove(0).contains(0))

  suite.test("Intersection of non-trivial sets works"):
    val intersectionSet = positiveSet.intersection(negativeSet)
    suite.assert(intersectionSet.contains(0))
    suite.assert(!intersectionSet.contains(1))
    suite.assert(!intersectionSet.contains(-1))
    suite.assert(intersectionSet.add(1).contains(1))
    suite.assert(intersectionSet.add(1).contains(1))


class FunctionalTreeSetTest extends munit.FunSuite:
  val zeroTreeSet = NonEmptySet(0, EmptySet(), EmptySet())
  val positiveTreeSet =
    NonEmptySet(0, EmptySet(), NonEmptySet(1, EmptySet(), EmptySet()))
  val negativeTreeSet =
    NonEmptySet(0, NonEmptySet(-1, EmptySet(), EmptySet()), EmptySet())

  testEmptySet(EmptySet(), this)
  testSingleElementSet(zeroTreeSet, this)
  testPairOfSets(positiveTreeSet, negativeTreeSet, this)


class FunctionalCharacteristicSetTest extends munit.FunSuite:
  val zeroCharacteristicSet = CharacteristicSet(_ == 0)
  val positiveCharacteristicSet =
    CharacteristicSet((x: Int) => x == 0 || x == 1)
  val negativeCharacteristicSet =
    CharacteristicSet((x: Int) => x < 1 && x >= -1)

  testEmptySet(CharacteristicSet(_ => false), this)
  testSingleElementSet(CharacteristicSet(_ == 0), this)
  testPairOfSets(positiveCharacteristicSet, negativeCharacteristicSet, this)


class FunctionalMixedSetTest extends munit.FunSuite:
  val positiveTreeSet =
    NonEmptySet(0, EmptySet(), NonEmptySet(1, EmptySet(), EmptySet()))
  val negativeCharacteristicSet =
    CharacteristicSet((x: Int) => x < 1 && x >= -1)

  testPairOfSets(positiveTreeSet, negativeCharacteristicSet, this)
