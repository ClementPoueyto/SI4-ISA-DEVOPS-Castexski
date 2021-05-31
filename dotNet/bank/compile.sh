#!/bin/bash

#csc src/*.cs -out:server.exe

mcs src/*.cs -pkg:wcf -out:server.exe
