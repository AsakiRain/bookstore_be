import pymysql

USD_TO_RMB = 6.7765

USD_DICT = [
    "USD",
    "$",
    "美元"
]

CLEAN_DICT = [
    "USD",
    "$",
    "美元",
    "CNY",
    "￥",
    "¥",
    "人民币",
    "元",
]

FIX_DICT = {
    "。": ".",
}


def getRows() -> list[list[str, str]]:
    cursor.execute("select isbn, price from stocks")
    rows = cursor.fetchall()
    return rows


def convertPrice(price_raw: str) -> float:
    multiplier: float = 1
    for word in USD_DICT:
        if word in price_raw:
            multiplier = USD_TO_RMB
            break
    price_text: str = price_raw
    for word in CLEAN_DICT:
        price_text = price_text.replace(word, "")
    for key in FIX_DICT:
        price_text = price_text.replace(key, FIX_DICT[key])
    price: float = float(price_text)
    # convert to RMB
    price = price * multiplier
    price = round(price, 2)
    print("{} → {} → {}".format(price_raw, price_text, price))
    return price


def price2cost() -> None:
    rows = getRows()
    for row in rows:
        price = convertPrice(row[1])
        cursor.execute("update stocks set price=%s, cost=%s where isbn=%s",
                       (price, int(price), row[0]))


conn = pymysql.connect(host="localhost", port=6033, user="crud",
                       passwd="ssr129631", db="bookstore", autocommit=True, charset='utf8')
cursor = conn.cursor()
price2cost()
