
🧩 JavaFX Logic Grid Solver\n
    A standalone Java application that challenges users to solve complex logic puzzles using a $3 \times 4$ grid system. Built with JavaFX, this project simulates the classic "Logic Grid" experience found in puzzle magazines, featuring automated rule enforcement, a hint system with time penalties, and a dynamic state-tracking engine.

🌟 Features

    Classic Logic Grid UI: A 48-square interactive grid divided into three comparison sections.
    
    Smart State Management: * Single Click: Marks an 'X' (Excluded).
    
        Double Click: Marks an 'O' (Selected).
        
        Auto-Exclusion: Selecting a square automatically fills the corresponding row and column with 'X' marks.
        
    Dynamic Hint System: Provides targeted highlights for squares to assist the user, with a +120s time penalty per use.
    
    Validation Engine: * Clear Errors: Identifies and resets incorrect marks while applying a time penalty.
    
        Smart Submit: The "Submit" button remains disabled until all 48 squares are filled, preventing incomplete attempts.
        
    Live Timer: Tracks total solve time, including penalties, to provide a final "Score" upon completion.
    

🛠 Technical SpecificationsArchitecture: 

    Object-Oriented Design (OOD) following a provided UML structure.
    
    GUI Framework: JavaFX for a responsive, hardware-accelerated interface.
    
    Data Persistence: Puzzle solutions are stored locally in-memory; no internet or cloud connection required.

    State Logic: Each grid square supports four distinct states: Empty, Excluded (Blue X), Selected (Filled Circle), and Highlighted (Hint).
    

🎮 How to PlayRead the Clues: 

    Use the four provided text clues to determine relationships between the three categories.
    
    Mark the Grid:
    
        Click once to place an X if a match is impossible.
        
        Double-click to place an O if a match is certain.
        
    Use Tools Wisely:
    
    Hint: Highlights a relevant square but adds 2 minutes to your time.
    
    Clear Errors: Removes all wrong guesses but adds 2 minutes per incorrect square removed.
    
    Solve: Fill every square. Once the grid is complete, hit Submit to see your final time!
    
