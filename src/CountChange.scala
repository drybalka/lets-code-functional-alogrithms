import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


/* Find the number of ways to collect a sum of money given a list of coin denominations */
object CountChange:

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

  def countChange_functional(money: Int, coins: List[Int]): Int =
    money match
      case 0          => 1
      case x if x < 0 => 0
      case _ =>
        if coins.isEmpty then 0
        else
          countChange_functional(money, coins.tail) +
            countChange_functional(money - coins.head, coins)

  /* Note that threshold may depend on both money and coins */
  def countChange_parallel(money: Int, coins: List[Int]): Int =
    val threshold = 10 max (money * 2 / 3)

    def calcWays(money: Int, coins: List[Int]): Future[Int] =
      if money < threshold || coins.isEmpty then
        Future {
          countChange_functional(money, coins)
        }
      else
        for
          waysWithNoCoin <- calcWays(money, coins.tail)
          waysWithCoin <- calcWays(money - coins.head, coins)
        yield waysWithNoCoin + waysWithCoin

    Await.result(calcWays(money, coins), 10.seconds)
