# Serverless architecture, JVM & GraalVM

## Java Virtual Machine

Die Java Virtual Machine (Java VM oder JVM) ist der Teil der Java-Laufzeitumgebung 
(Java Runtime Environment, JRE) für Java-Programme, der für die Ausführung des Java-Bytecodes verantwortlich ist.

Hierbei wird im Normalfall jedes gestartete Java-Programm in seiner eigenen virtuellen Maschine (VM) ausgeführt.
Die JVM dient dabei als Schnittstelle zur Maschine und zum Betriebssystem und ist für die 
meisten Plattformen verfügbar (Linux, macOS, Windows...).

Vom Java-Compiler erzeugter (plattformunabhängiger) Bytecode wird von einer plattformabhängigen Java Virtual Machine ausgeführt!

## Serverless architecture

Cloud Native

[Bild Servless]


Der Große Nachteil an der JVM ist der sehr Zeitintensive Startvorgang der JVM. 
Gerade im Cloud Zeitalter setzt man zunehmend auf Serverless architecture. Die Startzeit der JVM ist hierbei ein massives problem.
[Vergleichswerte]



## GraalVM

Sprachen der JVM -> 