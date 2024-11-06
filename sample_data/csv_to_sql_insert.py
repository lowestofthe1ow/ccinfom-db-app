import sys
import csv

writeTo = open(sys.argv[1], "a")
readFrom = open(sys.argv[2], 'r')
csvFile = csv.reader(readFrom)
header = next(csvFile)
headers = map((lambda x: '`'+x+'`'), header)

insert = 'INSERT INTO ' + sys.argv[3] + ' (' + ", ".join(headers) + ") VALUES "

for row in csvFile:
    values = map((lambda x: (x if x == "NULL" else "'"+x.replace("'", "\\'")+"'")), row)
    writeTo.write(insert +"("+ ", ".join(values) +");\n" )

writeTo.write("\n")
readFrom.close()