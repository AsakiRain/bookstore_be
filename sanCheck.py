import random
import pymysql

conn = pymysql.connect(host="localhost", port=6033, user="crud",
                       passwd="ssr129631", db="bookstore", autocommit=True, charset='utf8')
cursor = conn.cursor()
cursor.execute("select isbn, rating_people from books")
rows = cursor.fetchall()

for row in rows:
    if row[1] == "null":
        cursor.execute(
            "update books set rating_people = null where isbn = %s", row[0])
        print("Updated rating for isbn: " + row[0])
