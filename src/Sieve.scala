import scala.collection.mutable.ListBuffer


/* Find the first n prime numbers using the Sieve of Eratosthenes algorithm */
object Sieve:

  def calcPrimes_imperative(n: Int): List[Int] =
    val primes = ListBuffer.empty[Int]
    var num = 2
    while primes.length != n do
      if primes.forall(num % _ != 0) then primes += num
      num += 1
    primes.toList

  def calcPrimes_functional(n: Int): List[Int] =
    def primesFrom(n: Int): LazyList[Int] =
      n #:: primesFrom(n + 1).filter(_ % n != 0)

    primesFrom(2).take(n).toList

  def calcPrimes_functional2(n: Int): List[Int] =
    def from(n: Int): LazyList[Int] = n #:: from(n + 1)
    def sieve(numbers: LazyList[Int]): LazyList[Int] =
      numbers.head #:: sieve(numbers.tail.filter(_ % numbers.head != 0))

    sieve(from(2)).take(n).toList
