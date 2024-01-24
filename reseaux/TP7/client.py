import socket

BUFSIZE = 1024

def client(host, port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    sock.setsockopt(socket.IPPROTO_IP,socket.IP_MULTICAST_TTL,2)
    addr = (host, port)
    while True:
        txt = "Message de quentin :"
        txt += input()
        txt += "\n"
        sock.sendto(txt.encode(), addr)

client("224.0.0.4", 5556)