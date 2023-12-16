# ruru-craft-ecommerce
This project is an eCommerce site for selling  items. 

## Features

 **Product Management:**
  - Upload new products with details such as name, description, price, and image.
  - View a list of available products.
  
- **Customer Interaction:**
  - Customers can view product details, add products to their shopping cart, and proceed to checkout.
  - Seamless shopping experience with intuitive user interfaces.

- **Authentication and Authorization:**
  - Utilizes OAuth for secure authentication, allowing users to log in with their existing credentials from supported providers.
  - Implements JWT (JSON Web Token) for authorization, ensuring secure access to protected resources.

- **Security:**
  - Public and private keys for encryption are stored in the `certs` folder.

## Prerequisites

Make sure you have the following installed on your machine:

- Java 17
- Gradle
- MySQL (or another database of your choice)


## Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Ruth-Mwangi/ruru-craft-ecommerce
   cd ruru-craft-ecommerce
   ```

2. **Configure Database and OpenAI:**
   - Create a MySQL database and update the `application.properties` file with the database connection details.

     ```properties
     # Database Configuration
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=your_database_username
     spring.datasource.password=your_database_password
     ```

   - Define OpenAI properties in the `application.properties` file.

     ```properties
      # OpenAI Configuration
      springdoc.swagger-ui.path=/swagger-ui.html
      springdoc.api-docs.path=/api-docs
      springdoc.api-docs.version=OPENAPI_3_0
      springdoc.swagger-ui.tagsSorter=alpha
      springdoc.swagger-ui.defaultModelsExpandDepth=-1
     ```

     - Define RSA paths `application.properties` file.

     ```properties
      # RSA key file paths
      rsa.private-key=certs/private-key.pem
      rsa.public-key=certs/public-key.pem
     ```

3. **Create Certs Folder:**
   - Create a `certs` folder in the `src/main/resources` directory.
   - Generate public and private keys and store them in the `certs` folder.

5. **Build and Run:**
   ```bash
   gradlew clean build
   java -jar target/ruru-crafts-ecommerce.jar
   ```
6. **API Documentation:**
   - Explore the API documentation using Swagger UI once you run the application.
     - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
     - API Docs: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
    
## Usage

**Login:**
- Click on the "Login" button and choose your preferred OAuth provider to log in.

**Product Management:**
- Navigate to the admin dashboard to upload new products.

**Customer Interaction:**
- Browse products, view details, and add them to your shopping cart.

**Shopping Cart:**
- Review the items in your shopping cart and proceed to checkout.

**Logout:**
- Securely log out of the application.

## Contributing

We welcome contributions! Feel free to open issues or submit pull requests.

## License

This project is licensed under the MIT License.
