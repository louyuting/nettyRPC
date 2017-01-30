#!/bin/bash

echo "start git add commit fetch merge push"
echo "-------------------------------------"
echo "git add -A"
git add -A

echo "git commit -m 'NettyRPC'"
git commit -m 'NettyRPC'

echo "-------------------------------------"
echo "fetch"
git fetch origin master
echo "merge"
git merge origin/master

echo "-------------------------------------"
echo "push"
git push origin master:master
echo "[over]"
