# BMI-Calculator
The "BMI Calculator" project will provide users with a quick and easy tool to calculate their Body Mass Index (BMI).

BMI: Body Mass Index, a measure of body fat based on height and weight.


# API Endpoints

POST /bmi/calculate: http://localhost:80/bmi/calculate

GET /bmi/history: http://localhost:80/bmi/history

Getting all history from the Database.

Body Return:

[
    { 
        "id": "6744de481760d52ba08e03a4",
        "nome": "Agnes",
        "altura": 1.9,
        "peso": 65.0,
        "imc": 18.005540166204987
    },

    
    {
        "id": "6744e82355b22b16fe8fa5dd",
        "nome": "Geysiane",
        "altura": 1.72,
        "peso": 65.0,
        "imc": 21.971335857220122
    }
]



# NGINX
The project to implement a server proxy using an NGINX server and should be considered for demonstration or testing purposes only.
