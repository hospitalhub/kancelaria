#!/bin/bash
openssl req -new -x509 -days 1826 -key ca.key -out ca.crt -config openssl.cnf
# zainstaluj certyfikat ca
