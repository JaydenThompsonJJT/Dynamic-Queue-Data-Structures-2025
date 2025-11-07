Project Overview
The Warehouse Inventory Management System is a Java-based application designed to simulate and manage the inventory of a warehouse. It supports both perishable (e.g., chips, rice) and non-perishable (e.g., electronics) items, tracking their expiration dates and quantities. The system uses a timer to simulate the passage of days, automatically updating expiration timers and alerting users when items are about to expire.
This project demonstrates the use of data structures (priority queues, sets, and lists), object-oriented programming (inheritance, polymorphism), and event-driven programming (timer-based simulation) to address real-world logistics challenges.

Key Features
✅ Inventory Management

Add, view, and ship items.
Track perishable and non-perishable items separately.

✅ Expiration Tracking

Automatically decreases expiration timers each day.
Alerts users when items are about to expire (≤5 days).
Removes expired items from inventory.

✅ User-Friendly Interface

Menu-driven system for easy interaction.
Options to buy, view, ship, and process expired items.

✅ Simulation Mode

Timer simulates the passage of days (2 seconds = 1 day).
Pause and resume simulation as needed.


Technologies Used

Java (Core Java, Collections Framework)
Data Structures (PriorityQueue, Set, List)
Object-Oriented Design (Inheritance, Polymorphism)
Event-Driven Programming (Timer, TimerTask)


How to Run


Clone the Repository
bash Copygit clone https://github.com/your-username/Warehouse-Inventory-System.git


Compile and Run

Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
Compile and run the Warehouse.java file.



Interact with the System

Use the menu to buy, view, ship, or process expired items.
The timer will automatically advance days and update expiration timers.




Future Enhancements

Add a graphical user interface (GUI) for better user experience.
Implement database integration for persistent storage.
Extend the system to support multiple warehouses.


Contributing
Contributions are welcome! If you'd like to improve this project, feel free to fork the repository and submit a pull request.

License
This project is open-source and available under the MIT License.
