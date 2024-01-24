import socket

def client(host,port):
    sock=socket.socket()
    sock.connect((host,port))
    f=sock.makefile(mode="rw")
    while True:
        action = input("Enter a word :\n")
        f.write(action+"\n")
        f.flush()
        print(f.readline().strip())
        if action == "quit":
            f.write("quit\n")
            f.flush()
            f.close()
            sock.shutdown(socket.SHUT_RDWR)
            sock.close()
            break

client("localhost",5590)