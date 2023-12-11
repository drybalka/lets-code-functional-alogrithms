# Let's code functional algorithms

This is a workshop designed to familiarize you with functional programming by solving a few educational algorithmic problems.

The coding will be done in Scala - a high-level statically typed programming language that elegantly supports both imperative and functional styles of coding and feels like a mix of Java and Python.

This workshop is not designed to cover all (or even most) features of Scala, but instead gives you an opportunity to use the most common functional techniques present in many modern languages nowadays, such as, recursion, pattern matching, concurrency, etc..

Most of the problems are ~~shamelessly stolen~~ inspired and adapted from the excellent courses on [functional programming in Scala](https://www.coursera.org/specializations/scala).


## Programming paradigms

In most problems you are asked to write several implementations that solve the problem with the following constrains:

  - **Imperative** - you are free to use any techniques familiar to you, e.g., mutable objects/states, loops, etc..
    However, you should still adhere to good coding practices and avoid `null` (use Option instead) or typecasting.
    This part is meant to introduce you to the problem, its edge cases, as well as to provide an easy win.

  - **Functional** - you should adhere to functional style, which means among other:
    - Functions must be pure (have no side-effects and depend only on input arguments)
    - All data/objects/collections must be immutable
    - Variables may not be reassigned (no `var` keyword)
    - No early returns (no `return` keyword)
    
    Make use of recursion, functional language constructs such as `match` and `for-yield`, methods over collections such as `map`, `filter`, `fold`, etc., and remember that every expression returns a value.

  - **Parallel** - you should write an algorithm that utilizes multiple cores of your CPU to speed up the calculation.
    
    In general, for any parallel algorithm you have to do the following first:
    - Choose a threshold for input arguments below which computations should be done sequentially, as parallelism does not bring speedup anymore due to the cost of spawning and interacting with a new thread
    - Find a way to split the problem into 2 halves (preferably of the same computational cost), so that you can process both halves recursively and then reduce the 2 sub-results into the final result

    Given this you may use the following general structure for a parallel algorithm: 
    - Define a private function that will return `Future` of the result
    - If the input is below threshold then perform computations sequentially and return the result wrapped in `Future.successful` (resolved future)
    - Otherwise split the problem in 2, process both halves in parallel by calling the function recursively, and then reduce the 2 `Future` sub-results into the final `Future` result
    - Finally call the private function from the body of your main implementation method and `Await` the result


## Problems

Your task is simple - write implementations and make the tests green.
You may work on problems in any order as they are independent from each other.
I suggest you leave the parallel implementations to the end, as they are slightly more complicated.

The workshop consists of the following problems:
  - **Balance** - classical problem of parenthesis balancing
  - **CountChange** - another classical problem of counting the number of ways to collect a given sum out of coins
  - **FunctionalSet** - implement a recursive and a functional data structures that model a mathematical set
  - **Sieve** - an exercise for using lazy lists where you must find the first n prime numbers
  - **RunningMax** - a simple problem of processing a list that allows a non-trivial parallelization algorithm


## scala-cli

You may be able to run tests directly from your IDE, but it is also quite easy to run them in terminal using `scala-cli` tool (make sure you running them from the project root folder), for example:
```bash
scala-cli test .                            # runs all test suites
scala-cli test . -- "*Balance*"             # runs all test suites for Balance problem
scala-cli test . -- "*Count*Parallel*"      # runs the test suite for parallel solution of CountChange problem
                                            # In general the regex in quotes is used to match test suite names
```
For experiments and debugging you may also use REPL - interactive notebook-like environment with all your definitions available.
```bash
❯ scala-cli repl .          # make sure to run this from the project root

scala> 2+2
val res0: Int = 4

scala> Balance.isBalanced_imperative("(())")
val res1: Boolean = true
```
