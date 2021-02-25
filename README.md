# jar-exec
> Executes a specified command passed in via an argument. Pipes stdin and stdout. Also puts logs to logs/latest.log to work with Crafty Controller. 

[![forthebadge made-with-java](http://ForTheBadge.com/images/badges/made-with-java.svg)](https://java.com/)

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Building
Make sure you have Java and Maven installed.
```shell
mvn clean
mvn package
```

## Running
Make sure you have Java installed. Copy `jar-exec.jar` and the `dependency-jars` directory or
`jar-exec-jar-with-dependencies.jar` to the location you want to use jar-exec in.

### Example usage:
If you copied the jar with the dependencies:
```shell
java -jar jar-exec-jar-with-dependencies.jar "python tester.py"
```
If you copied `jar-exec.jar` and the `dependency-jars` directory:
```shell
java -jar jar-exec.jar "python tester.py"
```

You can download tester.py from https://www.thatonelukas.tk/files/jar-exec/tester.py
or get the file contents from here:
```py
#!/usr/bin/python
import time
import sys

sys.stdout.write('hello!\n')
sys.stdout.flush()
s = sys.stdin.readline().strip()

while s not in ['break', 'quit']:
    sys.stdout.write('Input: "' + s + '" and output: "' + s.upper() + '"\n')
    sys.stdout.flush()
    time.sleep(2)
    sys.stdout.write('Test if it works even after I executed command\n')
    sys.stdout.flush()

    fo = open("tester.txt", "a")
    fo.write('Input: "' + s + '" and output: "' + s.upper() + '"\n')
    fo.close()
    s = sys.stdin.readline().strip()
```
