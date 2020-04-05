# Sample commandline application based on <font color="green">_Spring Boot_</font>, written in <font color="green">_Kotlin_</font>

## Technologies
#### Build
We are using **Maven** as build tool. Its much easier to understand and
use than others like **Gradle**. In my opinion for 95% of all projects
**Maven** is the best choice. There are only few cases where **Gradle**
is necessary. You shouldn't make your application more complex than
really needed! If you need to do things, which are not possible with
**Maven** you can switch. Its easy to migrate a project from **Maven**
to **Gradle**.

#### Source Code
The source code is written in **Kotlin**. **Kotlin** hast a lot of
build-in features to avoid boilerplate code and avoid typical traps like
NullPointerExceptions. If you are familiar with java its easy to learn.

As base framework we are using **SpringBoot**. It provides basic
features like dependency injection, logging and working with properties
out of the box. It will also create the productive jar file, with all
dependencies included.

<font color="red">_TODO JCommander_</font>

#### Testing
<font color="red">_TODO Spock (Testing Framework), Groovy_</font>


## How the application works

The application is producing a single jar file (all dependencies
included), which can be used to rename files by their meta tags.