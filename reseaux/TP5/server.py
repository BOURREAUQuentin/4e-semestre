# pylint: disable=missing-class-docstring
import socket

class Server:
    def __init__(self):
        self.counter=0

    def mainServer(self,port):
        sock=socket.socket()
        sock.bind(("0.0.0.0",port))
        sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
        sock.listen(10)
        while True:
            cli,_ = sock.accept()
            sess=Session(self,cli)
            sess.mainSession()

class Session:
    def __init__(self,server,sock):
        self.server=server
        self.socket=sock
        self.file=sock.makefile(mode="rw")

    def mainSession(self):
        while True:
            line = self.file.readline().strip()
            if line == "get":
                self.file.write("val actual :  %d\n" % self.server.counter)
                self.file.flush()
            elif line == "incr":
                self.server.counter += 1
                self.file.write("Incremented. New val : %d\n" % self.server.counter)
                self.file.flush()
            elif line == "decr":
                self.server.counter -= 1
                self.file.write("Decremented. New val : %d\n" % self.server.counter)
                self.file.flush()
            elif "add" in line:
                valLine = line.split("add ")
                print(valLine)
                # probleme de add -r qui ne marche pas
                if (valLine[1][0] == "-" or valLine[1][0].isdigit()) and (len(valLine[1]) >= 1 or valLine[1][1:].isdigit()):
                    self.server.counter += int(valLine[1])
                    self.file.write("Modified. New val : %d\n" % self.server.counter)
                    self.file.flush()
                else:
                    self.file.write("err\n")
                    self.file.flush()
            elif line == "quit":
                self.file.write("Bye bye !\n")
                self.file.flush()
                break
            else:
                self.file.write("err\n")
                self.file.flush()
        self.file.close()
        self.socket.shutdown(socket.SHUT_RDWR)
        self.socket.close()

Server().mainServer(5590)