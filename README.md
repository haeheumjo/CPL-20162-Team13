# CPL-20162-Team13
종합프로젝트 설계1

#Addressing structures

To establish an RFCOMM connection with another Bluetooth device, incoming or outgoing, create and fill out a struct sockaddr_rc addressing structure. Like the struct sockaddr_in that is used in TCP/IP, the addressing structure specifies the details of an outgoing connection or listening socket.


struct sockaddr_rc {
	sa_family_t	rc_family;
	bdaddr_t	rc_bdaddr;
	uint8_t		rc_channel;
};

The rc_family field specifies the addressing family of the socket, and will always be AF_BLUETOOTH. For an outgoing connection, rc_bdaddr and rc_channel specify the Bluetooth address and port number to connect to, respectively. For a listening socket, rc_bdaddr specifies the local Bluetooth adapter to use, and is typically set to BDADDR_ANY to indicate that any local Bluetooth adapter is acceptable. For listening sockets, rc_channel specifies the port number to listen on.

# A note on byte ordering
Since Bluetooth deals with the transfer of data from one machine to another, the use of a consistent byte ordering for multi-byte data types is crucial. Unlike network byte ordering, which uses a big-endian format, Bluetooth byte ordering is little-endian, where the least significant bytes are transmitted first. BlueZ provides four convenience functions to convert between host and Bluetooth byte orderings.


unsigned short int htobs( unsigned short int num );
unsigned short int btohs( unsigned short int num );
unsigned int htobl( unsigned int num );
unsigned int btohl( unsigned int num );

Like their network order counterparts, these functions convert 16 and 32 bit unsigned integers to Bluetooth byte order and back. They are used when filling in the socket addressing structures, communicating with the Bluetooth microcontroller, and when performing low level operations on transport protocol sockets.

