import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global


object RunningMax:

  def runningMax_imperative(input: Vector[Int]): Vector[Int] =
    val output = Array.ofDim[Int](input.length)
    output(0) = input(0)
    for i <- 1 until input.length
    do output(i) = output(i - 1) max input(i)
    output.toVector

  def runningMax_functional(input: Vector[Int]): Vector[Int] =
    input.tail.foldLeft(Vector(input.head)) { (output, elem) =>
      output :+ (elem max output.last)
    }

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
