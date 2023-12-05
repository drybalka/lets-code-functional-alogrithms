import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


/* Write a function that checks whether a string has balanced parenthesis or not,
 * i.e., every opening '(' has a closing ')' and vice versa.
 * For simplicity only check the balance of round parenthesis and ignore all other chars.
 * For example, strings '(X)', ']()()' are balanced, whereas ')(' and '(()' are not. */
object Balance:

  /* First try solving the problem in any way you want and make sure it passes the tests.
   * This will familiarize you with the requirements and edge cases. */
  @annotation.nowarn
  def isBalanced_imperative(brackets: String): Boolean =
    var depth = 0
    for bracket <- brackets do
      if bracket == '(' then depth += 1
      else if bracket == ')' then depth -= 1
      if depth < 0 then return false

    return depth == 0

  /* Try solving the problem using a purely functional approach.
   * One possibility is to use recursion.
   * For some extra challenge (which may however be helpful for parallel implementation)
   * try implementing this function using a single `brackets.foldLeft` call. */
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

  /* The parallel algorithm is recursive and consists of 2 stages.
   * If the given string is longer than some threshold then split it in 2,
   * process the halves in parallel and then reduce the 2 outputs into one.
   * In the opposite case the overhead of spawning new threads is not worth it
   * and the string should be processed sequentially. */
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
