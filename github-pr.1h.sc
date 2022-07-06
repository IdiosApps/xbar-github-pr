#!/usr/bin/env /opt/homebrew/bin/scala-cli

// xbar config needs to be commented out for scala-cli
// # <xbar.title>Github PR checker</xbar.title>
// # <xbar.desc>Counts unreviewed PRs</xbar.desc>
// # <xbar.author>James Clark</xbar.author>
// # <xbar.author.github>IdiosApps</xbar.author.github>
// # <xbar.dependencies>java,scala,scala-cli</xbar.dependencies>
// # <xbar.version>1.0.0</xbar.version>

// Can run file manually, or refresh xbar to check changes:
    // `scala-cli ./github-pr.1h.sc`

import $ivy.`com.lihaoyi::requests:0.7.1`
import $ivy.`com.lihaoyi::ujson:2.0.0`

val resp = requests.get("https://api.github.com/users/baeldung")
val data = ujson.read(resp.text())
println(data("login"))


