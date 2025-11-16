#!/bin/sh
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
VALID_BRANCH_REGEX="^(dev|feature|dependabot)\/.*$"
ALLOWED_PUSH_BRANCHES="^(main|master)$"

# Allow pushing main or master
if echo "$CURRENT_BRANCH" | grep -qE "$ALLOWED_PUSH_BRANCHES"; then
    echo "Pushing main/master branch... OK."
    exit 0
fi

# Check for branch prefix
if ! echo "$CURRENT_BRANCH" | grep -qE "$VALID_BRANCH_REGEX"; then
    echo "\n[POLICY] Invalid branch name!" >&2
    echo "Your branch name must start with the 'dev/', 'feature/', or 'dependabot/' prefix (e.g., 'dev/my-feature')." >&2
    echo "Current branch: '$CURRENT_BRANCH'" >&2
    echo "Push rejected.\n" >&2
    exit 1
fi

echo "Branch name '$CURRENT_BRANCH' is valid. Proceeding with push..."
exit 0
