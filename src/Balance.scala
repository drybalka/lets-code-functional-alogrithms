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
  def isBalanced_imperative(brackets: String): Boolean = ???

  /* Try solving the problem using a purely functional approach.
   * One possibility is to use recursion.
   * For some extra challenge (which may however be helpful for parallel implementation)
   * try implementing this function using a single `brackets.foldLeft` call. */
  def isBalanced_functional(brackets: String): Boolean = ???

  /* You may try the general parallel algorithm described in the readme.
   * If the given string is longer than some threshold then split it in 2,
   * process the halves in parallel and then reduce the 2 outputs into one.
   * In the opposite case the overhead of spawning new threads is not worth it
   * and the string should be processed sequentially. */
  def isBalanced_parallel(brackets: String): Boolean = ???
