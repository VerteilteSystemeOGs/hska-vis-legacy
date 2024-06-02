#!/usr/bin/env sh
MINIKUBE_IP=$(minikube ip)

for i in {1..100}; do curl -s http://$MINIKUBE_IP/category/v1/categories; done > /dev/null
for i in {1..100}; do curl -s http://$MINIKUBE_IP/product/v1/products; done > /dev/null
for i in {1..100}; do curl -s http://$MINIKUBE_IP/user/v1/users/1; done > /dev/null

