import scala.collection.mutable.ListBuffer


/* Find the first n prime numbers using the Sieve of Eratosthenes algorithm */
object Sieve:

  /* You may use any approach that sieves out non-prime numbers here. */
  def calcPrimes_imperative(n: Int): List[Int] = ???

  /* Guessing the initial size of the list of integers, such that it contains
   * n primes is hard. Then why not take the list containing all integers? =)
   *
   * Build a lazy list containing all primes and then take its first n elements.
   * Use the sieve principle to filter out all non-prime numbers. */
  def calcPrimes_functional(n: Int): List[Int] = ???
