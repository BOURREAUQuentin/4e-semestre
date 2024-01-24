import socket
import datetime

BUFSIZE = 1024
def server(port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    sock.bind(('0.0.0.0', port))
    while True:
        data, addr = sock.recvfrom(BUFSIZE)
        print(data)
        data = datetime.datetime.now()
        data = "%s\n" % data
        sock.sendto(data.encode(), addr)
        for _ in range(50):
            sock.sendto(("ta mere").encode(), addr)
        print(addr)
server(5555)