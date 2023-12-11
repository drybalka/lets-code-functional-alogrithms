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
  def add(elem: Int): FunctionalSet = ???
  def remove(elem: Int): FunctionalSet = ???
  def contains(elem: Int): Boolean = ???
  def filter(f: Int => Boolean): FunctionalSet = ???
  def union(other: FunctionalSet): FunctionalSet = ???
  def intersection(other: FunctionalSet): FunctionalSet = ???


case class NonEmptySet(head: Int, left: FunctionalSet, right: FunctionalSet)
    extends FunctionalSet:
  def add(elem: Int): FunctionalSet = ???
  def remove(elem: Int): FunctionalSet = ???
  def contains(elem: Int): Boolean = ???
  def filter(f: Int => Boolean): FunctionalSet = ???
  def union(other: FunctionalSet): FunctionalSet = ???
  def intersection(other: FunctionalSet): FunctionalSet = ???


/* Another possible implementation of the set data type is through a
 * characteristic function.
 *
 * This function returns true for all values in the set and false otherwise.
 *
 * This way it is possible to model among others infinite sets, such as the
 * set of all positive integers. */
case class CharacteristicSet(characteristics: Int => Boolean)
    extends FunctionalSet:
  def add(elem: Int): FunctionalSet = ???
  def remove(elem: Int): FunctionalSet = ???
  def contains(elem: Int): Boolean = ???
  def filter(f: Int => Boolean): FunctionalSet = ???
  def union(other: FunctionalSet): FunctionalSet = ???
  def intersection(other: FunctionalSet): FunctionalSet = ???
