# Sample commandline application based on <font color="#0ab4b4">_Spring Boot_</font>, <font color="#0ab4b4">_Kotlin_</font> and <font color="#0ab4b4">_Maven_</font>

## How the application works

The application is producing a single jar file (all dependencies
included), which can be used to rename image files by their meta tags. It's a commandline application, so you can
start it via ```java -jar rename-files.jar```. Without commands/parameters (or invalid ones) a description with all
available commands will be displayed. 

Run ```java -jar rename-files.jar -i``` to get some examples and details information about the application.

## global usage on windows

# Releases

## 1.0.3
+ Timestamp will taken from ExifSubIfDDirectory.TAG_DATETIME
+ Status report on the command line after rename process
+ Show command overview if no command is selected
+ Linebreaks for console logs added

## 1.0.2
Initial version


# TODO

## Technical
- // TODO custom annotation required commands validation
- Unittest for exception handling!
- Example controller

## New Features
- TODO parse from file name, sometimes the timestamp is in the file name not in the exif data!!