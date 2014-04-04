#!/bin/bash
java -cp "bin/tea.jar:lib/*:lib/plugins/*:$classpath" org.tea.main.MyShell "$@"