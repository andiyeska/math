
# Project Description
```
    This is a REST project to solve equation given. This project focus on helping sutdent to get a better understanding on how to solve a math equation step by step    
```

# Preqrequisite
```
    - Java 8
    - Maven 3.x
```

# How to Run Service
- Open terminal and locate into the project	
```
    cd {project_location}
```

- Build jar by running command 
```
    mvn clean install
```

- Run the service by running command
```
    java .jar target/math.1.jar
```

# API Documentation

## Single Variable Non Quadratic Equation

- Path
```
    /mathematics/equation/quadratic/non
```

- Method
```
    POST
```

- Sample Request
```
    {
        "equation": "2(4x+3)+6=24-4x"
    }
```

- Sample Response
```
    {
        "solution": {
            "equation": "2(4x+3)+6=24-4x",
            "steps": [
                {
                    "description": "Distribute",
                    "leftEquationBefore": "2(4x+3)+6",
                    "leftEquationAfter": "8x+6+6",
                    "rightEquationBefore": "24-4x",
                    "rightEquationAfter": "24-4x",
                    "leftOperationBefore": "2(4x+3)",
                    "leftOperationAfter": "8x+6",
                    "rightOperationBefore": null,
                    "rightOperationAfter": null
                },
                {
                    "description": "Simplify",
                    "leftEquationBefore": "8x+6+6",
                    "leftEquationAfter": "8x+12",
                    "rightEquationBefore": "24-4x",
                    "rightEquationAfter": "24-4x",
                    "leftOperationBefore": "8x+6+6",
                    "leftOperationAfter": "8x+12",
                    "rightOperationBefore": null,
                    "rightOperationAfter": null
                },
                {
                    "description": "Move terms with variable to left and digit to right",
                    "leftEquationBefore": "8x+12",
                    "leftEquationAfter": "12x",
                    "rightEquationBefore": "24-4x",
                    "rightEquationAfter": "12",
                    "leftOperationBefore": "8x+12-12+4x",
                    "leftOperationAfter": "12x",
                    "rightOperationBefore": "24-4x-12+4x",
                    "rightOperationAfter": "12"
                },
                {
                    "description": "Set variable coefficient to 1",
                    "leftEquationBefore": "12x",
                    "leftEquationAfter": "x",
                    "rightEquationBefore": "12",
                    "rightEquationAfter": "1",
                    "leftOperationBefore": "12x/12",
                    "leftOperationAfter": "x",
                    "rightOperationBefore": "12/12",
                    "rightOperationAfter": "1"
                }
            ],
            "leftTerms": [
                {
                    "numerator": 1,
                    "denominator": 1,
                    "variable": {
                        "numeratorLetters": {
                            "x": 1
                        },
                        "denominatorLetters": {},
                        "empty": false
                    }
                }
            ],
            "rightTerms": [
                {
                    "numerator": 1,
                    "denominator": 1,
                    "variable": null
                }
            ],
            "termByVariable": {
                "x": {
                    "numerator": 1,
                    "denominator": 1,
                    "variable": null
                }
            }
        }
    }
```
