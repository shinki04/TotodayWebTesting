
```mermaid

graph LR
    %% Build System
    subgraph "Build System"
        BS1["build.gradle.kts"]
        BS2["gradlew"]
        BS3["gradlew.bat"]
        BS4["gradle/"]
    end

    %% Core Application
    subgraph "Core Application"
        CA1["Main Application"]
    end

    %% Testing Suite
    subgraph "Testing Suite"
        subgraph "Test Configuration"
            TC1["DriverConfig"]
        end
        
        subgraph "Test Cases"
            TST1["LoginTest"]
            TST2["OrderLookupTest"]
            TST3["RegisterTest"]
            TST4["SearchTest"]
            TST5["AccountInformationTest"]
            TST6["AddToCartTest"]
            TST7["ProductSorterTest"]
            TST8["ProductDetailsTest"]
            TST9["StoreLocatorTest"]
            TST10["ProductFilterTest"]
        end
        
        subgraph "Test Utilities"
            TU1["Notification Utility"]
            TU2["AlertNotification Utility"]
            TU3["Tools Utility"]
            TU4["ExcelReader Utility"]
        end
        
        subgraph "Page Objects"
            PO1["LoginPage"]
            PO2["AccountInformationPage"]
            PO3["EditPassPage"]
            PO4["SortPage"]
            PO5["AddCartPage"]
            PO6["UpdateCartPage"]
            PO7["DeleteFavoriteListPage"]
            PO8["ProductDetailsPage"]
            PO9["StoreLocatorPage"]
            PO10["FilterPage"]
        end
        
        subgraph "Test Resources"
            TR1["testng.xml"]
            TR2["accountln_information.xlsx"]
            TR3["login.xlsx"]
            TR4["config.properties"]
        end
        
        subgraph "Base Classes"
            BC1["BaseTest"]
            BC2["BasePage"]
        end
        
        TH1["Test Harness"]
    end

    %% Configuration Files
    subgraph "Configuration Files"
        CF1[".gitignore"]
        CF2["README.md"]
        CF3["settings.gradle.kts"]
        CF4["Testcase.txt"]
        CF5["LICENSE"]
    end

    %% Connections between major components
    BS1 -->|"builds"| CA1
    BS1 -->|"orchestrates"| TH1
    BS1 -->|"runs"| TC1

    %% Base Class connections
    BC1 -->|"extends"| TST1
    BC1 -->|"extends"| TST2
    BC1 -->|"extends"| TST3
    BC1 -->|"extends"| TST4
    BC1 -->|"extends"| TST5
    BC1 -->|"extends"| TST6
    BC1 -->|"extends"| TST7
    BC1 -->|"extends"| TST8
    BC1 -->|"extends"| TST9
    BC1 -->|"extends"| TST10
    BC1 -->|"uses"| TC1
    
    BC2 -->|"extends"| PO1
    BC2 -->|"extends"| PO2
    BC2 -->|"extends"| PO3
    BC2 -->|"extends"| PO4
    BC2 -->|"extends"| PO5
    BC2 -->|"extends"| PO6
    BC2 -->|"extends"| PO7
    BC2 -->|"extends"| PO8
    BC2 -->|"extends"| PO9
    BC2 -->|"extends"| PO10

    %% Test Cases interactions
    TST1 -->|"uses"| TC1
    TST2 -->|"uses"| TC1
    TST3 -->|"uses"| TC1
    TST4 -->|"uses"| TC1
    TST5 -->|"uses"| TC1
    TST6 -->|"uses"| TC1
    TST7 -->|"uses"| TC1
    TST8 -->|"uses"| TC1
    TST9 -->|"uses"| TC1
    TST10 -->|"uses"| TC1

    %% Test Cases validating Core Application
    TST1 -->|"tests"| CA1
    TST2 -->|"tests"| CA1
    TST3 -->|"tests"| CA1
    TST4 -->|"tests"| CA1
    TST5 -->|"tests"| CA1
    TST6 -->|"tests"| CA1
    TST7 -->|"tests"| CA1
    TST8 -->|"tests"| CA1
    TST9 -->|"tests"| CA1
    TST10 -->|"tests"| CA1

    %% Test Cases using Page Objects
    TST1 -->|"uses"| PO1
    TST2 -->|"uses"| PO1
    TST3 -->|"uses"| PO1
    TST4 -->|"uses"| PO1
    TST5 -->|"uses"| PO2
    TST5 -->|"uses"| PO3
    TST6 -->|"uses"| PO5
    TST6 -->|"uses"| PO6
    TST7 -->|"uses"| PO4
    TST8 -->|"uses"| PO8
    TST9 -->|"uses"| PO9
    TST10 -->|"uses"| PO10

    %% Test Cases using Resources
    TST1 -->|"reads"| TR3
    TST5 -->|"reads"| TR2
    TH1 -->|"configures"| TR1
    TC1 -->|"reads"| TR4

    %% Test Configuration loading utilities
    TC1 -->|"loads"| TU1
    TC1 -->|"loads"| TU2
    TC1 -->|"loads"| TU3
    TC1 -->|"loads"| TU4

    %% Page Objects using Utilities
    PO1 -->|"uses"| TU1
    PO2 -->|"uses"| TU1
    PO3 -->|"uses"| TU1
    PO4 -->|"uses"| TU1
    PO5 -->|"uses"| TU2
    PO6 -->|"uses"| TU2
    PO7 -->|"uses"| TU1
    PO8 -->|"uses"| TU1
    PO9 -->|"uses"| TU1
    PO10 -->|"uses"| TU1

    %% Test Utilities interactions
    TU4 -->|"reads"| TR2
    TU4 -->|"reads"| TR3

    %% Styling classes
    classDef build fill:#ffd1dc,stroke:#ff69b4,stroke-width:2px;
    classDef app fill:#d1ffd6,stroke:#32cd32,stroke-width:2px;
    classDef config fill:#d1e0ff,stroke:#1e90ff,stroke-width:2px;
    classDef test fill:#fff7d1,stroke:#ffcc00,stroke-width:2px;
    classDef util fill:#f0d1ff,stroke:#8a2be2,stroke-width:2px;
    classDef page fill:#ffd1a3,stroke:#ff8c00,stroke-width:2px;
    classDef resource fill:#d1ffff,stroke:#00ced1,stroke-width:2px;
    classDef base fill:#e6e6fa,stroke:#6a5acd,stroke-width:2px;
    classDef harness fill:#d1d1ff,stroke:#0000cd,stroke-width:2px;
    classDef configFile fill:#ffe4e1,stroke:#ff6347,stroke-width:2px;

    %% Assign classes
    class BS1,BS2,BS3,BS4 build;
    class CA1 app;
    class TC1 config;
    class TST1,TST2,TST3,TST4,TST5,TST6,TST7,TST8,TST9,TST10 test;
    class TU1,TU2,TU3,TU4 util;
    class PO1,PO2,PO3,PO4,PO5,PO6,PO7,PO8,PO9,PO10 page;
    class TR1,TR2,TR3,TR4 resource;
    class BC1,BC2 base;
    class TH1 harness;
    class CF1,CF2,CF3,CF4,CF5 configFile;
%% Click Events for Build System
click BS1 "https://github.com/shinki04/TotodayWebTesting/blob/main/build.gradle.kts"
click BS2 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradlew"
click BS3 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradlew.bat"
click BS4 "https://github.com/shinki04/TotodayWebTesting/tree/main/gradle/"

%% Click Event for Core Application
click CA1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/main/java/edu/vlu/Main.java"

%% Click Event for Test Configuration
click TC1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/config/DriverConfig.java"

%% Click Events for Test Cases
click TST1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/LoginTest.java"
click TST2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/OrderLookupTest.java"
click TST3 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/RegisterTest.java"
click TST4 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/SearchTest.java"
click TST5 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/AccountInformationTest.java"
click TST6 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/AddToCartTest.java"
click TST7 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/ProductSorterTest.java"
click TST8 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/ProductDetailsTest.java"
click TST9 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/StoreLocatorTest.java"
click TST10 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/ProductFilterTest.java"

%% Click Events for Test Utilities
click TU1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/Notification.java"
click TU2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/AlertNotification.java"
click TU3 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/Tools.java"
click TU4 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/ExcelReader.java"

%% Click Events for Page Objects
click PO1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/LoginPage.java"
click PO2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/AccountInformationPage.java"
click PO3 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/EditPassPage.java"
click PO4 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/SortPage.java"
click PO5 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/AddCartPage.java"
click PO6 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/UpdateCartPage.java"
click PO7 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/DeleteFavoriteListPage.java"
click PO8 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/ProductDetailsPage.java"
click PO9 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/StoreLocatorPage.java"
click PO10 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/pages/FilterPage.java"

%% Click Events for Test Resources
click TR1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/testng.xml"
click TR2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/accountln_information.xlsx"
click TR3 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/login.xlsx"
click TR4 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/config.properties"

%% Click Event for Base Classes
click BC1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/base/BaseTest.java"
click BC2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/base/BasePage.java"

%% Click Event for Test Harness
click TH1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/testng.xml"

%% Click Events for Configuration Files
click CF1 "https://github.com/shinki04/TotodayWebTesting/blob/main/.gitignore"
click CF2 "https://github.com/shinki04/TotodayWebTesting/blob/main/README.md"
click CF3 "https://github.com/shinki04/TotodayWebTesting/blob/main/settings.gradle.kts"
click CF4 "https://github.com/shinki04/TotodayWebTesting/blob/main/Testcase.txt"
click CF5 "https://github.com/shinki04/TotodayWebTesting/blob/main/LICENSE"

