## General info

A live house (ライブハウス, raibu hausu) is a type of music venue prevalent in Japan. It features live music from relatively smaller performers. Traditionally, these venues operate under a quota system (ノルマ, noruma) that requires performers to sell a certain number of tickets. This business model would benefit from using a database system as doing so would streamline the management process (e.g., when relating records to each other); a spreadsheet in this case would quickly become cumbersome and prone to errors.

Developed by:
Marqueses, Lorenz Bernard B.
Cesar, Jusper Angelo M.
Silva, Paulo Grane Gabriel C.

Proposal link:
https://docs.google.com/document/d/1ZmCvGMf5r2TxYZaf5cglUBvxg7jh8-sEziFgCaXIkqo/edit?usp=sharing

## How to run

1. Initialize the database with `database/livehouse.sql` and `database/transaction-procedures.sql`, then populate it with `database/sample_data/base_records.sql` and `database/sample_data/transaction_records.sql`

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
-   [`flatlaf-1.1-javadoc.jar`](https://repo1.maven.org/maven2/com/formdev/flatlaf/1.1/flatlaf-1.1-javadoc.jar)
-   [`flatlaf-1.1.jar`](https://repo1.maven.org/maven2/com/formdev/flatlaf/1.1/flatlaf-1.1.jar)
-   [`flatlaf-1.1-sources.jar`](https://repo1.maven.org/maven2/com/formdev/flatlaf/1.1/flatlaf-1.1-sources.jar)
  
## References

-   [JDBC API tutorial](https://www.youtube.com/watch?v=BOUMR85B-V0&list=PLhs1urmduZ2-yp3zID5rMEmXDETN8xvMo&pp=iAQB)
-   [Flatlaf website](https://www.formdev.com/flatlaf/)
