# CPSC1181-Assignment8
JavaFX Ball Game - Completed Mar 2023

Animated game coded in JavaFX for more practice with inner classes, event handling and timers. A ball slowly crosses the screen from left to right and the user can click on it, incrementing their score counter by one. The game ends when the user misses 5 balls, with the number of missed balls being shown on a counter. With each successful click on a ball, the ball resets it's starting position to off screen and the velocity increases, thus upping the difficulty as the game progresses. Buttons are provided to both pause the current state of the game and reset the whole game. 


At first, I had set the check for whether the user had missed the ball using `if .getCenter() == 450`. This should have counted as a miss and incremented the "missed" counter as well as reset the ball's position, but since the ball was constantly moving, it was rare that the instant where the ball's centerX position was exactly 450.0 . Thus, misses were not being detected at all. I solved this by changing the condition to `if .getCenter() > 450`. I also added checks in the relevant EventHandlers to make sure that the pause button could toggle starting and stopping the game, and that clicking on the ball would not count if the game was paused.
