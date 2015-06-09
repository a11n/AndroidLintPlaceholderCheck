# AndroidLintPlaceholderCheck

## Deprecated
**This project is deprecated. There will be no new development on this project. Please use [designtime layout attributes](http://tools.android.com/tips/layout-designtime-attributes) instead.**

This project features a custom lint check for **placerholders** in your XML layout files.

## What is a placeholder?

A placeholder is a text which is used at design time but is replaced by a particular value at runtime. Assume you have a TextView which for example displays an order number. The order number is not present at design time but will be displayed at runtime.

![Screenshot illustrating usage of placeholder.](/static/Screenshot1.png)

## Why is it needed?

While it's definitely bad practice to hard code strings into your layouts there are several good reasons where it make sense to use placeholder:

* get a meaningful overview of the layout
* see text appearance (font size, color, style, …)
* check correct wrapping
* …

## So, what’s the difference between a string and a placeholder?

A string is considered as a translatable entity within the layout. Strings are labels, which should be stored in your strings.xml.

Placeholders are used for layout purpose, they should fill a certain area of the screen in order to provide the developer on impression how the screen would look like at runtime. Since the are replaced by a particular value at runtime, there is no need to store them into your strings.xml

## Why is a custom lint check required?

Android’s default checks already feature a check for hardcoded values. It basically checks if provided values in *android:text* and android:hint attributes start with *@string/*. This results in a majority of warnings, if you decide to use placeholders in your layouts.

![Screenshot showing hardcoded string warning.](/static/Screenshot2.png)

To overcome these warnings this lint check is aware of a certain type of text which it also accepts: placeholder.

##What’s the syntax of a placeholder?

To express the usage of a placeholder, a placeholder is prefixed by „:“. The lint check won’t emit a warning if it detects a placeholder.

*Note: Feel free to fork this project and change the syntax to whatever suits your needs.*

Example:
```xml
<TextView android:text=":OrderNumber" />
```

##How to install and run the custom check?

In order to install this check you need to place the compiled jar in your user's *android/lint/* directory.

On Unix based systems you can simply use ```./buildAndInstall``` for your convenience.

* Compile the rule
```shell
./gradlew clean assemble
```

* Copy AndroidLintPlaceholderCheck-1.0.jar from *build/libs/* to your user's *android/lint/* directory.
Note: On Unix based systems this is most likely *~/.android/lint/*
```shell
mkdir ~/.android/lint/
cp build/libs/* ~/.android/lint/
```

* Since the Android tools consider the above mentioned *lint* directory when executing checks you are all set to run the custom check for your project's layout files.
However, since the default **"HardcodedText"** check is still in place you may want to ignore the default **"HardcodedText"** check to avoid I18N warnings. Since this custom check is an extension of the default "HardcodedText" check you won't loose any functionality.
Run this check:
```shell
lint --check HardcodedTextWithPlaceholder
```
