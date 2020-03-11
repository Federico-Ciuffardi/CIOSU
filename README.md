# CIOSU
Cisco IOS Utilities

### Serial
CIOSU provides a console to configure a cisco device trough the serial port.

**Features:**

* **Controled pasting:**  Pasting speed is controlled to prevent common pasting problems that could arise when using the serial port.
* **Waits for the user input on the commands that require it**
* **List of serial ports available on the preferences**
* **Cancel the Paste:** if you press CTRL+C when pasting it will stop
* **DEL working properly**

### Compare
Utility to compare two configurations (with the same format as the show running/startup commands of the Cisco IOS).

**Color code:**

* **RED:**     ERROR  (no match)
* **YELLOW:**  CARFUL (may be a encripted string or something router specific that prevents matching)
* **GREEN:**   OK     (it matches)

There may be other uses since it compares the lines and configuration modes without order, the configuration mode of a line is the global configuration mode if it has no indentation, otherwise the configuration mode is the nearest line without indentation.

## Prerequisites
* java jre 8 or higher

## How to use
* Download and run a IOSU-X.Y.Z.jar from the [releases](https://github.com/Federico-Ciuffardi/IOSU/releases)
* **Note for Linux users:** Serial port access is limited to certain users and groups in Linux. To enable user access, you must open a terminal and enter the following commands to access the ports on your system without using sudo. Don't worry if some of the commands fail. All of these groups may not exist on every Linux distro. (Note, this process must only be done once for each user):

```
sudo usermod -a -G uucp yourusername

sudo usermod -a -G dialout yourusername

sudo usermod -a -G lock yourusername

sudo usermod -a -G tty yourusername
```

## Versioning
Using [SemVer](http://semver.org/) for versioning. For the versions available, see the [releases](https://github.com/Federico-Ciuffardi/IOSU/releases) 

## Authors
* Federico Ciuffardi

## Note
Thank you for checking out this repository, send all your comments or questions to Federico.Ciuffardi@outlook.com.
