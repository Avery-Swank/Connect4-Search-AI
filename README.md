# CSCI4511_Connect4_AI
CSCI4511W  Final Project. Write a program that uses artificial intelligence, search algorithms, data structures that were discussed in class to solve a game of Connect 4 and analyze the differences between those algorithms. Program those different algorithms. Analyze what performs better and why

## Connect 4 Overview
For our project, we will be focusing on the game Connect Four. Connect Four is a game where you play on a grid that is composed of a six by seven grid, six rows and seven columns. The game is played by two people with one color being black and one being red. Each turn, a player can play a piece only in any of the columns that have not yet been filled all the way to the top. The players alternate between moves until a winner has been found. The winner is determined by whoever is able to connect four disks of their own color in a row in a style similar to tic-tac-toe. These four disks can be connected either vertically, diagonally, or horizontally and consist of their own colors, hence the name Connect Four

One key characteristic to note when writing an algorithm to learn or solve Connect Four is that a player's advantage is the other player's disadvantage. This means that when considering the best move to take, the other player will suffer a certain amount of disadvantage which will affect its heuristic and overall position potentially. Also, the environment is dynamic where one player's actions affect the other player's actions. There are only up to seven possible actions for each move, the seven columns to place discs. For our project, we will be using two players as is the rule for Connect Four and applying that to the game of Connect Four to find optimal playing strategies. Using different heuristics, searches, and modifications of those searches, we will test them on a Connect Four simulation and record the results to be analyzed. For our algorithms, we will be using a naive search, a simple search, a depth limited search, a minimax search, a minimax with pruning, and a heuristic function. By testing all these algorithms, we hope to find an optimal and effective approach to playing the game Connect Four and be able to use that information to consistently win games against any opponent with the approach we find to be best.

## Our Approach
When constructing our project, we recognize that there most likely exists software we can use to run our experiments on Connect Four. However, we decided to write up our own Connect Four game from scratch, this means writing our own game of Connect 4, our own searches, heuristics, and algorithms. All of the work that we perform will be heavily inspired from our research articles we found that are centered around algorithms for Connect 4. We decided that it would be best if we wrote everything from scratch, given both of our very proficient experience in object oriented languages, it would be very easy to write our own solution. This gives us the ability to easily extend it to handle search algorithms, heuristics, etc. This would also give us complete control over what is going on and how our algorithm is performing without restrictions from third party programs.

We maintain the core rules of Connect Four: two rational agents play against each other to connect four discs in a straight. We will then pull expertise and analysis from each of our research articles to decide which algorithms to implement and play with for a game of Connect 4.

Our take on this project is that we implement some of the algorithms described in those articles as well as our own for comparison. We may want to try multiple different heuristics or a completely different algorithm than what was suggested and say "okay, how does this compare to other researchers? why do they use or don't use these algorithms" and questions like that.

## Project Structure Overview
- **Board.java**
  The board class contains all of the logic for handling the Connect4 board. Adding disks to columns, removing disks for search algorithms, checking for a Connect4 on the board, any layer of validation is included as well
- **Player.java**
  The Player class contains references to the two players that are playing. This is good to have player types to differentiate between human players, between randomized players, minimax players, simple players, etc.
- **Game.java**
  The game class contains all of the logic for playing a game between two players and a board. The game executes a move from each player, alternating between the players, executing their searches, and places pieces on the board until there is a Connect4 or the board is full
- **Search.java**
  The Search class is a class containing universal search functions. This class contains references to all of the different depth first search, calculating heuristics, searching possible moves based on those heuristics, minimax trees, and randomized algorithms like naive and simple
- **Run.java**
  The run class contains all of the game, board, and player information. This is the main class that runs all of the games for as many player types for as many games on as many different boards as we want

## Our Metrics
For a project that measures the performance of a search algorithm when finding optimal solutions to a game like Connect4. Our biggest measurement is completeness and ability to outperform other algorithms. Given a game of Connect 4 is relatively small in terms of different combinations of games compared to Chess or Go, memory is not going to be a major factor. Also, give a Connect 4 game is a small 2D space with very basic rules compared to Chess or Go, speed will also not be a major factor between algorithms.

