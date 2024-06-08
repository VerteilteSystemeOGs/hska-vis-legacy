#!/usr/bin/env sh

USER_PODS=$(kubectl get pods -n default | grep user | cut -d' ' -f1) K6_WEB_DASHBOARD=true MINIKUBE_IP=$(minikube ip) k6 run script.js

