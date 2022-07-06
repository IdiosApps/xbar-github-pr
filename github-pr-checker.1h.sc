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

// For this script we'll use GitHub enterprise ...
// ... so we need to specify our company's hostname, as in http(s)://[hostname]/api/v3
val hostname = "hostname"
val org = "ourOrg"
val repos = List("aRepo", "anotherRepo")
val username = "yourUsername"
val requiredReviewCount = 2

val prs = repos.map(repo => requests.get(
    s"https://${hostname}/api/v3/repos/${org}/${repo}/pulls?",
    headers = Map(
        "Content-Type" -> "application/vnd.github.v3+json",
        "Authorization" -> s"Bearer ${apiKey.get}"
    )))
    .map(response => ujson.read(response.text()))
    .reduceLeft(_.arr ++ _.arr) // be concise in display; combine all repo information

val (myPrs, notMyPrs) = prs.arr
    .filter(pr => pr("draft").bool == false) // didn't find how to filter PRs in above query
    .partition(pr => pr("user")("login").str == username)

def needReviewCount(prs: ArrayBuffer[ujson.Value]): Int = {
    prs.map(pr => (pr("head")("repo")("name").str, pr("number")))
    .map(tupple =>
        val (repo, number) = tupple // tuple unpack workaround
        requests.get(
        s"https://${hostname}/api/v3/repos/${org}/${repo}/pulls/${number}/reviews",
        headers = Map(
            "Content-Type" -> "application/vnd.github.v3+json",
            "Authorization" -> s"Bearer ${apiKey.get}"
    )))
    .map(resp => ujson.read(resp.text()))
    .map(_.arr) 
    .map(reviews => reviews
        .filter(_("state").str == "APPROVED")
        .distinctBy(_("user")("login").str)
    )
    .map(_.size)
    .filter(_ < requiredReviewCount)
    .size
}

if (myPrs.size == 0) {
    println(s"👥 👁️${needReviewCount(notMyPrs)}/${notMyPrs.size}")
} else {
    println(s"👤👁️${needReviewCount(myPrs)}/${myPrs.size}‖👥 👀${needReviewCount(notMyPrs)}/${notMyPrs.size}")
}