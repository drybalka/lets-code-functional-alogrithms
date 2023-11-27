import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


object Balance:

  @annotation.nowarn
  def isBalanced_imperative(brackets: String): Boolean =
    var depth = 0
    for bracket <- brackets do
      if bracket == '(' then depth += 1
      else if bracket == ')' then depth -= 1
      if depth < 0 then return false

    return depth == 0

  def isBalanced_functional(brackets: String): Boolean =
    def isBalanced(brackets: List[Char], depth: Int): Boolean =
      if depth < 0 then false
      else
        brackets match
          case Nil         => depth == 0
          case '(' :: tail => isBalanced(tail, depth + 1)
          case ')' :: tail => isBalanced(tail, depth - 1)
          case _ :: tail   => isBalanced(tail, depth)

    isBalanced(brackets.toList, 0)

  def isBalanced_parallel(brackets: String): Boolean =
    val threshold = 10 max (brackets.length() / 16)

    def calcMinAndDeltaDepth(brackets: String): Future[(Int, Int)] =
      if brackets.length < threshold then
        Future {
          brackets.foldLeft((0, 0)) { (minAndDeltaDepth, char) =>
            val (minDepth, deltaDepth) = minAndDeltaDepth
            char match
              case '(' => (minDepth, deltaDepth + 1)
              case ')' => (minDepth min deltaDepth - 1, deltaDepth - 1)
              case _   => (minDepth, deltaDepth)
          }
        }
      else
        val (leftHalf, rightHalf) = brackets.splitAt(brackets.length / 2)
        for
          (leftMinDepth, leftDeltaDepth) <- calcMinAndDeltaDepth(leftHalf)
          (rightMinDepth, rightDeltaDepth) <- calcMinAndDeltaDepth(rightHalf)
        yield (
          leftMinDepth min leftDeltaDepth + rightMinDepth,
          leftDeltaDepth + rightDeltaDepth
        )

    Await.result(calcMinAndDeltaDepth(brackets), 10.seconds) == (0, 0)
