import socket

HOST='172.20.10.12'
PORT =5003

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Connecting to ' + HOST)
s.connect((HOST,PORT))
try:
    message = b'Hello Wrold'
    print('Sending ' + format(message))
    s.sendall(message)

    while True:
        data = s.recv(128)
        if data:
            print('Received ' + format(data))
finally:
    s.close()
