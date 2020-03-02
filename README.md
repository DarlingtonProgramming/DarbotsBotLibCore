# DarbotsBotLibCore
 Darbots Java Core Robotics Utilities   
 This java library will be providing basic types / class definitions for future darbots programs, including but not limited to FTC Robot Simulator, DarbotsFTCLib, etc.   
 
## 1.0 Library Design Concept
The library is designed to have zero dependency on the official FTC SDK to provide portability and possibility of running on devices such as PC and Raspberry PI. We believe that the cost to train new programmers for the FTC game can be reduced by using a control system that is not FTC specific (though the API should stay UNIVERSAL).   
The abstraction of control components lets us extend the possibility of our control system software. We might be constructing software-only competitions on our own competition platform in the near future.   

## 2.0 Integrating into your target platform
In your target control software, make sure you do the following.
1. Look into the `GlobalEntry` class, make sure `startRobot()`, `terminateRobot()`, `startLifeCycle()`, and `stopLifeCycle()` is called at the appropriate time.
2. You need to register how you update / refresh encoder datas, digital / analog channel datas for each control loop by calling `ControlLoopUtil.registerRefreshDataCallable(Callable callable)`. Everytime you run a loop in the main thread, you need to manually call `ControlLoopUtil.clearData()` to clear data cache. Usually the registration should be done when startRobot() is called.