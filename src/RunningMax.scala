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
  def runningMax_imperative(input: Vector[Int]): Vector[Int] =
    val output = Array.ofDim[Int](input.length)
    output(0) = input(0)
    for i <- 1 until input.length
    do output(i) = output(i - 1) max input(i)
    output.toVector

  /* Use functional methods to produce the output. */
  def runningMax_functional(input: Vector[Int]): Vector[Int] =
    input.tail.foldLeft(Vector(input.head)) { (output, elem) =>
      output :+ (elem max output.last)
    }

  /* The principle of this parallel algorithm is the similar as for other problems:
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
  def runningMax_parallel(input: Vector[Int]): Vector[Int] =
    val threshold = 10 max (input.length / 16)

    trait Tree:
      val maxElem: Int

    case class Node(left: Tree, right: Tree) extends Tree:
      val maxElem = left.maxElem max right.maxElem

    case class Leaf(content: Vector[Int]) extends Tree:
      val maxElem = content.max

    def buildTree(input: Vector[Int]): Future[Tree] =
      if input.length < threshold then Future { Leaf(input) }
      else
        val (left, right) = input.splitAt(input.length / 2)
        for
          leftTree <- buildTree(left)
          rightTree <- buildTree(right)
        yield Node(leftTree, rightTree)

    def runningMax(input: Vector[Int], previousMax: Int): Vector[Int] =
      val newMax = input.head max previousMax
      if input.tail.isEmpty then Vector(newMax)
      else newMax +: runningMax(input.tail, newMax)

    def convertTree(tree: Tree, previousMax: Int): Future[Vector[Int]] =
      tree match
        case Leaf(content) =>
          Future { runningMax(content, previousMax) }
        case Node(left, right) =>
          for
            leftVec <- convertTree(left, previousMax)
            rightVec <- convertTree(right, previousMax max left.maxElem)
          yield leftVec ++ rightVec

    Await.result(
      for
        tree <- buildTree(input)
        output <- convertTree(tree, 0)
      yield output,
      10.seconds
    )
