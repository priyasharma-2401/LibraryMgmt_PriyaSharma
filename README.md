# Library Management System

##  Author

Priya Sharma

---

##  Overview

This project implements a console-based Library Management System in Java that models real-world library operations such as book inventory management, patron management, lending workflows, reservations, and recommendations.
The system demonstrates strong application of Object-Oriented Programming (OOP), SOLID principles, and multiple design patterns.

---

##  Features

The system demonstrates strong application of Object-Oriented Programming (OOP), SOLID principles, and multiple design patterns.



### Core Features

* Book management (add, remove, search)
* Patron management with borrowing history
* Book checkout and return
* Inventory availability tracking
* Search by title, author, and ISBN

### Advanced Features

* Multi-branch library support
* Book transfer between branches
* Reservation system with notifications (Observer pattern)
* Recommendation engine based on borrowing history
* Logging of key system events

---

## Design Patterns Used

| Pattern          | Usage                                |
| ---------------- | ------------------------------------ |
| Factory Pattern  | Centralized creation of Book objects |
| Strategy Pattern | Flexible book search implementation  |
| Observer Pattern | Reservation notification mechanism   |

---

## Key Design Assumptions

To keep the implementation focused on OOP concepts and system design (as per assignment scope), the following assumptions were made:

### Single-Copy-Per-ISBN Model

* Each ISBN represents a single physical copy of a book.
* Book availability is tracked using a boolean flag (`available`).
* When a book is checked out, it becomes unavailable until returned.

**Rationale:**
This simplifies the lending workflow and keeps the focus on demonstrating design patterns and object relationships rather than inventory scaling complexity.

**Future Enhancement:**
The model can be extended to support multiple copies by replacing the boolean availability flag with `totalCopies` and `availableCopies` counters.

---

### In-Memory Storage

* All data is stored in Java collections (Map/List/Set).
* No database or persistence layer is used.

Rationale:
The assignment explicitly focuses on OOP and design principles rather than persistence.

---

### Console-Based Interaction

* The system uses a menu-driven console interface.
* No GUI or web interface is included.

Rationale:
Keeps the implementation lightweight and focused on core logic.

---

### Single-Threaded Execution

* The system assumes a single-user environment.
* Concurrency and thread safety are out of scope.

---

## How to Run

From project root:

```bash
javac -d . $(find src -name "*.java")
java Main
```

---

## Class Diagram (High Level)

```
LibraryService
 ├── LibraryBranch
 │     └── Book
 ├── Patron
 │     └── Loan
 ├── ReservationManager (Observer)
 └── SearchStrategy (Strategy)
```

---

## Possible Future Improvements

* Support multiple copies per ISBN
* Persistent storage (database)
* Fine and due-date management
* Thread-safe operations
* REST API / Web UI
* Enhanced recommendation algorithms

---

This implementation demonstrates:

* Object-Oriented Programming (encapsulation, abstraction, polymorphism)
* SOLID principles
* Multiple design patterns
* Proper use of Java collections
* Logging of important events
* Clean, modular architecture

---

Repository:
https://github.com/priyasharma-2401/LibraryMgmt_PriyaSharma/tree/feature-clean

