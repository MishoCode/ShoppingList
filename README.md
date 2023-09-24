
## General information
This is the back-end of a simple Spring Boot application that allows users to keep in mind
their shopping needs. It provides a list of the items that need to be bought. Items are divided
into categories. A user can add and remove items.
There are two authorization roles within the application - USER and ADMIN.

---  
## Main functionalities
- User registration with email confirmation
- User login using JWT (users are logged in with email and password)
- Image upload - when a user is registered, he can upload a profile picture. Image files are stored
in [Cloudinary](https://cloudinary.com/), whereas the DB stores only the url and the public id of the Cloudinary image
- Get all items from the shopping list
- Get all items from a specific category
- Users can modify the list
- Admins can also view all registered users, remove a user or change the role of a user

---  
## Technologies used
- Java 17
- Spring Boot
- Maven
- Hibernate
- Spring Security
- MySQL
- Git
- Postman

---  