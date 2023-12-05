import scala.collection.mutable.ListBuffer


/* Find the first n prime numbers using the Sieve of Eratosthenes algorithm */
object Sieve:

  /* You may use any approach that sieves out non-prime numbers here. */
  def calcPrimes_imperative(n: Int): List[Int] =
    val pool = ListBuffer.range(2, n * n)
    val primes = ListBuffer.empty[Int]
    while primes.length != n do
      val newPrime = pool.head
      primes += newPrime
      pool.filterInPlace(_ % newPrime != 0)
    primes.toList

  def calcPrimes_imperative2(n: Int): List[Int] =
    val primes = ListBuffer.empty[Int]
    var num = 2
    while primes.length != n do
      if primes.forall(num % _ != 0) then primes += num
      num += 1
    primes.toList

  /* Guessing the initial size of the list of integers, such that it contains
   * n primes is hard. Then why not take the list containing all integers? =)
   *
   * Build a lazy list containing all primes and then take its first n elements.
   * Use the sieve principle to filter out all non-prime numbers. */
  def calcPrimes_functional(n: Int): List[Int] =
    def primesFrom(n: Int): LazyList[Int] =
      n #:: primesFrom(n + 1).filter(_ % n != 0)

    primesFrom(2).take(n).toList

  def calcPrimes_functional2(n: Int): List[Int] =
    def from(n: Int): LazyList[Int] = n #:: from(n + 1)
    def sieve(numbers: LazyList[Int]): LazyList[Int] =
      numbers.head #:: sieve(numbers.tail.filter(_ % numbers.head != 0))

    sieve(from(2)).take(n).toList
