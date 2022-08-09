#!/usr/bin/env /opt/homebrew/bin/scala-cli

import scala.language.postfixOps
import sys.process.*
import java.io.File

// A convenient way to call the script with a few environment variables
// Can develop on non-MacOs devices

val process = Process("scala-cli github-pr-checker.1h.sc",
  new File("./"),
  "XBAR_GITHUB_API_KEY" -> "abc123" // todo if empty, should be None in script
)

val output = process.!!

println(output)

// TODO point to a fixed org/repo with output like ? 1/2 ? 1/3
if (output.contains("? ??5/5")) {
  println("Output of script is good")
  sys.exit(0)
}
println("Output of script is not as expected")
sys.exit(1)
