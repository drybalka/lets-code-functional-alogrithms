import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


/* Find the number of ways to collect a sum of money given a list of coin denominations.
 * There is no coin limit and the order is not important.
 * For example, there are 3 ways to collect 4 with coins 1 and 2: 1+1+1+1, 1+1+2, and 2+2. */
object CountChange:

  /* Any algorithm is fine here, I recommend you use whatever feels natural and comfortable.
   * If you want some diversity you may try to use dynamic programming here. */
  def countChange_imperative(money: Int, coins: List[Int]): Int = ???

  /* The classical way of solving this problem functionally is recursion.
   * Just remember to avoid return statements! */
  def countChange_functional(money: Int, coins: List[Int]): Int = ???

  /* You may try the general parallel algorithm described in the readme.
   * If the expected sequential calculation for the given arguments is long then split the problem in 2,
   * process the halves in parallel and finally reduce the 2 results into one.
   * In the opposite case the overhead of spawning new threads for the parallel calculation
   * is not worth it and the string should be processed sequentially.
   *
   * Try to come up with a sensible heuristics for the threshold condition on the input arguments.
   * Note, that both extreme cases (no money and no coins) are trivial to compute. */
  def countChange_parallel(money: Int, coins: List[Int]): Int = ???
