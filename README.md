
```mermaid
graph LR
    %% Build System
    subgraph "Build System"
        BS1["build.gradle.kts"]
        BS2["gradlew"]
        BS3["gradlew.bat"]
        BS4["gradle/wrapper/"]
        BS5["gradle-wrapper.properties"]
        BS6["gradle-wrapper.jar"]
    end
    
    BS4 --> BS5
    BS4 --> BS6

    %% Core Application
    subgraph "Core Application"
        CA1["Main.java"]
    end

    %% Testing Suite
    subgraph "Testing Suite"
        subgraph "Test Configuration"
            TC1["DriverConfig.java"]
        end
        
        subgraph "Test Cases"
            TST1["LoginTest.java"]
            TST2["OrderLookupTest.java"]
            TST3["RegisterTest.java"]
            TST4["SearchTest.java"]
            TST5["AccountInformationTest.java"]
            TST6["AddToCartTest.java"]
            TST7["ProductSorterTest.java"]
            TST8["ProductDetailsTest.java"]
            TST9["StoreLocatorTest.java"]
            TST10["ProductFilterTest.java"]
            TST11["UpdateCartTest.java"]
        end
        
        subgraph "Test Utilities"
            TU1["Notification.java"]
            TU2["AlertNotification.java"]
            TU3["Tools.java"]
            TU4["ExcelReader.java"]
            TU5["FileReader.java"]
        end
        
        subgraph "Page Objects"
            PO1["LoginPage.java"]
            PO2["AccountInformationPage.java"]
            PO3["EditPassPage.java"]
            PO4["SortPage.java"]
            PO5["AddCartPage.java"]
            PO6["UpdateCartPage.java"]
            PO7["DeleteFavoriteListPage.java"]
            PO8["ProductDetailsPage.java"]
            PO9["StoreLocatorPage.java"]
            PO10["FilterPage.java"]
        end
        
        subgraph "Test Resources"
            TR1["testng.xml"]
            TR2["accountln_information.xlsx"]
            TR3["login.xlsx"]
            TR4["config.properties"]
            TR5["FilterData.xlsx"]
            TR6["SortData.xlsx"]
            TR7["search.xlsx"]
        end
        
        subgraph "Base Classes"
            BC1["BaseTest.java"]
            BC2["BasePage.java"]
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
    BC1 -->|"extends"| TST11
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
    TST11 -->|"uses"| TC1

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
    TST11 -->|"tests"| CA1

    %% Test Cases using Page Objects
    TST1 -->|"uses"| PO1
    TST2 -->|"uses"| PO1
    TST3 -->|"uses"| PO1
    TST4 -->|"uses"| PO1
    TST5 -->|"uses"| PO2
    TST5 -->|"uses"| PO3
    TST6 -->|"uses"| PO5
    TST7 -->|"uses"| PO4
    TST10 -->|"uses"| PO10
    TST11 -->|"uses"| PO5
    TST11 -->|"uses"| PO6

    %% Test Cases using Resources
    TST1 -->|"reads"| TR3
    TST4 -->|"reads"| TR7
    TST5 -->|"reads"| TR2
    TST7 -->|"reads"| TR6
    TST10 -->|"reads"| TR5
    TH1 -->|"configures"| TR1
    TC1 -->|"reads"| TR4

    %% Test Utilities connections
    TU4 -->|"reads"| TR2
    TU4 -->|"reads"| TR3
    TU5 -->|"reads"| TR5
    TU5 -->|"reads"| TR6
    TU5 -->|"reads"| TR7

    %% Page Objects using Utilities
    PO1 -->|"uses"| TU1
    PO2 -->|"uses"| TU1
    PO3 -->|"uses"| TU1
    PO4 -->|"uses"| TU1
    PO5 -->|"uses"| TU2
    PO6 -->|"uses"| TU2
    PO7 -->|"uses"| TU1
    PO10 -->|"uses"| TU3
    
    %% Styling classes with vibrant text colors
    classDef build color:#FF0000,stroke:#FF0000,stroke-width:2px;
    classDef app color:#00CC00,stroke:#00CC00,stroke-width:2px;
    classDef config color:#0066FF,stroke:#0066FF,stroke-width:2px;
    classDef test color:#CC6600,stroke:#FFCC00,stroke-width:2px;
    classDef util color:#6600CC,stroke:#6600CC,stroke-width:2px;
    classDef page color:#FF6600,stroke:#FF6600,stroke-width:2px;
    classDef resource color:#00AAAA,stroke:#00CCCC,stroke-width:2px;
    classDef base color:#3333CC,stroke:#3333CC,stroke-width:2px;
    classDef harness color:#0000FF,stroke:#0000FF,stroke-width:2px;
    classDef configFile color:#CC0000,stroke:#FF3333,stroke-width:2px;
    %% Assign classes
    class BS1,BS2,BS3,BS4,BS5,BS6 build;
    class CA1 app;
    class TC1 config;
    class TST1,TST2,TST3,TST4,TST5,TST6,TST7,TST8,TST9,TST10,TST11 test;
    class TU1,TU2,TU3,TU4,TU5 util;
    class PO1,PO2,PO3,PO4,PO5,PO6,PO7,PO8,PO9,PO10 page;
    class TR1,TR2,TR3,TR4,TR5,TR6,TR7 resource;
    class BC1,BC2 base;
    class TH1 harness;
    class CF1,CF2,CF3,CF4,CF5 configFile;

    %% Click Events for Build System
    click BS1 "https://github.com/shinki04/TotodayWebTesting/blob/main/build.gradle.kts"
    click BS2 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradlew"
    click BS3 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradlew.bat"
    click BS4 "https://github.com/shinki04/TotodayWebTesting/tree/main/gradle/"
    click BS5 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradle/wrapper/gradle-wrapper.properties"
    click BS6 "https://github.com/shinki04/TotodayWebTesting/blob/main/gradle/wrapper/gradle-wrapper.jar"

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
    click TST11 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/tests/UpdateCartTest.java"

    %% Click Events for Test Utilities
    click TU1 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/Notification.java"
    click TU2 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/AlertNotification.java"
    click TU3 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/Tools.java"
    click TU4 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/ExcelReader.java"
    click TU5 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/java/utils/FileReader.java"

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
    click TR5 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/FilterData.xlsx"
    click TR6 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/SortData.xlsx"
    click TR7 "https://github.com/shinki04/TotodayWebTesting/blob/main/src/test/resources/search.xlsx"

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

