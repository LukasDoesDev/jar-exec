# Moved to [GitLab](https://gitlab.com/LukasDoesDev/jar-exec)

# jar-exec
> Executes a specified command passed in via an argument. Pipes stdin and stdout. Also puts logs to logs/latest.log to work with Crafty Controller. 

[![forthebadge made-with-java](https://forthebadge.com/images/badges/made-with-java.svg)](https://java.com/)

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Building
Make sure you have Java and Maven installed.
```shell
mvn clean
mvn package
```

## Running
Make sure you have Java installed. Copy `jar-exec-jar-with-dependencies.jar`
or `jar-exec.jar` and the `dependency-jars` directory to the location you
want to use jar-exec in.

You can put `--disable-dt-log` before the command to get run (Example: `java -jar target/jar-exec-jar-with-dependencies.jar --disable-dt-log "python tester.py"`) to disable date and time log information if the application you are running does that, see Example Logs section for more.

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
or from [the tester.py file](./tester.py):

## Example Logs
With the `--disable-dt-log` flag:
```
INFO ! Disabled date and time log info
INFO ! Launching with: python tester.py
INFO ! Launch dir: /home/luukas/dev/real-projects/jar-exec
stdout: hello!
testing
stdout: TESTING
stderr: stderr test
stdout: multiline test
stdout: second line
stdout: async output test
stop
INFO ! program quit
```
Without it:
```
2021-04-08 22:15:32.643591167 - INFO ! Launching with: python tester.py
2021-04-08 22:15:32.645920828 - INFO ! Launch dir: /home/luukas/dev/real-projects/jar-exec
2021-04-08 22:15:32.852700262 - stdout: hello!
testing
2021-04-08 22:15:35.994191611 - stdout: TESTING
2021-04-08 22:15:35.994257576 - stderr: stderr test
2021-04-08 22:15:35.994503240 - stdout: multiline test
2021-04-08 22:15:35.994712996 - stdout: second line
2021-04-08 22:15:37.995154164 - stdout: async output test
stop
2021-04-08 22:15:40.004240022 - INFO ! program quit
```
