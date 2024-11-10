## How to run

1. Initialize the database with `livehouse.sql` and `transaction-procedures.sql`, then populate it with `sample_data/base_records.sql` and `sample_data/transaction_records.sql`

2. Create a file named `.env` in the root directory containing your login credentials for the MySQL server. Its contents
   should be as follows:

```
JDBC_URL=jdbc:mysql://localhost:3306/livehouse
JDBC_USER=root
JDBC_PASSWORD=tobisawamisaki
```

3. Download the following `.jar` files into `BocchiTheApp/lib`:

-   [`dotenv-java-3.0.2.jar`](https://repo1.maven.org/maven2/io/github/cdimascio/dotenv-java/3.0.2/dotenv-java-3.0.2.jar)
-   [`mysql-connector-j-9.1.0.jar`](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar)

## References

-   [JDBC API tutorial](https://www.youtube.com/watch?v=BOUMR85B-V0&list=PLhs1urmduZ2-yp3zID5rMEmXDETN8xvMo&pp=iAQB)
