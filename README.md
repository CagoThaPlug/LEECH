# Comprehensive Guide to Learning Java and Writing RuneLite Plugins



---


Welcome to the Comprehensive Guide to Learning Java and Writing RuneLite Plugins! This guide is designed to provide you with a solid foundation in Java programming and help you get started with developing plugins for the RuneLite client. Whether you're a beginner or have some programming experience, this guide will walk you through the essentials and provide you with examples and resources to enhance your learning.

## Table of Contents

- [Section 1: Java Fundamentals](#section-1-java-fundamentals)
  - [1.1 Understanding Java](#11-understanding-java)
  - [1.2 Setting up the Development Environment](#12-setting-up-the-development-environment)
  - [1.3 Basics of Java Syntax](#13-basics-of-java-syntax)
  - [1.4 Object-Oriented Programming (OOP)](#14-object-oriented-programming-oop)
  - [1.5 Exception Handling](#15-exception-handling)
  - [1.6 Multi Threading](#16-multi-threading)

- [Section 2: RuneLite Plugin Development](#section-2-RuneLite-plugin-development)
  - [2.1 Introduction to RuneLite](#21-introduction-to-RuneLite)
  - [2.2 Setting up the Development Environment](#22-setting-up-the-development-environment)
  - [2.3 Creating Your First RuneLite Plugin](#23-creating-your-first-RuneLite-plugin)
  - [2.4 Interacting with the RuneScape Game Client](#24-interacting-with-the-runescape-game-client)
  - [2.5 Advanced Plugin Features](#25-advanced-plugin-features)

- [Section 3: External Plugin Informative](#section-3-external-plugin-informative)
  - [3.1 External Plugin Warning](#31-external-plugin-warning)
  - [3.2 EthanVann Plugin API](#32-ethanvann-plugin-api)
  - [3.3 Kotori Plugin Loader](#33-kotori-plugin-loader)
  - [3.4 Game Pack Reflection](#34-game-pack-reflection)
  - [3.5 Revision updates](#35-revision-updates)
- [Credits](#credits)
- [TODO](#todo)
- [Additonal Resources](#additional-resources)
- [Contact Info](#contact-info)

---

## Section 1: Java Fundamentals

### 1.1 Understanding Java

Java is a widely-used programming language known for its simplicity, portability, and versatility. In this section, you will learn the basics of Java, including its features, history, and advantages as a language.

#### Features of Java

Java is a general-purpose programming language that is used to develop a wide range of applications, including desktop, mobile, and web applications. It is a class-based, object-oriented language that is designed to be simple, portable, and secure. Here are some of the key features of Java:

- **Simple**: Java is designed to be simple and easy to learn. It has a concise syntax and a small set of keywords, making it easy to read and write code in Java.
- **Portable**: Java is designed to be portable across different platforms. It is compiled into bytecode, which can be executed on any platform that has a Java Virtual Machine (JVM).
- **Secure**: Java is designed to be secure. It has built-in security features such as automatic memory management and type safety.
- **Object-Oriented**: Java is an object-oriented language. It supports the four pillars of object-oriented programming: encapsulation, abstraction, inheritance, and polymorphism.
- **Multithreaded**: Java is designed to be multithreaded. It supports multithreading, which allows multiple threads to run concurrently.

#### History of Java

Java was originally developed by James Gosling at Sun Microsystems in 1991. It was designed to be a simple, portable, and secure programming language for embedded systems. The first version of Java was released in 1995. Since then, Java has become one of the most popular programming languages in the world.

---

### 1.2 Setting up the Development Environment

To start programming in Java, you need to set up your development environment. In this section, we'll guide you through the process of installing the JDK (Java Development Kit) and configuring your IDE (Integrated Development Environment) for a smooth development experience.

#### Installing JDK 11 from Adoptium

To install JDK 11 from Adoptium, follow these steps:

1. Visit the [Adoptium website](https://adoptium.net/) and navigate to the downloads page.
2. Select the appropriate JDK 11 distribution for your operating system.
3. Download the installer package and run it.
4. Follow the installation wizard instructions to complete the JDK installation.

#### Configuring Intellij IDEA Community Edition

Intellij IDEA is a popular IDE for Java development. In this guide, we'll use Intellij IDEA Community Edition. To configure Intellij IDEA for Java development, follow these steps:

1. Download and install [Intellij IDEA Community Edition](https://www.jetbrains.com/idea/download/).
2. Launch Intellij IDEA after the installation is complete.

#### Setting up a New Project in Intellij IDEA

Once you have Intellij IDEA installed, you can set up a new Java project. Here's how:

1. Open Intellij IDEA and click on "Create New Project" or go to "File" > "New" > "Project".
2. Select "Java" in the left pane and ensure that JDK 11 is selected as the Project SDK.
3. Choose the desired project template and click "Next".
4. Enter the project name and select the project location on your computer.
5. Click "Finish" to create the project.

#### Understanding build.gradle in both Kotlin (KTS) and Groovy

In Java projects, build.gradle files are commonly used for project configuration and dependency management. There are two syntax options for writing build.gradle files: Kotlin (KTS) and Groovy.

##### Kotlin (KTS) Syntax

The Kotlin DSL (KTS) provides a more concise and type-safe syntax for build.gradle files. It is recommended for new projects. Here's an example of a build.gradle.kts file:

```kotlin
// build.gradle.kts (Kotlin DSL)
plugins {
  kotlin("jvm") version "1.5.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  testImplementation(kotlin("test"))
}
```

##### Groovy Syntax

The Groovy syntax is the traditional syntax for build.gradle files and is still widely used. Here's an equivalent example of a build.gradle file written in Groovy:

```groovy
// build.gradle (Groovy DSL)
plugins {
  id 'java'
}

group 'com.example'
version '1.0-SNAPSHOT'

repositories {
  mavenCentral()
}

dependencies {
  implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk8'
  testImplementation 'org.jetbrains.kotlin:kotlin-test'
}
```

Choose the syntax that you are more comfortable with or that aligns with the existing project structure.

---

### 1.3 Basics of Java Syntax

In this section, you'll learn the fundamental building blocks of Java programs. We'll cover topics such as variables, data types, operators, conditional statements, and looping constructs. By understanding these concepts, you'll be able to write basic Java programs and manipulate data effectively.

#### Variables

In Java, variables are used to store data that can be accessed and manipulated throughout the program. They have a specific data type and a name. Here's an example of declaring and initializing a variable:

```java
int age = 25;
```

Data Types
Java has built-in data types to represent different kinds of values. Some common data types include:

`int`: for integer values
`double`: for floating-point values
`boolean`: for boolean values (true or false)
`String`: for representing textual data
`float`: for floating-point values (less precise than double)
`char`: for single characters
`long`: for integer values (larger range than int)
`byte`: for integer values (smaller range than int)
`short`: for integer values (smaller range than int)

Here's an example of using different data types:

```java
int age = 25;
double height = 1.75;
boolean isStudent = true;
String name = "John Doe";
```

#### Operators
Java provides various operators to perform operations on variables and values. Some common operators include:

Arithmetic operators: + (addition), - (subtraction), * (multiplication), / (division), % (modulo)
Comparison operators: == (equality), != (inequality), < (less than), > (greater than), <= (less than or equal to), >= (greater than or equal to)
Logical operators: && (logical AND), || (logical OR), ! (logical NOT)
Assignment operators: =, +=, -=, *=, /=, %= (compound assignment)

```java
int a = 5;
int b = 10;
int sum = a + b; // sum = 15

boolean isTrue = (a > b) && (a != 0); // isTrue = false

int counter = 0;
counter += 1; // counter = 1        
```

#### Conditional Statements
Conditional statements allow your program to make decisions and execute different code blocks based on certain conditions. The if statement is a commonly used conditional statement. Here's an example:

```java
int age = 18;

        if (age >= 18) {
        System.out.println("You are an adult.");
        } else {
        System.out.println("You are a minor.");
        }
```

#### Looping Constructs
Looping constructs allow you to repeat a block of code multiple times. The for loop is commonly used when you know the number of iterations in advance. Here's an example:

```java
for (int i = 0; i < 5; i++) {
        System.out.println("Iteration " + i);
        }
```


---

### 1.4 Object-Oriented Programming (OOP)

Java is an object-oriented programming (OOP) language, which means it emphasizes the use of classes and objects to structure programs. In this section, you'll explore the key concepts of OOP, including classes, objects, inheritance, polymorphism, and encapsulation. You'll learn how to create and use classes, define methods, and apply access modifiers to control the visibility of class members.

#### Classes and Objects

In Java, a class is a blueprint or template that defines the properties (attributes) and behaviors (methods) of objects. An object is an instance of a class. Here's an example of a simple class and its usage:

```java
public class Person {
  // Attributes
  private String name;
  private int age;

  // Constructor
  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Method
  public void sayHello() {
    System.out.println("Hello, my name is " + name + " and I'm " + age + " years old.");
  }
}

  // Usage
  Person person = new Person("John Doe", 25);
person.sayHello();
```

#### Methods and Parameters

Methods are the behaviors of a class. They are used to perform specific actions or calculations. A method can have parameters, which are inputs to the method. Here's an example:

```java
public class Calculator {
  public int add(int a, int b) {
    return a + b;
  }

  public double divide(double numerator, double denominator) {
    if (denominator != 0) {
      return numerator / denominator;
    } else {
      throw new IllegalArgumentException("Cannot divide by zero.");
    }
  }
}

  // Usage
  Calculator calculator = new Calculator();
  int sum = calculator.add(5, 3);
  double result = calculator.divide(10.0, 2.0);

```

#### Access Modifiers

Access modifiers control the visibility and accessibility of class members (variables, methods, constructors) from other parts of the program. Java provides four access modifiers:

- `public`: accessible from anywhere
- `private`: accessible only within the same class
- `protected`: accessible within the same class and subclasses
- default (no explicit modifier): accessible within the same package

#### Inheritance

Inheritance is a mechanism in OOP that allows one class to inherit the properties and methods of another class. The class that is inherited from is called the superclass or parent class, and the class that inherits is called the subclass or child class. Here's an example:

```java
public class Vehicle {
  protected String brand;

  public Vehicle(String brand) {
    this.brand = brand;
  }

  public void start() {
    System.out.println("Starting the " + brand + " vehicle.");
  }
}

public class Car extends Vehicle {
  private int numberOfDoors;

  public Car(String brand, int numberOfDoors) {
    super(brand);
    this.numberOfDoors = numberOfDoors;
  }

  public void drive() {
    System.out.println("Driving the " + brand + " car with " + numberOfDoors + " doors.");
  }
}

  // Usage
  Car car = new Car("Toyota", 4);
car.start();
        car.drive();

```

#### Polymorphism

Polymorphism is the ability of objects of different classes to be treated as objects of a common superclass. It allows you to write code that can work with objects of different types, as long as they are related through inheritance. Here's an example:

```java
public class Shape {
  public void draw() {
    System.out.println("Drawing a shape.");
  }
}

public class Circle extends Shape {
  @Override
  public void draw() {
    System.out.println("Drawing a circle.");
  }
}

public class Square extends Shape {
  @Override
  public void draw() {
    System.out.println("Drawing a square.");
  }
}

  // Usage
  Shape shape1 = new Circle();
  Shape shape2 = new Square();
shape1.draw(); // Output: Drawing a circle.
        shape2.draw(); // Output: Drawing a square.

```

#### Encapsulation

Encapsulation is the principle of bundling data (attributes) and methods (behaviors) together within a class and controlling access to them. It helps maintain the integrity and security of the data. Here's an example:

```java
public class BankAccount {
  private double balance;

  public BankAccount(double initialBalance) {
    this.balance = initialBalance;
  }

  public double getBalance() {
    return balance;
  }

  public void deposit(double amount) {
    balance += amount;
  }

  public void withdraw(double amount) {
    if (amount <= balance) {
      balance -= amount;
    } else {
      System.out.println("Insufficient funds.");
    }
  }
}

  // Usage
  BankAccount account = new BankAccount(1000.0);
  double currentBalance = account.getBalance(); // 1000.0
account.deposit(500.0);
        account.withdraw(200.0);
        double updatedBalance = account.getBalance(); // 1300.0

```

---

### 1.5 Exception Handling

Errors and exceptions are common in software development. Java provides a robust exception handling mechanism to deal with unexpected situations. In this section, you'll learn how to handle runtime exceptions and errors using try-catch blocks, and you'll also discover how to create custom exceptions to handle specific scenarios.

#### Try-Catch Blocks

In Java, you can handle exceptions using try-catch blocks. The code that may throw an exception is placed in the try block, and the catch block catches and handles the exception if it occurs. Here's an example:

```java
try {
        int result = divide(10, 0);
        System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
        System.out.println("An error occurred: " + e.getMessage());
        }
```

#### Handling Multiple Exceptions

You can handle multiple exceptions by using multiple catch blocks. Each catch block can handle a specific type of exception. Here's an example:

```java
try {
        // Code that may throw exceptions
        } catch (IOException e) {
        // Handle IOException
        } catch (SQLException e) {
        // Handle SQLException
        }
```

#### Finally Block

The finally block is used to execute code that should always be run, regardless of whether an exception occurs or not. It is commonly used to release resources or perform cleanup operations. Here's an example:

```java
try {
        // Code that may throw exceptions
        } catch (Exception e) {
        // Handle exceptions
        } finally {
        // Code to be executed regardless of exceptions
        }
```

#### Custom Exceptions

Java allows you to create your own custom exceptions by extending the `Exception` class or one of its subclasses. Custom exceptions can be used to handle specific scenarios in your code. Here's an example:

```java
public class CustomException extends Exception {
  public CustomException(String message) {
    super(message);
  }
}

// Usage
try {
        // Code that may throw CustomException
        throw new CustomException("Custom exception occurred");
        } catch (CustomException e) {
        System.out.println("An error occurred: " + e.getMessage());
        }
```

#### Exception Propagation

When an exception is thrown, it can be caught and handled by a method higher up in the call stack or propagated to the calling method. By default, checked exceptions must be declared in the method signature using the `throws` keyword if they are not caught and handled. Here's an example:

```java
public void method1() throws IOException {
        method2();
        }

public void method2() throws IOException {
        method3();
        }

public void method3() throws IOException {
        throw new IOException("Exception occurred");
        }

// Usage
        try {
        method1();
        } catch (IOException e) {
        System.out.println("An error occurred: " + e.getMessage());
        }
```

---

### 1.6 Multi Threading

Multi-threading is a programming technique that allows a single process to perform multiple tasks concurrently. It is used to improve the performance of applications that perform long-running tasks such as network requests, file I/O, and computations. In this section, you'll learn how to create and manage threads in Java.

#### Creating Threads

In Java, you can create threads by extending the `Thread` class or implementing the `Runnable` interface.

##### Extending the `Thread` class:

Extending the `Thread` class allows you to define a new class that is a subclass of `Thread` and override its `run()` method to define the task the thread will execute. Here's an example:

```java
public class MyThread extends Thread {
  @Override
  public void run() {
    System.out.println("MyThread running");
  }
}
```

##### Implementing the `Runnable` interface:

Implementing the `Runnable` interface requires you to implement the `run()` method defined in the interface. This approach is useful when you want to separate the task logic from the thread class. Here's an example:

```java
public class MyRunnable implements Runnable {
  @Override
  public void run() {
    System.out.println("MyRunnable running");
  }
}
```

#### Starting Threads

To start a thread, you need to create an instance of the thread class and call the `start()` method on it. This will execute the `run()` method on the new thread. Here's an example:

```java
Thread thread1 = new MyThread();
        thread1.start(); // Output: MyThread running

        Thread thread2 = new Thread(new MyRunnable());
        thread2.start(); // Output: MyRunnable running
```

#### Thread States

A thread can be in one of the following states:

- `NEW`: The thread has been created but has not yet started.
- `RUNNABLE`: The thread is executing or ready to execute.
- `BLOCKED`: The thread is blocked waiting for a monitor lock.
- `WAITING`: The thread is waiting indefinitely for another thread to perform a particular action.
- `TIMED_WAITING`: The thread is waiting for another thread to perform a particular action for a specified amount of time.
- `TERMINATED`: The thread has exited and is no longer executing.

Understanding the different thread states is important for managing and synchronizing threads effectively in your Java programs.

#### Synchronizing Threads

When multiple threads are accessing shared resources concurrently, it's crucial to synchronize their access to prevent race conditions and ensure data consistency. Java provides the `synchronized` keyword and `Lock` objects to achieve thread synchronization.

The `synchronized` keyword can be applied to methods or blocks of code to ensure that only one thread can execute them at a time. Here's an example:

```java
public class Counter {
  private int count;

  public synchronized void increment() {
    count++;
  }
}
```

In this example, the `increment()` method is synchronized, which means only one thread can execute it at any given time. This prevents multiple threads from interfering with each other and causing inconsistent results.

Alternatively, you can use `Lock` objects from the `java.util.concurrent.locks` package to achieve more fine-grained control over synchronization. Here's an example:

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
  private int count;
  private Lock lock = new ReentrantLock();

  public void increment() {
    lock.lock();
    try {
      count++;
    } finally {
      lock.unlock();
    }
  }
}
```

In this example, the `ReentrantLock` class is used to create a lock, and the `lock()` and `unlock()` methods are used to acquire and release the lock, respectively. The `try-finally` block ensures that the lock is always released, even if an exception occurs.

Synchronizing threads properly is essential to avoid data corruption and ensure the correctness of concurrent Java programs.

#### Thread Safety

Thread safety refers to the ability of a program or a specific piece of code to perform correctly and produce the expected results when accessed by multiple threads concurrently. Writing thread-safe code is crucial in multi-threaded applications to prevent data corruption and unexpected behavior.

To achieve thread safety, you can use various synchronization techniques, such as the `synchronized` keyword, locks, atomic classes, and concurrent data structures provided by the Java `java.util.concurrent` package. These techniques help ensure that shared data is accessed and modified safely by multiple threads.

When designing multi-threaded applications, it's important to identify shared resources and critical sections of code that need to be synchronized to maintain thread safety. Applying appropriate synchronization mechanisms will help you avoid race conditions, deadlocks, and other concurrency-related issues.

#### Thread Communication

In multi-threaded applications, threads often need to communicate and coordinate with each other. Java provides several mechanisms for thread communication, including:

- **Wait and Notify**: The `wait()` and `notify()` methods of the `Object` class allow threads to wait for a specific condition and notify other threads when that condition is met.
- **Blocking Queues**: The `java.util.concurrent` package provides blocking queue implementations like `LinkedBlockingQueue` and `ArrayBlockingQueue`, which enable threads to exchange data safely.
- **Thread Signaling**: Custom signaling mechanisms can be implemented using shared objects, flags, and synchronization primitives like locks and semaphores.

Understanding and utilizing these mechanisms appropriately is crucial for building robust and efficient multi-threaded applications in Java.

#### Exception Handling in Threads

Exception handling in multi-threaded programs requires careful consideration. When an exception is thrown in a thread, it can terminate the thread's execution and potentially affect the stability of the entire application. To handle exceptions in threads effectively, you can use the following approaches:

- **Catch and Handle Exceptions Locally**: Place the code that may throw exceptions inside a `try-catch` block within the thread's `run()` method to catch and handle exceptions locally. This ensures that exceptions are captured and processed within the thread, preventing it from abruptly terminating.
- **Logging**: Use logging frameworks like `java.util.logging`, Log4j, or SLF4J to log exceptions and related information. This helps in debugging and troubleshooting multi-threaded applications.
- **Thread Uncaught Exception Handler**: Set an uncaught exception handler for threads by calling the `setUncaughtExceptionHandler()` method. This allows you to define a global exception handler that will be invoked whenever an uncaught exception occurs in a thread. The handler can log the exception or take appropriate actions to handle it gracefully.

By implementing proper exception handling strategies, you can ensure that exceptions in threads are caught, logged, and handled appropriately, improving the overall robustness of your Java applications.

#### Summary

In this section, you learned about multi-threading in Java. You discovered how to create threads by extending the `Thread` class or implementing the `Runnable` interface. You also learned about starting threads, thread states, thread synchronization, thread safety, thread communication, and exception handling in threads. Applying these concepts correctly will help you write efficient, reliable, and scalable multi-threaded Java applications.

---

# Section 2: RuneLite Plugin Development

## 2.1 Introduction to RuneLite

RuneLite is an open-source game client for Old School RuneScape. It provides a customizable and feature-rich alternative to the official game client. RuneLite offers various plugins that enhance the gameplay experience by adding new features, improving visuals, and providing useful tools for players.

#### What is RuneLite?

RuneLite is built on the principles of community-driven development and open-source collaboration. It aims to provide a highly customizable and lightweight game client for Old School RuneScape. The RuneLite client is designed to be user-friendly, intuitive, and accessible to players of all skill levels.

#### Architecture and How it Works

RuneLite utilizes a modular architecture that allows developers to create plugins for extending and enhancing the functionality of the client. The client is written in Java and leverages the game's official API to interact with the game server. It provides various hooks and events that plugin developers can utilize to access game data, listen to events, and modify the game's behavior.

#### Benefits of Writing RuneLite Plugins

Writing RuneLite plugins offers several benefits for both developers and players:

- **Customizability**: RuneLite plugins allow players to customize their gaming experience by adding or modifying features according to their preferences.
- **Enhanced Gameplay**: Plugins can provide useful tools, overlays, and visual enhancements that improve gameplay efficiency and overall experience.
- **Community Contributions**: By developing RuneLite plugins, you can contribute to the vibrant RuneLite community and share your creations with fellow players.
- **Learning Experience**: Creating RuneLite plugins is a great way to learn Java programming, software development, and game development concepts.

#### Resources

To learn more about RuneLite and get started with plugin development, check out the following resources:

- [RuneLite Official Website](https://RuneLite.net/)
- [RuneLite GitHub Repository](https://github.com/RuneLite/RuneLite)
- [RuneLite Example Plugin Repository](https://github.com/RuneLite/example-plugin)

The RuneLite Example Plugin repository provides a helpful starting point for developing your own plugins. It offers code examples and a guide to help you understand the plugin development process.

RuneLite's active community and extensive documentation provide further support and resources for plugin development. Feel free to explore, experiment, and create your own RuneLite plugins to enhance your Old School RuneScape experience!

---

## 2.2 Setting up the Development Environment

To develop RuneLite plugins, you need to set up your development environment accordingly. In this section, you'll learn how to download and install RuneLite and configure your IDE to start building your own plugins. We'll cover the necessary libraries, dependencies, and tools.

#### Downloading and Installing RuneLite

To get started with RuneLite plugin development, follow these steps to download and install RuneLite:

1. Visit the [RuneLite website](https://RuneLite.net/) and navigate to the downloads page.
2. Download the RuneLite installer suitable for your operating system.
3. Run the installer and follow the installation instructions.

#### Configuring the IDE

Once you have RuneLite installed, you can configure your Integrated Development Environment (IDE) to start building RuneLite plugins. The recommended IDE for RuneLite plugin development is IntelliJ IDEA. Here's how you can set it up:

1. Download and install [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/).
2. Launch IntelliJ IDEA after the installation is complete.

#### Getting the RuneLite Project from Version Control

To start developing RuneLite plugins, you'll need to obtain the RuneLite project from version control. Follow these steps to get the project:

1. Open IntelliJ IDEA and select "Check out from Version Control" on the welcome screen.
2. Choose the Git option and enter the RuneLite repository URL: `https://github.com/RuneLite/RuneLite.git`.
3. Specify the desired directory to clone the project into.
4. Click "Clone" to initiate the cloning process.

#### Setting Up VM and Program Arguments

To enable specific features and options during RuneLite plugin development, you may need to configure VM and program arguments in IntelliJ IDEA. Here are some commonly used arguments:

- `-ea` (Enable Assertions): Enable assertions in the RuneLite client for debugging purposes.
- `--developer-mode` (Developer Mode): Enable developer mode, which provides additional debugging tools and features.

To configure these arguments in IntelliJ IDEA, go to "Run" > "Edit Configurations", select the "Client" configuration, and specify the desired arguments in the "VM options" and "Program arguments" fields.

#### Bypassing the Jagex Launcher for Development

During RuneLite plugin development, you may want to bypass the Jagex Launcher to directly launch the RuneLite client. The RuneLite Wiki provides detailed instructions on how to bypass the Jagex Launcher for development purposes. Refer to the [Using Jagex Accounts](https://github.com/RuneLite/RuneLite/wiki/Using-Jagex-Accounts) guide for more information.

Please note that RuneLite plugin development requires knowledge of Java programming and familiarity with the RuneLite plugin system. It is recommended to have a basic understanding of Java and IntelliJ IDEA before diving into plugin development.

---

## 2.3 Creating Your First RuneLite Plugin

In this section, you'll learn how to create a basic RuneLite plugin step by step. By the end, you'll have a solid foundation to build more complex plugins for RuneLite.

To create a RuneLite plugin, follow these steps:

#### Step 1: Setting up the Plugin Class

First, create a new Java class for your plugin. You can put it in a package like `com.plugins`. The class should extend the `Plugin` class provided by RuneLite.

```java
package com.plugins;

import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  // Plugin code goes here
}
```

#### Step 2: Adding Dependencies (Optional)

If your plugin requires any additional plugins or external libraries, you can specify them as dependencies using annotations. For example, if your plugin needs the `OtherPlugin` to function, you can add the `@PluginDependency(OtherPlugin.class)` annotation.

```java
@PluginDependency(OtherPlugin.class)
public class ExamplePlugin extends Plugin {
  // Plugin code goes here
}
```

#### Step 3: Injecting Dependencies

To access RuneLite services and configurations, you can use dependency injection. This allows you to conveniently access important objects and settings. For example, you can inject the `Client`, `KeyManager`, and `ExampleConfig` objects into your plugin using the `@Inject` annotation.

```java
import javax.inject.Inject;
import net.RuneLite.client.Client;
import net.RuneLite.client.input.KeyManager;
import net.RuneLite.client.plugins.Plugin;
import net.RuneLite.client.config.ConfigManager;

public class ExamplePlugin extends Plugin {
  @Inject
  private Client client;

  @Inject
  private KeyManager keyManager;

  @Inject
  private ExampleConfig config;

  // Plugin code goes here
}
```

#### Step 4: Configuring the Plugin

If your plugin requires configuration settings, you can create a method annotated with `@Provides` to retrieve the configuration from the `ConfigManager`. This allows you to access the plugin's configuration easily.

```java
import javax.inject.Inject;
import net.RuneLite.client.config.ConfigManager;
import net.RuneLite.client.plugins.Plugin;
import com.google.inject.Provides;

public class ExamplePlugin extends Plugin {
  @Inject
  private ExampleConfig config;

  @Provides
  public ExampleConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(ExampleConfig.class);
  }

  // Plugin code goes here
}
```

#### Step 5: Implementing Lifecycle Methods

Override the `startUp()` and `shutDown()` methods provided by the `Plugin` class. In the `startUp()` method, you can perform initialization tasks for your plugin, such as sending a welcome message to the client's chat or registering event listeners. In the `shutDown()` method, you can perform cleanup tasks or finalize operations, like sending a goodbye message or unregistering event listeners.

```java
import net.RuneLite.api.ChatMessageType;
import net.RuneLite.api.events.ChatMessage;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  // ...

  @Override
  public void startUp() {
    sendClientMessage("Hello " + client.getLocalPlayer().getName() + "!");
    keyManager.registerKeyListener(startButton);
  }

  @Override
  public void shutDown() {
    sendClientMessage("Goodbye " + client.getLocalPlayer().getName() + "!");
    keyManager.unregisterKeyListener(startButton);
  }

  // ...
}
```

#### Step 6: Interacting with the RuneLite Client

To interact with the RuneLite client, you can add UI elements and register event listeners. For example, you can define a `HotkeyListener` to listen for specific hotkey events. When the hotkey is pressed, the associated method will be executed. You can use the `sendClientMessage()` method to send messages to the client's chat.

```java
import net.RuneLite.client.input.KeyManager;
import net.RuneLite.client.util.HotkeyListener;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  @Inject
  private KeyManager keyManager;

  private final HotkeyListener startButton = new HotkeyListener(() -> config.ExampleHotKey()) {
    @Override
    public void hotkeyPressed() {
      sendClientMessage("Start button pressed!");
    }
  };

  // ...

  @Override
  public void startUp() {
    // ...
    keyManager.registerKeyListener(startButton);
  }

  @Override
  public void shutDown() {
    // ...
    keyManager.unregisterKeyListener(startButton);
  }

  public void sendClientMessage(String message) {
    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", message, null);
  }

  // ...
}
```

#### Step 7: Handling Events

You can listen for specific events, such as chat messages, by annotating a method with `@Subscribe`. Inside the event listener method, you can check the event type and perform actions accordingly. For example, if a chat message of type `GAMEMESSAGE` contains the word "test," you can send a response message to the client's chat.

```java
import net.RuneLite.api.ChatMessageType;
import net.RuneLite.api.events.ChatMessage;
import net.RuneLite.client.plugins.Plugin;
import net.RuneLite.client.eventbus.Subscribe;

public class ExamplePlugin extends Plugin {
  // ...

  @Subscribe
  public void onChatMessage(ChatMessage event) {
    if (event.getType() == ChatMessageType.GAMEMESSAGE) {
      String message = event.getMessage();
      if (message.contains("test")) {
        sendClientMessage("Test successful!");
      }
    }
  }

  // ...
}
```

Following these steps will guide you in creating your first RuneLite plugin. It's important to structure your plugin correctly, handle dependencies, and interact with the RuneLite client effectively. With practice and further exploration, you'll be able to develop more advanced and feature-rich plugins for RuneLite.

## 2.4 Interacting with the RuneScape Game Client

RuneLite plugins provide extensive capabilities for interacting with the RuneScape game client, allowing you to access game data, listen to events, and modify game behavior. In this section, we'll explore some of the powerful features available to plugin developers.

#### Accessing Game Client Data

Plugins can access a wide range of game data provided by the RuneLite API. This includes information about the player, game objects, NPCs, the game world, and more. By accessing this data, you can create dynamic and interactive plugins.

For example, you can retrieve the local player's name using the `getClient()` method provided by the `Plugin` class:

```java
String playerName = client.getLocalPlayer().getName();
```

Similarly, you can access other game entities and properties using the appropriate methods and classes provided by the RuneLite API.

#### Listening to Game Events

Plugins can listen to various game events to be notified of changes or specific actions in the game. By subscribing to these events, you can respond accordingly and perform actions based on the game state.

To listen to game events, you need to annotate a method with the `@Subscribe` annotation and specify the event class you want to listen to. Here's an example of listening to the `GameStateChanged` event:

```java
import net.RuneLite.api.events.GameStateChanged;
import net.RuneLite.client.plugins.Plugin;
import net.RuneLite.client.eventbus.Subscribe;

public class ExamplePlugin extends Plugin {
  // ...

  @Subscribe
  public void onGameStateChanged(GameStateChanged event) {
    // Handle the game state change event
  }

  // ...
}
```

By implementing event listeners, you can respond to changes in the game state, player actions, and other important events.

#### Modifying Game Behavior with Hooks

RuneLite exposes various hooks that allow you to modify the behavior of the game client. By using hooks, you can extend the functionality of the game client or customize its behavior to suit your needs.

For example, you can modify the game rendering behavior by accessing the `client` object and setting properties or invoking methods provided by the RuneLite API. Here's an example of modifying the game rendering:

```java
client.setDrawDistance(100);
```

By utilizing hooks, you can enhance the game client's functionality, create custom visualizations, or implement new features.

#### Displaying Overlays

Plugins can display overlays on the game client to provide additional information or visual cues. Overlays are typically used to highlight specific game elements, show timers, or present additional information relevant to the player.

To create an overlay, you can extend the `Overlay` class provided by the RuneLite API and implement the necessary methods. Here's an example:

```java
import net.RuneLite.api.overlay.Overlay;
import net.RuneLite.api.overlay.OverlayLayer;
import net.RuneLite.api.overlay.OverlayPosition;
import net.RuneLite.api.overlay.OverlayUtil;
import java.awt.Graphics2D;

public class ExampleOverlay extends Overlay {
  @Override
  public void render(Graphics2D graphics) {
    // Render the overlay
  }
}
```

You can then register your overlay using the `OverlayManager` in the plugin's `startUp()` method:

```java
@Override
public void startUp() {
        overlayManager.add(overlay);
        }
```

By utilizing overlays, you can provide useful visual information to players within the game client.

#### Responding to User Input

Plugins can respond to user input events, such as mouse clicks or key presses, to provide interactive functionality. By listening to user input events, you can perform actions based on player interactions.

To listen to user input events, you can implement the `MouseListener` or `KeyListener` interfaces provided by the RuneLite API. Here's an example of implementing a mouse click listener:

```java
import net.RuneLite.client.input.MouseListener;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin implements MouseListener {
  // ...

  @Override
  public MouseEvent mouseClicked(MouseEvent event) {
    // Handle the mouse click event
  }

  // ...
}
```

You can then register your plugin as a mouse listener using the `MouseManager` in the plugin's `startUp()` method:

```java
@Override
public void startUp() {
        mouseManager.registerMouseListener(this);
        }
```

By responding to user input events, you can create interactive plugins that allow players to interact with your plugin's features.


---

## 2.5 Advanced Plugin Features

Once you have a solid understanding of the basics, you can explore more advanced features and techniques to take your RuneLite plugin development to the next level. In this section, we'll dive into more advanced concepts and provide guidance on utilizing them effectively.

#### Creating Custom Overlays

Custom overlays allow you to add visual elements to the game client, providing additional information or enhancing the user interface. RuneLite offers a comprehensive set of APIs for creating overlays with various styles and behaviors.

To create a custom overlay, you can extend the `Overlay` class provided by the RuneLite API and implement the necessary rendering logic. Here's an example:

```java
import net.RuneLite.api.overlay.Overlay;
import net.RuneLite.api.overlay.OverlayLayer;
import net.RuneLite.api.overlay.OverlayPosition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class CustomOverlay extends Overlay {
  private static final Color OVERLAY_COLOR = new Color(255, 0, 0, 100);

  @Override
  public Dimension render(Graphics2D graphics) {
    // Perform custom rendering logic here
    graphics.setColor(OVERLAY_COLOR);
    graphics.fillRect(50, 50, 100, 100);

    return null; // Return null to indicate that the overlay should be rendered continuously
  }
}
```

You can configure the overlay's layer and position using annotations such as `@OverlayLayer` and `@OverlayPosition`. By registering your custom overlay with the `OverlayManager`, it will be rendered on top of the game client.

#### Handling Mouse and Keyboard Input

Advanced plugins often require interaction with mouse and keyboard events to provide dynamic and responsive functionality. RuneLite offers robust input handling capabilities that allow you to listen for and respond to user input events.

To handle mouse events, you can implement the `MouseListener` interface provided by the RuneLite API. Here's an example:

```java
import net.RuneLite.client.input.MouseAdapter;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  private final MouseAdapter mouseAdapter = new MouseAdapter() {
    @Override
    public MouseEvent mousePressed(MouseEvent event) {
      // Handle mouse press event
      return event;
    }

    @Override
    public MouseEvent mouseReleased(MouseEvent event) {
      // Handle mouse release event
      return event;
    }

    // Override other mouse event methods as needed
  };

  @Override
  public void startUp() {
    mouseManager.registerMouseListener(mouseAdapter);
  }

  @Override
  public void shutDown() {
    mouseManager.unregisterMouseListener(mouseAdapter);
  }
}
```

Similarly, you can handle keyboard events by implementing the `KeyListener` interface and registering it with the `KeyManager`. This allows you to respond to key presses and releases:

```java
import net.RuneLite.client.input.KeyListener;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  private final KeyListener keyListener = new KeyListener() {
    @Override
    public void keyTyped(KeyEvent event) {
      // Handle key typed event
    }

    @Override
    public void keyPressed(KeyEvent event) {
      // Handle key press event
    }

    @Override
    public void keyReleased(KeyEvent event) {
      // Handle key release event
    }
  };

  @Override
  public void startUp() {
    keyManager.registerKeyListener(keyListener);
  }

  @Override
  public void shutDown() {
    keyManager.unregisterKeyListener(keyListener);
  }
}
```

By leveraging mouse and keyboard input handling, you can create plugins that respond to user actions in real-time.

#### Using External APIs and Libraries

RuneLite plugins can utilize external APIs and libraries to expand their capabilities and integrate with external systems. This allows you to leverage existing tools and services to enhance your plugins.

To use an external API or library, you need to include the necessary dependencies in your plugin's build configuration. This can be done using build tools such as Maven or Gradle. Once the dependencies are added, you can import and utilize the API or library in your plugin code.

For example, if you want to use the Apache HttpClient library to make HTTP requests, you can include the following Maven dependency:

```xml
<dependencies>
  <dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.13</version>
  </dependency>
</dependencies>
```

Then, you can import and use the HttpClient in your plugin code:

```java
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ExamplePlugin extends Plugin {
  private final HttpClient httpClient = HttpClientBuilder.create().build();

  // Use the httpClient to make HTTP requests
}
```

By utilizing external APIs and libraries, you can extend the capabilities of your plugins and integrate with external systems seamlessly.

#### Managing Plugin Configurations and Settings

Plugins often require configuration settings that can be customized by users. RuneLite provides a built-in configuration framework that allows you to easily manage and store plugin configurations.

To create a configuration for your plugin, you can define a configuration class and annotate it with `@ConfigGroup` and `@ConfigItem` annotations. Here's an example:

```java
import net.RuneLite.client.config.Config;
import net.RuneLite.client.config.ConfigGroup;
import net.RuneLite.client.config.ConfigItem;

@ConfigGroup("example")
public interface ExampleConfig extends Config {
  @ConfigItem(
          keyName = "optionEnabled",
          name = "Enable Option",
          description = "Enable a specific option",
          position = 0
  )
  default boolean optionEnabled() {
    return false;
  }
}
```

By using the `ConfigManager`, you can access and modify the plugin's configuration settings within your plugin code:

```java
import javax.inject.Inject;
import net.RuneLite.client.config.ConfigManager;
import net.RuneLite.client.plugins.Plugin;

public class ExamplePlugin extends Plugin {
  @Inject
  private ExampleConfig config;

  @Inject
  private ConfigManager configManager;

  @Override
  public void startUp() {
    boolean optionEnabled = config.optionEnabled();
    // Use the configuration setting
  }

  public void setOptionEnabled(boolean enabled) {
    configManager.setConfiguration("example", "optionEnabled", enabled);
  }
}
```

By leveraging the built-in configuration framework, you can provide a customizable experience for your plugin users.

#### Optimizing Plugin Performance

As your plugins become more complex, it's essential to optimize their performance to ensure smooth gameplay and efficient resource utilization. Here are some tips for optimizing your plugins:

- Minimize the use of CPU-intensive operations within event listeners.
- Avoid frequent polling or unnecessary data updates.
- Dispose of resources properly to prevent memory leaks.
- Optimize rendering and limit the number of overlays drawn simultaneously.
- Use caching and efficient algorithms where applicable.
- Use multithreading and asynchronous operations to offload heavy tasks.
- Profile your plugins to identify potential bottlenecks and areas for improvement.

By following these optimization techniques, you can create high-performance plugins that provide a seamless and responsive experience for users.

---

# Section 3: External Plugin Informative

## 3.1 External Plugin Warning

Here we will explore the usage of the RuneLite API for some not so good things. This may include loading in plugins not meant to be usable through RuneLite's Plugin Hub or Plugins that were removed by Gagex request.

#### Educational Purposes Only

This section is for educational purposes only. We do not condone the usage of these plugins in any way. We are simply showcasing the capabilities of the RuneLite API and how it can be used to create plugins that are not meant to be used through the Plugin Hub. Please use at your own risk.

---

## 3.2 EthanVann Plugin API

EthanVann has made significant contributions to the plugin repository we are creating, showcasing his skills as a RuneLite plugin developer. In this chapter, we'll explore EthanVann's contributions, focusing on his API and the impact it has had on the plugin development community.

## EthanVannAPI usage in SuperGlassMakerPlugin

### Package Dependencies
The following packages are imported by the SuperGlassMakerPlugin:

```java
import com.plugins.Collections.*;
import com.plugins.EthanApiPlugin;
import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.SneakyThrows;
```

### Class Definitions
The `SuperGlassMakerPlugin` class extends the `Plugin` class and is decorated with various annotations that define certain properties of the plugin.

```java
@PluginDescriptor(
        name = "Super Glass Maker",
        description = "",
        enabledByDefault = false,
        tags = {"ethan"}
)
@Slf4j
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(EthanApiPlugin.class)
public class SuperGlassMakerPlugin extends Plugin {
```

- `@PluginDescriptor`: Provides basic information about the plugin, such as its name, a short description, its default enabled state, and related tags.

- `@Slf4j`: This Lombok annotation allows the class to utilize an instance of a logger.

- `@PluginDependency`: This plugin requires the `PacketUtilsPlugin` and `EthanApiPlugin` to function effectively, hence the dependency.

### Class-level Variables
The following fields are declared at the class level:

```java
public int timeout = 0;

@Inject
Client client;

@Inject
EthanApiPlugin api;

@Inject
SuperGlassMakerPluginConfig config;
        
int timesFailed = 0;
```

### Plugin Methods
- `startUp()`: This method is automatically called when the plugin is enabled by the user. Currently, it sets the timeout to 0.

- `shutDown()`: This method is automatically called when the plugin is disabled. Currently, it doesn't have any specific implementation.

- `getConfig(ConfigManager configManager)`: This method returns the configuration settings for this plugin.

- `onGameTick(GameTick event)`: This method is automatically called at every game tick. It contains the main functionality of the plugin, interacting with NPCs, the bank, and glass items in the game.

- `handleSecondary()`: This function is responsible for handling secondary tasks in the game. Without more context, the specific implementation is unknown.

### Game Interactions
The `onGameTick(GameTick event)` method handles various game interactions, such as item picking, NPC interactions, and bank transactions. The usage of WidgetPackets and MousePackets suggests operations related to UI and mouse movements.

Please note that this plugin appears to be designed for a specific game, as it references game-specific items like `Secondary.GIANT_SEAWEED` and `SuperGlassMakerPluginConfig`. To use this plugin with a different game, you may need to replace these items with game-specific equivalents.

### Logging
Throughout the plugin, there are several logging statements used to trace operations and aid in debugging in case of errors or exceptions.

### Game Tick Event Subscription
The plugin subscribes to the game tick event. Each game tick triggers the execution of the `onGameTick` method.

```java
@Subscribe
public void onGameTick(GameTick event) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    // Code here...
}
```

### Bank or Banker Interaction
If no bank widget is active, the plugin searches for a banker NPC or a bank tile object (both having an action named "Bank") nearest to the player. It interacts with the first it finds to open the bank interface.

```java
Optional<NPC> banker = NPCs.search().withAction("Bank").nearestToPlayer();
Optional<TileObject> bank = TileObjects.search().withAction("Bank").nearestToPlayer();
```

### Item Checks
The plugin checks for specific items in the bank necessary for Super Glass Making: Bucket of Sand, Molten Glass, Astral Rune, and a secondary item. It also checks for the required spell widget. If any of these are not found, a corresponding message is sent to the game chat.

```java
Optional<Widget> sand = Bank.search().withId(ItemID.BUCKET_OF_SAND).first();
Optional<Widget> glass = BankInventory.search().withId(ItemID.MOLTEN_GLASS).first();
Optional<Widget> astral = BankInventory.search().withId(ItemID.ASTRAL_RUNE).first();
Optional<Widget> secondary = Bank.search().withId(config.secondary().getId()).first();
Widget make_glass = client.getWidget(14286966);
```

### Failure Handling
If any item check fails, this increases a fail counter. If the fail counter exceeds 2, the plugin is stopped.

```java
timesFailed++;

if (timesFailed > 2) {
    EthanApiPlugin.stopPlugin(this);
}
```

### Item Withdrawal
If all necessary items are found, the plugin begins withdrawing these items in precise amounts from the bank.

```java
BankInteraction.withdrawX(sand.get(), config.secondary().getSandAmount());
```

### Secondary Item Handling
An additional method, `handleSecondary()`, takes care of dealing with different types of the secondary element. If anything goes wrong in this function, it returns false which signals the main routine to stop the plugin.

```java
Optional<Widget> secondary = Bank.search().withId(config.secondary().getId()).first();
if (secondary.isEmpty()) {
    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "no secondary second try", null);
    return false;
}

if (config.secondary() == Secondary.GIANT_SEAWEED) {
    MousePackets.queueClickPacket();
    WidgetPackets.queueWidgetAction(secondary.get(), "Withdraw-1");
    MousePackets.queueClickPacket();
    WidgetPackets.queueWidgetAction(secondary.get(), "Withdraw-1");
    MousePackets.queueClickPacket();
    WidgetPackets.queueWidgetAction(secondary.get(), "Withdraw-1");
    return true;
}

BankInteraction.withdrawX(secondary.get(), config.secondary().getSandAmount());
return true;
```

### Casting the Spell
Finally, if all steps are successful, the plugin casts the Super Glass Making spell via a click packet and queueWidgetAction.

```java
MousePackets.queueClickPacket();
WidgetPackets.queueWidgetAction(make_glass, "Cast");
```
<sup> There is an easier way to do this my creating a Magic Handling class, we can cover later - Cago </sup>

### Timeout
After performing an operation, a timeout of 3 ticks is set to prevent immediate re-triggering of the method.

```java
timeout = 3;
```

<sup> 

In progress

For the complete code, please refer to the following link: [SuperGlassMakerPlugin](https://github.com/Ethan-Vann/EthanVannPlugins/tree/master/src/main/java/com/example/superglass) 

</sup>

---

## 3.3 Kotori Plugin Loader

<sup> 

In progress

</sup>


---

## 3.4 Game Pack Reflection

><i>" Mixin files in RuneLite serve as connections to the game client, allowing developers to access specific methods and data. These files are kept private and obfuscated, meaning their inner workings are hidden. When RuneLite is initialized, these mixin files are loaded to inject or retrieve game information in a logical way.
<br><br>To understand mixins, let's consider a couple of examples. Imagine we want to get data about the currently selected spell widget in the game. In this case, we use a hook to directly access the needed information from the game client. On the other hand, if we need to determine the animation ID of a character, we may require additional information from different fields. This is where a mixin comes in, helping us access those specific fields to calculate the animation ID correctly.
<br><br>Finding the obfuscated method names in the patched RuneLite client involves a simple process. For instance, if we're looking for the obfuscated name of the method "getAnimationID," we can obtain an instance of the relevant class, like an Actor. By calling a method on that instance and logging the result, we can identify the obfuscated name we're looking for.
<br><br>Typically, obfuscated method names have an extra parameter compared to their original unobfuscated versions. This additional parameter is usually a throwaway value, such as an integer. By observing the number of parameters and checking for any additional ones, we can identify the obfuscated method.
<br><br>Mixin files and hooks enable developers to enhance the functionality of the RuneLite client. They allow access to specific game information and support more complex operations. In cases where RuneLite doesn't provide built-in hooks for certain gamepack methods, developers can utilize mixins and hooks to interact with them and achieve their desired goals. This flexibility empowers developers to customize and improve the RuneLite client to suit their needs.
>"</i>
>
> -- <cite>[SkylerMiner](#SkylerMiner) 7/18/2023</cite>

<sup>
In Progress
</sup>

---

## 3.5 Revision updates
This Section will cover the basic differences between the two types of revisions updates most commonly seen in the runelite plugin development market. This helps understand what is going on and possible could help you start being able to help update the revisions in the future.

### What is a Rev update?
A rev update stands for a "Revision update". When this happens either Runelite has pushed a mixin update changing their obfuscation. Or Jagex has updated the base gamepack which changes almost every obfuscated method name we are trying to acces.

### Sub-Rev Updates
Sub-rev updates are what happens when runelite pushes an update. This is when they update their mixins. This is a term that is a "In-between" layer of the injection process that lets runelite inject specific bytcode into the original gamepack. This lets them add hooks into the gamepack as well as realsy grabbing methods and fields making them into a more useful method call. This means any of their specific methods you need to reflect will need to change. Such as, iff you can see that runelite has recently update you can be sure to have some issues with plugin development if your repo relies on any reflected runelite API methods. Such methods include things such as getAnimation() on the actor class, and getPath() of Npcs/Players. These rely on the base RuneLite mixins so you'll have to grab the newest obfuscated names when this type of update occurs

In Ethan API based repos one of the reflected methods is integral to the creation of packets usually causing any of these repos to break where as some other repos that only use invokes my be unnafected on a sub-rev update day



### Gamepack Rev Update
Gamepack updates are where java re-obfuscates all of their client code when releasing a new version. These usually occur anywhere from 1month->3months between each major revision. When this happens we have to de-obfuscated the gamepack and map it with a previous mapping of the last gamepack. This spits us out mapped files that are more human readable and lets us both easily grab methods/fields we want but this output is either directly used in the updating any OPRS repos such as Storm, Squire, and Devious. Whereas other repos use a combination of this output and other inputs to map the classname/fields/methods they need. 

The level of difficulty here is really understanding what is happening to begin with and people who don't understand how the gamepack is reflected into to change client methods might have difficulty attempting this. As well difficulty is dependant on what type of client the rev update is for. 

If the update is for an strictly invoke based client with no additional reflection updates an be done in around 10-30minutes. OpenOSRS based clients have a decent amount of classfiles that just need to be turned into proper java and ensure the mixins they have are still working properly. Packet clients usually have to ensure all parts of the packet + packetfieldnames + buffernodes + packetwrite functions are all working correctly.

<sub>
in progress
</sub>


---

# TODO:

- [ ] Extend Section 3
- [ ] More Examples
- [ ] Resources

---

# Credits
#### [EthanVann](https://github.com/Ethan-Vann)

#### [Kotori](https://github.com/OreoCupcakes)

#### [SkylerMiner](https://github.com/SkylerPIlot)
#### [Bilbo](https://www.youtube.com/watch?v=Z65DM37RhyY)
#### [Cago](https://github.com/CagoThaPlug)

---

## Additional Resources

For additional resources, references, and ongoing community support, check out the following:

### Repos of Interest 

- EthanVann Forks
    - [PiggyPlugins](https://github.com/0Hutch/PiggyPlugins)
    - [ImpactPlugins](https://github.com/moneyprinterbrrr/ImpactPlugins)
    - [Oluiscabral](https://github.com/oluiscabral/runescape-plugins)
    - [RuneBotEVP](https://github.com/KALE1111/RuneBotEVP)
    - [KatAnalPlug](https://github.com/PaJauKat/KatAnalPlug)

<br>

<sup> Want to add to the List? Submit a PR or DM me on Discord! </sup>

---

## Contact Info

- Discord: .zalc (User ID: 242837834120036353)
- Discord: cagomyre (User ID: 1072925304982614138)
- Discord: bilbomyre (User ID: 693075521051033602)

I only have these 2 discord I will never PM you from another. <br> If you get a PM from someone claiming to be me, it is not me.

Same goes for Bilbo's discord.

---

Want to give a thanks?
<br>
<br>
[![coinbase](https://img.shields.io/badge/Donate-Coinbase-blue.svg)](https://commerce.coinbase.com/checkout/57579fb4-002e-4a2a-a4a3-a1cf523c2dfd)

---

[![CagoThaPlug - LEECH](https://img.shields.io/static/v1?label=CagoThaPlug&message=LEECH&color=grey&logo=github)](https://github.com/CagoThaPlug/LEECH "Go to GitHub repo")
![](https://komarev.com/ghpvc/?username=CagoThaPlug&label=PROFILEVIEWS)
![License](https://img.shields.io/badge/License-BSD--3--Clause_license-grey)

<br>
    
![stars - LEECH](https://img.shields.io/github/stars/CagoThaPlug/LEECH?style=social)
![forks - LEECH](https://img.shields.io/github/forks/CagoThaPlug/LEECH?style=social)


<sup>[Back to Top](#table-of-contents)</sup>

---
