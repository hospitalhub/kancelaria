#!/bin/bash
openssl req -new -key ia.key -out ia.csr -config openssl.cnf
# zainstaluj certyfikat ca
