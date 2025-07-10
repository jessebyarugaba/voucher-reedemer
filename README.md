# 🎟️ Voucher Redeemer API

Hi!

This is a small Spring Boot project I built to let users redeem voucher codes. You give it a voucher code, it checks if it is valid and not yet used, then marks it as used and returns package info and expiration date.

---

## 🚀 How I set it up

### Prerequisites

* Java (I used JDK 21)
* Maven
* An IDE ( I used VSCode integrated with CursorAI )

---

### Steps I followed

1. I used [Spring Initializr](https://start.spring.io/) to generate a new project.

2. I picked Maven, Java, and added these dependencies:

   * Spring Web
   * Spring Data JPA
   * H2 Database

3. After downloading, I extracted and opened it in my IDE.

---

### Database setup

In `src/main/resources`, I created:

* **schema.sql** — This defines my `voucher` table:

```sql
CREATE TABLE voucher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    used BOOLEAN NOT NULL,
    package_type VARCHAR(255),
    expiration_date DATE
);
```

* **data.sql** — This inserts a sample voucher:

```sql
INSERT INTO voucher (code, used, package_type, expiration_date)
VALUES ('ABC123', FALSE, 'Premium', '2025-12-31');
```

Spring Boot automatically runs these files when the app starts. I didn’t write any extra code to call them.

---

### Code structure

#### `VoucherApplication.java`

This is the entry point. It starts Spring Boot.

#### `model/Voucher.java`

My entity class. It maps to the `voucher` table in the DB. It has fields like code, used, package type, and expiration date.

#### `repository/VoucherRepository.java`

This talks directly to the database. I used Spring Data JPA, so I only needed to define one extra method: `findByCode`.

#### `service/VoucherService.java`

This has my main logic for redeeming vouchers. It checks if the code exists and if it has already been used. If not, it marks it as used and saves.

#### `controller/VoucherController.java`

This exposes the HTTP endpoint `/api/vouchers/redeem`. It accepts a voucher code and calls the service. It then returns a JSON response telling if it succeeded and what package info to show.

---

## 💻 How I run it

1. I ran the main application class `VoucherApplication.java`.
2. The app started on port 8080.
3. I tested it with curl and Postman.

---

## ⚔️ Challenges I faced

At first, I was trying to pass the code in the URL as a query parameter (like `?code=ABC123`). This worked fine with curl but caused weird issues with Postman.

In Postman, I realized it was easier and more consistent to send the code as JSON in the body, like:

```json
{
  "code": "ABC123"
}
```

I updated my controller to always accept JSON in the body using `@RequestBody` and stopped mixing query parameters. After that, both curl and Postman worked the same way.

---

## ✅ How to test

**POST** request to:

```
http://localhost:8080/api/vouchers/redeem
```

**Body (JSON):**

```json
{
  "code": "ABC123"
}
```

**Response if valid:**

```json
{
  "success": true,
  "packageType": "Premium",
  "expirationDate": "2025-12-31"
}
```

**Response if invalid or already used:**

```json
{
  "success": false,
  "message": "Invalid or already used voucher"
}
```

---

## 💬 Conclusion

This project helped me learn how Spring Boot automatically handles database initialization with SQL files and how to structure an API using controllers, services, and repositories. I also learned how important it is to stick to one method of passing data to avoid confusion.
