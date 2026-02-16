# Selenium TestNG E-Commerce Framework

[![Selenium Tests](https://github.com/wbandara-tech/selenium-testng-ecommerce-framework/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/wbandara-tech/selenium-testng-ecommerce-framework/actions/workflows/selenium-tests.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 🚀 Overview

Industrial-standard Selenium TestNG hybrid automation framework for [SauceDemo](https://www.saucedemo.com/) with data-driven testing, parallel execution, CI/CD pipeline, and enterprise-level design patterns.

## 📊 Allure Report

View the latest test results: [Allure Report](https://wbandara-tech.github.io/selenium-testng-ecommerce-framework/)

## 📋 Features

- **Hybrid Framework Architecture**: Page Object Model (POM) + Data-Driven Testing
- **Thread-Safe WebDriver**: ThreadLocal implementation for parallel execution
- **Cross-Browser Support**: Chrome and Edge browsers
- **Data-Driven Testing**: CSV-based test data management
- **Dynamic Test Data**: Random data generation utilities
- **Comprehensive Logging**: Log4j2 integration
- **Rich Reporting**: Allure Reports with screenshots
- **CI/CD Ready**: GitHub Actions workflow with automatic deployment
- **Retry Mechanism**: Automatic retry for failed tests
- **Custom Listeners**: TestNG listeners for enhanced reporting
- **GitHub Pages**: Automated Allure report publishing

## 🏗️ Project Structure

```
selenium-testng-ecommerce-framework/
├── src/
│   ├── main/java/com/wbandara/
│   │   ├── base/           # Base classes (BasePage, BaseTest, DriverFactory)
│   │   ├── pages/          # Page Object classes
│   │   ├── utils/          # Utility classes
│   │   └── listeners/      # TestNG listeners
│   └── test/java/com/wbandara/tests/
│       ├── LoginTest.java
│       ├── ProductTest.java
│       ├── CheckoutTest.java
│       └── SessionTest.java
├── .github/workflows/      # CI/CD configuration
├── testng.xml              # TestNG configuration
└── pom.xml                 # Maven configuration
```

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome or Edge browser

## 🚀 Quick Start

### Clone the repository
```bash
git clone https://github.com/wbandara-tech/selenium-testng-ecommerce-framework.git
cd selenium-testng-ecommerce-framework
```

### Run all tests
```bash
mvn clean test
```

### Run tests in headed mode (visible browser)
```bash
mvn clean test -Dheadless=false
```

### Run smoke tests only
```bash
mvn clean test -Psmoke
```

### Generate Allure Report
```bash
mvn allure:report
```

### Open Allure Report
```bash
mvn allure:serve
```

## 📦 Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| Selenium | 4.18.1 | Web automation |
| TestNG | 7.9.0 | Test framework |
| WebDriverManager | 5.7.0 | Browser driver management |
| Log4j2 | 2.23.0 | Logging |
| Allure TestNG | 2.25.0 | Reporting |
| OpenCSV | 5.9 | CSV data handling |

## 🧪 Test Modules

- **LoginTest**: Valid login scenarios
- **ProductTest**: Add to cart, remove, sorting functionality
- **CheckoutTest**: Complete checkout flow with dynamic data
- **SessionTest**: Session validation and security tests

## 🔄 CI/CD Pipeline

The project includes GitHub Actions workflow that:
1. Runs tests on every push/PR
2. Generates Allure Report
3. Deploys report to GitHub Pages

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**W. Bandara** - [wbandara-tech](https://github.com/wbandara-tech)

---

⭐ **Star this repository** if you find it helpful!
