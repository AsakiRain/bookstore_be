import random
import pymysql

conn = pymysql.connect(host="localhost", port=6033, user="crud",
                       passwd="ssr129631", db="bookstore", autocommit=True, charset='utf8')
cursor = conn.cursor()
cursor.execute("select isbn from books")
rows = cursor.fetchall()
indexes = set()

while(len(indexes) < 200):
    index = random.randint(0, len(rows)-1)
    indexes.add(index)

for index in indexes:
    cursor.execute("insert into lists(username, isbn) values(%s, %s)", ("nahida", rows[index][0]))