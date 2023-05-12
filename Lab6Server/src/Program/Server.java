package Program;

import Commands.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class Server {

    private final String hostname;
    private final int port;

    //private DatagramSocket serverSocket;
    private DatagramChannel serverChannel;
    private InetSocketAddress address;
    private SocketAddress remoteAddress;
    private int remotePort;

    private ByteBuffer buffer;
    private byte[] bytes;

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException {
        serverChannel = DatagramChannel.open();
        address = new InetSocketAddress(hostname, port);
        serverChannel.socket().bind(address);

        System.out.println("Server started at " + getAddress());

        buffer = ByteBuffer.allocate(16384); //создает буффер

        serverChannel.configureBlocking(false);  //неблокирующий режим
        Selector selector = Selector.open();
        serverChannel.register(selector, OP_READ); //приоритет на чтение = первыми будут обрабатываться клиенты с запросом

        Lab5.consoleCommandReader = new Thread(() -> { //для запуска консали на сервере
            try {
                new ConsoleCommandReader().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Lab5.consoleCommandReader.start();


        while(true) {
            if(selector.select(1000) == 0) { //ищет запрос, поступивший на сервер
                continue;
            }

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator(); //список клиентов

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next(); //запоминает клиента

                if(key.isValid()) {
                    if(key.isAcceptable()) { //запрос на подключение
                        serverChannel = (DatagramChannel) key.channel(); //настраивание канала
                        serverChannel.configureBlocking(false);  //неблокирующий режим
                        serverChannel.register(key.selector(), OP_READ);
                        key.interestOps(OP_READ);
                    }
                    if(key.isReadable()) { //с клиента передаем данные на сераер
                        serverChannel = (DatagramChannel) key.channel();
                        Lab5.lastString = receive();  //принятие данных
                        System.out.println(Lab5.lastString);
                        new ClientCommandReader().execute();  //выполнение данных
                        serverChannel.configureBlocking(false);
                        key.interestOps(OP_WRITE);
                    }
                    if(key.isWritable()) {  //клиент ждет ответ
                        serverChannel = (DatagramChannel) key.channel();
                        if(!Lab5.output.isEmpty()) {
                            send(Lab5.output);
                            Lab5.output = "";
                        }
                        serverChannel.configureBlocking(false);
                        key.interestOps(OP_READ);
                    }
                    keyIterator.remove();  //очистка запросов
                }
            }
        }
    }

    public void close() throws IOException {
//        try {
//            send("Сервер отключён");
//        }
//        catch (Exception e) {}
        serverChannel.close();
    }

    public String receive() throws IOException {
        remoteAddress = serverChannel.receive(buffer);
        buffer.flip();
        bytes = new byte[buffer.limit()];
        buffer.get(bytes, 0, buffer.limit());
        return new String(bytes);
    }

    public void send(String message) throws IOException {
        bytes = message.getBytes();
        buffer = ByteBuffer.wrap(bytes);
        serverChannel.send(buffer, remoteAddress);
        buffer.clear();
        buffer = ByteBuffer.allocate(16384);
    }

    public String getHostname() {
        return hostname;
    }
    public int getPort() {
        return port;
    }
    public DatagramChannel getServerChannel() {
        return serverChannel;
    }
    public InetSocketAddress getAddress() {
        return address;
    }
    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }
}