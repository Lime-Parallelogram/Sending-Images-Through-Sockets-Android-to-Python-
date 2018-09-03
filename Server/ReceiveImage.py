#Imports modules
import socket

#Sets up listener
listensocket = socket.socket()
listenPort = 8000
numberOfConnections=999
thisIp = socket.gethostname()
listensocket.bind(('', listenPort))

#Starts Server
listensocket.listen(numberOfConnections)
print("Started Listening")

#Accepts Connection
(clientsocket, address) = listensocket.accept()
print("Connected")

#Define File Name
fname = "your path + your file name + file extension"

#Opens File
f = open(fname, 'w')
datain = 1

#Receives Image
while datain:
    datain = clientsocket.recv(999999999) #Gets incomming data
    f.write(datain) #Writes data to file

#Closes socket
f.close()
listensocket.close()
