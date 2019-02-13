# IOSU
Cisco IOS utilities, includes:

## Serial
Used for configuring your cisco device trough the serial port.

**Features:**

* **Controled pasting:**  each line is pasted one at the time so it avoids common pasting  problems when doing it through serial port
* **Waits for the input further options to keep pasting:** useful when a large configuration text is pasted that includes commands like "license accept end user agreement" that asks [yes/no]: and there are other commands left to paste after this one without this the commands left are pasted as answers  to  [yes/no]: making them useless 
* **List of serial ports available on the preferences**
* **Paste cancel:** if you press CTRL+C when pasting it will stop
* **DEL working properly**

## Compare
Utility for comparing a current configuration with a target configuration (must have the same format as the show running/startup commands of the Cisco IOS).

**Color code:**

* **RED:**     ERROR  (no match)
* **YELLOW:**  CARFUL (may be a encripted string or something router specific that prevents matching)
* **GREEN:**   OK     (it matches)

There may be other uses since it compares the lines and configuration modes without order, the configuration mode of a line is the global configuration mode if it has no indentation, else the configuration mode is the nearest line without indentation.

### Prerequisites
-java jre 8 or higher

## Versioning
Using [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/Federico-Ciuffardi/IOSU/releases). 

## Authors
* Federico Ciuffardi

## Note
Thank you for checking out this repository, send all your comments or questions to Federico.Ciuffardi@outlook.com.
