# jar-exec
> Executes a specified command passed in via an argument. Pipes stdin and stdout. Also puts logs to logs/latest.log to work with Crafty Controller. 

## Building
Make sure you have Java and Maven installed.
```shell
mvn clean
mvn package
```

## Running
Make sure you have Java installed
Copy `jar-exec.jar` and `dependency-jars` or
`jar-exec-jar-with-dependencies.jar` to the location you want to use jar-exec in.

### Example usage:
```shell
java -jar jar-exec-jar-with-dependencies.jar "python test.py"
```