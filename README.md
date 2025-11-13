# ES-2024_25-2Sem-Quarta-LEI-G
Software Engineering Project 2024/25

## Video
Demonstration video: [video](https://youtu.be/7X2gcjkpPnk)


## Team Identification
- Group G
- Subject: Software Engineering
- Semester: 2nd Semester 2024/25

| Name             | Student Number | GitHub Username |
|------------------|----------------|-----------------|
| Guilherme Penedo | 111222         | GuilhermePenedo |
| Pedro Pacheco    | 111039         | pcp2003         |
| João Antunes     | 111139         | deavenZ         |
| Rafael Lopes     | 111110         | tranquilizante  |

## Version

The current version of the application is tagged with `GestaodoTerritorio-1.0`.

Ver: [Releases](https://github.com/GuilhermePenedo/ES-2024_25-2Sem-Quarta-LEI-G/releases/tag/GestaodoTerritorio-1.0)

## Project Description
The project consists of a real estate cadastral management system that processes geographic data from CSV files. The system uses modern technologies for geospatial data processing and robust logging, with a graphical interface for data visualization.

### Main Features
- Import of cadastral data from CSV files
- Processing of MultiPolygon geometries using JTS (Java Topology Suite)
- Complete logging system with file output
- Geometric data validation
- Detection of physical adjacencies between properties
- Sorting of cadastres by different criteria (ID, length, area, owner)
- Graphical interface for data visualization and interaction
- Visualization of geometric shapes in dedicated panel
- Property exchange suggestions with feasibility analysis

### Technologies Used
- Java 24
- Apache Commons CSV (1.9.0) for CSV file processing
- JTS Core (1.19.0) for geometric data manipulation
- JUnit Jupiter (5.8.2) for unit testing
- Maven for dependency management and build

### Project Structure
```
src/
├── lib/           # External libraries
│   ├── commons-csv-1.9.0.jar
│   ├── jts-core-1.19.0.jar
│   └── junit-jupiter-5.8.2.jar
├── main/
│   ├── java/
│   │   ├── core/            # System core classes
│   │   │   ├── Constants.java
│   │   │   └── Main.java
│   │   ├── model/          # Data models
│   │   │   ├── Cadastro.java
│   │   │   └── Location.java
│   │   ├── service/        # Business logic
│   │   │   ├── exchange/   # Property exchange functionality
│   │   │   │   ├── PropertyExchange.java
│   │   │   │   └── PropertyExchangeService.java
│   │   │   ├── OwnerGraph.java
│   │   │   └── PropertyGraph.java
│   │   └── ui/            # Graphical interface
│   │       ├── Gui.java
│   │       └── ShapePanel.java
└── test/
    └── java/
        ├── core/          # Core classes tests
        │   └── ConstantsTest.java    # System constants tests
        ├── model/         # Data models tests
        │   ├── CadastroTest.java     # Cadastro model tests
        │   └── LocationTest.java     # Location model tests
        └── service/       # Services tests
            ├── GraphTest.java        # Base Graph class tests
            ├── OwnerGraphTest.java   # Owner graph tests
            ├── PropertyGraphTest.java # Property graph tests
            └── exchange/  # Exchange functionality tests
                ├── PropertyExchangeTest.java        # Exchange model tests
                └── PropertyExchangeServiceTest.java # Exchange service tests
```

### Main Classes

#### Cadastro
- Represents a real estate cadastre
- Processes data from CSV files
- Validates and processes MultiPolygon geometries
- Manages information such as ID, length, area, geometric shape, owner and location
- Implements sorting by different criteria
- Calculates prices based on location and property density

#### Location
- Represents a location with parish, municipality and district
- Manages prices per square meter based on administrative hierarchy
- Implements price logic for different administrative levels

#### PropertyExchange
- Represents a property exchange suggestion
- Calculates price differences and feasibility
- Evaluates improvements in average area after exchange

#### PropertyExchangeService
- Generates property exchange suggestions
- Analyzes exchange feasibility
- Calculates improvement metrics
- Implements optimization algorithms for exchanges

#### GUI
- Implements the graphical user interface using Java Swing
- Visualization of cadastres and their properties
- Dedicated panel for geometric shape visualization
- Interaction with property graph
- Visualization of exchange suggestions

### How to Run
1. Make sure you have Java 24 installed
2. Clone the repository
3. Run the project using Maven:
   ```bash
   mvn clean install
   # To run the graphical interface
   mvn exec:java -Dexec.mainClass="core.Main"
   ```

### Tests
The project includes comprehensive unit tests organized by package:

#### Core Tests
- `ConstantsTest`: Tests system constants
  - Validation of constant values
  - System configuration verification
  - Error message tests
  - GUI constants tests

#### Model Tests
- `CadastroTest`: Tests cadastre creation, validation and processing
  - Input data validation
  - Geometry processing
  - Price calculation
  - Cadastre sorting
  - Location manipulation

- `LocationTest`: Tests the location model
  - Administrative hierarchy validation
  - Price calculation by level
  - String formatting
  - Property access

#### Service Tests
- `GraphTest`: Tests the base Graph class
  - Graph construction
  - Adding adjacencies
  - Physical adjacency verification
  - Location filtering

- `OwnerGraphTest`: Tests the owner graph
  - Graph creation
  - Average area calculation
  - Getting adjacent properties
  - Owner and adjacency counting

- `PropertyGraphTest`: Tests the property graph
  - Graph creation
  - Average area calculation
  - Getting adjacent properties
  - Property and adjacency counting

- `PropertyExchangeTest`: Tests the property exchange model
  - Creating exchange suggestions
  - Price difference calculation
  - Feasibility validation
  - Area improvement calculation

- `PropertyExchangeServiceTest`: Tests exchange functionality
  - Suggestion generation
  - Feasibility calculation
  - Improvement analysis
  - Exchange optimization

Run tests with:
```bash
mvn test
```

### Environment Setup
- Java 24
- Maven 3.8.1 or higher
- UTF-8 encoding
- Dependencies managed via Maven

### External Libraries
The project uses the following external libraries, located in the `src/lib` directory:
- Apache Commons CSV 1.9.0: For CSV file processing
- JTS Core 1.19.0: For geometric data manipulation
- JUnit Jupiter 5.8.2: For unit testing

These libraries are managed by Maven and do not need to be installed manually.

