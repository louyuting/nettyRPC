#!/bin/bash

echo "start git add commit fetch merge push"
echo "-------------------------------------"
echo "git add -A"
git add -A

echo "git commit -m 'spring'"
git commit -m 'spring'

echo "-------------------------------------"
echo "fetch and merge"
git fetch origin master
git merge origin/master

echo "-------------------------------------"
echo "push"
git push origin master:master








