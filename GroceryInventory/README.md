# Super Store Inventory Management system

## Description

This is a project to aid stores and warehouses manage their products stock and keep track of sales. The program was written in Java and the data persisted in a MySQL database.
Different categories of products are stored in different data structures.

### Categories

**-- Stack Categories --**

1. Beverages – coffee/tea, juice, soda
2. Bread/Bakery – sandwich loaves, dinner rolls, tortillas, bagels
3. Canned/Jarred Goods – vegetables, spaghetti sauce, ketchup
4. Dairy – cheeses, eggs, milk, yoghurt, butter

**-- Queue Categories --**

5. Dry/Baking Goods – cereals, flour, sugar, pasta, mixes
6. Frozen Foods – waffles, vegetables, individual meals, ice cream
7. Meat – lunch meat, poultry, beef, pork

**-- List Categories --**

8. Produce – fruits, vegetables
9. Cleaners – all-purpose, laundry detergent, dishwashing liquid/detergent
10. Paper Goods – paper towels, toilet paper, aluminium foil, sandwich bags
11. Personal Care – shampoo, soap, hand soap, shaving cream

## Features

- Adding Products
- Editing Product details
- Removing Products
- Adding Vendors
- Editing Vendor details
- Removing Vendors
- Creating Orders/Sales
- Viewing Orders/Sales
- Manage products according to the data structure they are contained in.

## Project Structure

```
SuperStore
|__ .settings
|       org.eclipse.core.resources.prefs
|       org.eclipse.jdt.core.prefs
|__ bin
|   |__application
|       |__ dataStructures
|               ArrayStack.class
|               CircularArrayQueue.class
|               ElementNotFoundException.class
|               EmptyCollectionException.class
|               ListADT.class
|               QueueADT.class
|               StackADT.class
|               UorderedArrayList.class
|               UnorderedListADT.class
|           AddProduct.fxml
|           AddProduct2.fxml
|           AddProductController.class
|           AddProductController2.class
|           AddToCart.fxml
|           AddToCartController.class
|           AddVendor.fxml
|           AddVendorController.class
|           application.css
|           Application.fxml
|           ApplicationController.class
|           BarcodeGenerator.class
|           Bill.class
|           BillController.class
|           Category.class
|           config.properties
|           DB_Connection.class
|           EditProduct.fxml
|           EditProductController.class
|           EditVendor.fxml
|           EditVendorController.class
|           EnqueueProduct.fxml
|           EnqueueProductController.class
|           ErrorMessage.class
|           Main.class
|           Order.class
|           Product.class
|           Products.fxml
|           ProductsController.class
|           PushProduct.fxml
|           PushProductController.class
|           RemoveProduct.fxml
|           RemoveProductController.class
|           Reports.fxml
|           ReportsController.class
|           Sales.fxml
|           SalesController.class
|           Vendor.class
|           VendorController.class
|           Vendors.fxml
|__ jar
|       barbecue-1.5-beta1.jar
|       dotenv-java-2.2.4.jar
|       ikonli-core-12.3.1.jar
|       ikonli-fontawesome5-pack-12.3.1.jar
|       ikonli-javafx-12.3.1.jar
|       json-20220320.jar
|       mysql-connector-java-8.0.30m.jar
|__ sql
|       inventory_management_system data.sql
|       inventory_management_system.sql
|       inventory_management_system_category.sql
|       inventory_management_system_order.sql
|       inventory_management_system_order_list.sql
|       inventory_management_system_product.sql
|       inventory_management_system_supplier.sql
|       inventory_management_system_user.sql
|__ src
|   |__application
|       |__ dataStructures
|               ArrayStack.java
|               CircularArrayQueue.java
|               ElementNotFoundException.java
|               EmptyCollectionException.java
|               ListADT.java
|               QueueADT.java
|               StackADT.java
|               UorderedArrayList.java
|               UnorderedListADT.java
|           AddProduct.fxml
|           AddProduct2.fxml
|           AddProductController.java
|           AddProductController2.java
|           AddToCart.fxml
|           AddToCartController.java
|           AddVendor.fxml
|           AddVendorController.java
|           application.css
|           Application.fxml
|           ApplicationController.java
|           BarcodeGenerator.java
|           Bill.java
|           BillController.java
|           Category.java
|           config.properties
|           DB_Connection.java
|           EditProduct.fxml
|           EditProductController.java
|           EditVendor.fxml
|           EditVendorController.java
|           EnqueueProduct.fxml
|           EnqueueProductController.java
|           ErrorMessage.java
|           Main.java
|           Order.java
|           Product.java
|           Products.fxml
|           ProductsController.java
|           PushProduct.fxml
|           PushProductController.java
|           RemoveProduct.fxml
|           RemoveProductController.java
|           Reports.fxml
|           ReportsController.java
|           Sales.fxml
|           SalesController.java
|           Vendor.java
|           VendorController.java
|           Vendors.fxml
|   .classpath
|   .env
|   .project
|   build.fxbuild
|   NBC_Superstore.png
```

## Project Setup

### Requirements

- MySQL Server
- Eclipse Java IDE
  - E(fx)clipse plugin
- JDK 18
- JavaFX 18 SDK

### Installation

1. Download and install MySQL Server and get it running. Use [Getting Started with MySQL](https://dev.mysql.com/doc/mysql-getting-started/en/).
2. Open the `.env` file in the project root and fill in the database configuration variables as they are setup on your machine.
3. Open the project as an Eclipse project. You can use the `.project` file to set it up.
4. Install the E(fx)clipse plugin from the eclipse marketplace.
5. Setup your Eclipse project for JavaFX by following [Getting Stared with JavaFX](https://openjfx.io/openjfx-docs/#IDE-Eclipse). You can also check out how to set up the project on other IDEs. (We used Eclipse for development and so all intructions relate to Eclipse).

### Run

Run the projet using the run configurations created in step 5 of [Installation](#installation)


## License

[MIT License](https://opensource.org/licenses/MIT)

## Project Status

This project is an end of semester delivera ble for DCIT 308 and may no longer be manitained by the authors post-grading. Open to forks and volunteers.
