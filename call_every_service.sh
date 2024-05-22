#!/usr/bin/env sh

for i in {1..100}; do curl -s http://192.168.59.100:31595/category/v1/categories; done > /dev/null
for i in {1..100}; do curl -s http://192.168.59.100:31595/product/v1/products; done > /dev/null
for i in {1..100}; do curl -s http://192.168.59.100:31595/user/v1/users/1; done > /dev/null