Our main metric will be quality of results. Does the expected performance of a algorithm consistently perform better than presumably worse algorithms? Do our results somewhat align with that of the research papers we discovered? We plan on performing in-depth analysis of each algorithm written and compare results across hundreds or even thousands of games.

## Research Papers
When researching the complexity, decision-making, and possible algorithms to tackle the Connect Four problem, a few different articles went in-depth into particular strategies and decision trees for the optimal playing strategy. The most common algorithm discussed across a variety of articles was MiniMax, heuristics, and randomized as a baseline for compare and contrast of performance. We wrote our own custom Java solution however all of the folliwing algorithms were heavily influenced from the research article's descriptions:

## Search Algorithms
All of these search functions are contained in `Search.java`
 - **Random Search** - Always pick a random column
 - **Naive Search** - Pick a winning move for the player. Otherwise, block any winning move for the opponent. Otherwise, pick a random column
 - **Simple Search** - Pick a winning move for the player. Otherwise, block any winning move for the opponent. Otherwise, pick a column based on the best heuristic for the next move
 - **Heuristic Search** - Pick a column based on the best heuristic for the next move. Act very selfishly, only care about the player's heuristic and not the opponent's heuristic
 - **MiniMax Search** - Pick a column based on the best heuristic for the next set of moves. Create and traverse a tree of all combinations of the next three moves: player, opponent, player again. Calculate the heuristics of each of those boards. Then, pick the first move that leads to the maximum set of heuristics. This in turn focuses more on future moves if there is not an immediate winning move or an immediate blocking move.
 
## Data
Since this solution was created from scratch, there is proper separation of Players, Games and Matches. Now we can run any number of games, any type of Player in any combination we want all in `Run.java`. For the data sets, matches consistend of 100 games between two different types of players as well as two of the same types of players. We also added some of our own analytics by counting the number of moves until a win. For all of Games, it was played on a 7 by 6 Connect 4 board. That is 42 total possible moves spaces to fill in a single game. Note: Left number is for row, right number is for column

### Number of Wins - All Types Against All Types
| Players    | Random     | Naive      | Simple     | Heuristic  | MiniMax    |
| ---------- | ---------- | ---------- | ---------- | ---------- | ---------- |
| Random     | 64, 36     | 1, 99      | 0, 100     | 31, 60     | 1, 99      |
| Naive      |            | 49, 51     | 16, 84     | 79, 21     | 28, 72     |
| Simple     |            |            | 100, 0     | 100, 0     | 0, 100     |
| Heuristic  |            |            |            | 0, 100     | 0, 100     |
| MiniMax    |            |            |            |            | 100, 0     |

### Average Number of Moves To Win - All Types Against All Types
| Players    | Random     | Naive      | Simple     | Heuristic  | MiniMax    |
| ---------- | ---------- | ---------- | ---------- | ---------- | ---------- |
| Random     | 20, 23     | 27, 18     | NaN, 12    | 14, 15     | 15, 12     |
| Naive      |            | 25, 25     | 14, 21     | 15, 23     | 17, 22     |
| Simple     |            |            | 7, NaN     | 7, NaN     | NaN, 12    |
| Heuristic  |            |            |            | NaN, 16    | NaN, 12    |
| MiniMax    |            |            |            |            | 25, 0      |

## References
Heuristics in the game of Connect-K [Reference](http://inside.mines.edu/~huawang/CSCI404_Projects/Project2/connectk.pdf)\
Online Adaptive Agent for Connect Four [Reference](https://www.researchgate.net/profile/Olana_Missura/publication/26920413_Online_Adaptive_Agent_for_Connect_Four/links/0c960534fd3fd23910000000/Online-Adaptive-Agent-for-Connect-Four.pdf)\
Real-Time Connect 4 Game Using Artificial Intelligence [Reference](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.165.9761)

## Technologies Used
Java, Eclipse

## Contributors
Avery Swank (swank026) and David Wu (wuxx1751)
