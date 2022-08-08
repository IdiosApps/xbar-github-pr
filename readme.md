# Introduction

Always know how many PRs there are, how many need reviews, and if you need to merge your own PRs!

- Install xbar
- Install this plugin by downloading `github-pr-checker.1h.sc`
- Set the environment variable `XBAR_GITHUB_API_KEY` with a PAT generated in GitHub (which has repo access)
- Customise the org/repos/username etc.

## Public orgs/repos

- Enterprise repositories will interact with API of URL `https://$hostname/api/v3/`
- Non-enterprise public repositories will interact with API of URL `https://api.github.com/repos/`, 
  - e.g. you can `curl https://api.github.com/repos/com-lihaoyi/mill/pulls`
- https://$hostname/api/v3/repos/$org/$repo/pulls

# Development

Before opening in an IDE (IntelliJ / VS Code), run `scala-cli setup-ide .` See more info on scala-cli IDE integration [here](https://scala-cli.virtuslab.org/docs/commands/setup-ide/).

Run manually with `scala-cli ./github-pr.1h.sc`

# Goals

- [x] a
- [ ] add tests
  - [ ] Public only for now, so no PAT hanging around
- [ ] GitHub PAT won't be needed for some repos
  - [ ] Enterprise => needs PAT
  - [ ] Non-enterprise, public => no PAT
  - [ ] Non-enterprise, private => needs PAT
    - Probably have to log on HTTP forbidden/unauthennticated 