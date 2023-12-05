/* Write 2 implementations of the following interface, that model a mathematical set of integers. */

trait FunctionalSet:

  /* Adds element if it is not present and returns the new set */
  def add(elem: Int): FunctionalSet

  /* Removes element if it is present and returns the new set */
  def remove(elem: Int): FunctionalSet

  /* Returns true if element is present and false otherwise */
  def contains(elem: Int): Boolean

  /* Removes all values that do not satisfy the filtering function and returns the new set */
  def filter(f: Int => Boolean): FunctionalSet

  /* Returns the union with the other set */
  def union(other: FunctionalSet): FunctionalSet

  /* Returns the intersection with the other set */
  def intersection(other: FunctionalSet): FunctionalSet


/* One of the possible implementations of the set data type is a binary tree.
 *
 * This recursive data structure consists of 2 classes: a trivial EmptySet which represents
 * a leaf of the tree and a NonEmptySet which represents a branch of the tree.
 *
 * The NonEmptySet holds one of the values of the set and has two children:
 * left and right sets which can be either empty or not. */
case class EmptySet() extends FunctionalSet:
  def add(elem: Int): FunctionalSet = NonEmptySet(elem, EmptySet(), EmptySet())
  def remove(elem: Int): FunctionalSet = this
  def contains(elem: Int): Boolean = false
  def filter(f: Int => Boolean): FunctionalSet = this
  def union(other: FunctionalSet): FunctionalSet = other
  def intersection(other: FunctionalSet): FunctionalSet = this


case class NonEmptySet(head: Int, left: FunctionalSet, right: FunctionalSet)
    extends FunctionalSet:

  def add(elem: Int): FunctionalSet =
    if elem < head then NonEmptySet(head, left.add(elem), right)
    else if elem > head then NonEmptySet(head, left, right.add(elem))
    else this

  def remove(elem: Int): FunctionalSet =
    if elem < head then NonEmptySet(head, left.remove(elem), right)
    else if elem > head then NonEmptySet(head, left, right.remove(elem))
    else left.union(right)

  def contains(elem: Int): Boolean =
    if elem < head then left.contains(elem)
    else if elem > head then right.contains(elem)
    else true

  def filter(f: Int => Boolean): FunctionalSet =
    val filteredTail = left.filter(f).union(right.filter(f))
    if f(head) then filteredTail.add(head) else filteredTail

  def union(other: FunctionalSet): FunctionalSet =
    right.union(left.union(other.add(head)))

  def intersection(other: FunctionalSet): FunctionalSet =
    val tailIntersection =
      right.intersection(other).union(left.intersection(other))
    if other.contains(head) then tailIntersection.add(head)
    else tailIntersection


/* Another possible implementation of the set data type is through a
 * characteristic function.
 *
 * This function returns true for all values in the set and false otherwise.
 *
 * This way it is possible to model among others infinite sets, such as the
 * set of all positive integers. */
case class CharacteristicSet(characteristics: Int => Boolean)
    extends FunctionalSet:

  def add(elem: Int): FunctionalSet =
    if characteristics(elem) then this
    else CharacteristicSet((x: Int) => characteristics(x) || x == elem)

  def remove(elem: Int): FunctionalSet =
    if !characteristics(elem) then this
    else CharacteristicSet((x: Int) => characteristics(x) && x != elem)

  def contains(elem: Int): Boolean = characteristics(elem)

  def filter(f: Int => Boolean): FunctionalSet =
    CharacteristicSet((x: Int) => characteristics(x) && f(x))

  def union(other: FunctionalSet): FunctionalSet =
    CharacteristicSet((x: Int) => characteristics(x) || other.contains(x))

  def intersection(other: FunctionalSet): FunctionalSet =
    CharacteristicSet((x: Int) => characteristics(x) && other.contains(x))
