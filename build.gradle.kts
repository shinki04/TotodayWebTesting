plugins {
    id("java")
}

group = "edu.vlu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks {
    withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }
    withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
    withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}
dependencies {
//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.testng:testng:7.10.2")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.28.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("org.seleniumhq.selenium:selenium-chromium-driver:4.28.1")
    testImplementation("org.testng:reportng:1.2.2")
}
tasks.named<Test>("test") {
    // Sử dụng TestNG cho unit tests
    useTestNG {
        useDefaultListeners = true
        outputDirectory = file("$projectDir/TestNG_Reports")
    }
    reports {
        html.required.set(true)
        html.outputLocation.set(file("$projectDir/GradleReports"))
    }
}
tasks.test {
//    useJUnitPlatform()
    useTestNG()
}

//tasks.register("helloVietnam") {
//    doLast {
//        println("Xin chào Việt Nam! ")
//    }
//}