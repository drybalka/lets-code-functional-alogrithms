import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


/* Write a function that for each position in a given input sequence calculates the running max
 * and returns the resulting vector.
 * The running max for some position is the maximum out of all numbers in the input
 * up to and including this position.
 * For example, the running max vector for the input [2,1,4,5,3] is [2,2,4,5,5]. */
object RunningMax:

  /* You may want to use a mutable array to build the output. */
  def runningMax_imperative(input: Vector[Int]): Vector[Int] = ???

  /* Use functional methods to produce the output.
   * Can you make it a one-liner? */
  def runningMax_functional(input: Vector[Int]): Vector[Int] = ???

  /* The principle of this parallel algorithm is similar to the general scheme:
   * it also involves recursive splitting of input until some threshold,
   * some sequential computations, and combining of results.
   *
   * However, this time you are missing some information to immediately produce
   * the output in the sequential step.
   *
   * The solution to this problem is actually doing 2 parallelizable runs over
   * the input - once computing the missing information, and secondly actually
   * producing the desired output.
   *
   * Make sure to use the identical splitting in both runs!
   * Or alternatively on the first run produce some data structure that
   * already contains the splits and then simply convert it to the desired output
   * on the second run. */
  def runningMax_parallel(input: Vector[Int]): Vector[Int] = ???
