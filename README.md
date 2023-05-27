# Hotel Reservation Application
The Hotel Reservation Application is a user-friendly system that allows users to easily make, update, and manage hotel reservations. With this application, you can streamline the reservation process and efficiently handle booking modifications.

Features
Create a new Reservation: Users can create new reservations by providing essential details such as guest information, check-in and check-out dates.

Update a Reservation: The application allows users to modify existing reservations. Users can change dates, update guest information, or make any necessary adjustments to the reservation details.

Find All Reservations: Users can retrieve a list of all reservations stored in the system. This feature provides an overview of current and past reservations, allowing for easy tracking and management.

Installation
Follow these steps to install and run the Hotel Reservation Application:

Clone this repository to your local machine.

Copy code
git clone https://github.com/your-username/hotel-reservation.git
run HotelReservationApplication.java

# API testing examples

Create a reservation

curl --location 'http://localhost:8081/api/create-reservation' \
--header 'Content-Type: application/json' \
--data '{
"clientFullName": "John Doe",
"fromDate": "2023-05-27",
"toDate": "2023-05-28"
}'

Update a reservation

curl --location --request PUT 'http://localhost:8081/api/update-reservation/3' \
--header 'Content-Type: application/json' \
--data '{
"id": 3,
"reservationNumber": "1003",
"clientFullName": "Johnny Doe",
"fromDate": "2023-05-27",
"toDate": "2023-05-29",
"roomNumber": 103
}'

Get all reservations

curl --location 'http://localhost:8081/api/get-reservations'

Technologies and libraries used
Java 11
Springboot
Lombok
Junit
Mockito

We hope you enjoy using the Hotel Reservation Application!
