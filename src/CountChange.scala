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
  def countChange_imperative(money: Int, coins: List[Int]): Int =
    // ways(i, j) is the number of ways to collect j money by using only the first i denominations
    val ways = Array.ofDim[Int](coins.length + 1, money + 1)
    ways(0)(0) = 1

    for
      i <- 1 to coins.length
      coin = coins(i - 1)
      j <- 0 to money
    do
      val waysWithNoCoin = ways(i - 1)(j)
      val waysWithCoin =
        if ways(i).isDefinedAt(j - coin) then ways(i)(j - coin) else 0
      ways(i)(j) = waysWithNoCoin + waysWithCoin

    ways(coins.length)(money)

  /* The classical way of solving this problem functionally is recursion.
   * Just remember to avoid return statements! */
  def countChange_functional(money: Int, coins: List[Int]): Int =
    money match
      case 0          => 1
      case x if x < 0 => 0
      case _ =>
        if coins.isEmpty then 0
        else
          countChange_functional(money, coins.tail) +
            countChange_functional(money - coins.head, coins)

  /* You may try the general parallel algorithm described in the readme.
   * If the expected sequential calculation for the given arguments is long then split the problem in 2,
   * process the halves in parallel and finally reduce the 2 results into one.
   * In the opposite case the overhead of spawning new threads for the parallel calculation
   * is not worth it and the string should be processed sequentially.
   *
   * Try to come up with a sensible heuristics for the threshold condition on the input arguments.
   * Note, that both extreme cases (no money and no coins) are trivial to compute. */
  def countChange_parallel(money: Int, coins: List[Int]): Int =
    val threshold = 10 max (money * 2 / 3)

    def calcWays(money: Int, coins: List[Int]): Future[Int] =
      if money < threshold || coins.isEmpty then
        val ways = countChange_functional(money, coins)
        Future.successful(ways)
      else
        for
          waysWithNoCoin <- calcWays(money, coins.tail)
          waysWithCoin <- calcWays(money - coins.head, coins)
        yield waysWithNoCoin + waysWithCoin

    Await.result(calcWays(money, coins), 10.seconds)
