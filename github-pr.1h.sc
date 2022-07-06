#!/usr/bin/env /opt/homebrew/bin/scala-cli

// xbar metadata
// # <xbar.title>Github PR checker</xbar.title>
// # <xbar.desc>Counts unreviewed PRs</xbar.desc>
// # <xbar.author>James Clark</xbar.author>
// # <xbar.author.github>IdiosApps</xbar.author.github>
// # <xbar.dependencies>java,scala,scala-cli</xbar.dependencies>
// # <xbar.version>1.0.0</xbar.version>
// xbar config
// xbar scripts can get env vars when either entered 1. in UI, or 2. directly here
// # <xbar.var>string(XBAR_GITHUB_API_KEY=""): Github Personal Access Token</xbar.var>

// Can run file manually, or refresh xbar to check changes:
    // `scala-cli ./github-pr.1h.sc`
// Keep imports at the top of the script
import $ivy.`com.lihaoyi::requests:0.7.1`
import $ivy.`com.lihaoyi::ujson:2.0.0`

val api_key = sys.env.get("XBAR_GITHUB_API_KEY")
if (api_key.isEmpty) {
    println("Please provide Github PAT")
    System.exit(0)
}

val resp = requests.get("https://api.github.com/users/baeldung")
val data = ujson.read(resp.text())
println(data("login"))


