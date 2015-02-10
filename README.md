# AndroidLintPlaceholderCheck

This project features a custom lint check for **placerholders** in your XML layout files.

## What is a placeholder?

A placeholder is a text which is used at design time but is replaced by a particular value at runtime. Assume you have a TextView which for example displays an order number. The order number is not present at design time but will be displayed at runtime.

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

To overcome these warnings this lint check is aware of a certain type of text which it also accepts: placeholder.

##What’s the syntax of a placeholder?

To express the usage of a placeholder, a placeholder is prefixed by „:“. The lint check won’t emit a warning if it detects a placeholder.

Example:
```xml
android:text=":OrderNumber"
```



