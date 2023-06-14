package Program;

import Commands.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Iterator;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class Server {

    private final String hostname;
    private final int port;
    private DatagramChannel serverChannel;
    private InetSocketAddress address;
    private SocketAddress remoteAddress;
    public Exchanger<String> exchanger;
    private SQLManager sqlManager;

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

        sqlManager = new SQLManager();
        try {
            sqlManager.connectToDatabase(); //связь бд с кодом
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            SQLManager.fill(); //загрузка данных из бд в код
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        buffer = ByteBuffer.allocate(16384); //создает буффер

        serverChannel.configureBlocking(false);  //неблокирующий режим
        Selector selector = Selector.open();
        serverChannel.register(selector, OP_READ); //приоритет на чтение = первыми будут обрабатываться клиенты с запросом

        Lab5.consoleCommandReader = new Thread(() -> {
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
                SelectionKey key = keyIterator.next();

                if(key.isValid()) {
                    if(key.isAcceptable()) { //запрос на подключение
                        serverChannel = (DatagramChannel) key.channel(); //настраивание канала
                        serverChannel.configureBlocking(false);  //неблокирующий режим
                        serverChannel.register(key.selector(), OP_READ);
                        key.interestOps(OP_READ);
                    }
                    if(key.isReadable()) { //с клиента передаем данные на сераер
                        serverChannel = (DatagramChannel) key.channel(); //настраиваю канал сервера на канал клиента
//                        ExecutorService ctpool = Executors.newCachedThreadPool();
//                        ctpool.submit(()->{
//                            try {
//                                Lab5.lastString = receive();  //принятие данных
//                                System.out.println(Lab5.lastString);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                        ctpool.shutdown();
                        Lab5.lastString = receive();
                        System.out.println(Lab5.lastString);
                        exchanger = new Exchanger<>(); //потоки меняют значения на друг друга переменной exchanger (a(0) и b(1) > a(1) и b(0))
                        ForkJoinPool executorPool = new ForkJoinPool(); //создает пул потоков (у меня нет параметра, значит только 1 поток)
                        executorPool.execute(new ClientCommandReader(exchanger));
                        try {
                            Lab5.output = exchanger.exchange(Lab5.output);
                            System.out.println(Lab5.output);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        executorPool.shutdown(); //убивает поток executorPool
                        serverChannel.configureBlocking(false); //сервер может одновременно работать с нескольуими клиаентами
                        key.interestOps(OP_WRITE);
                    }
                    if(key.isWritable()) {  //клиент ждет ответ
                        serverChannel = (DatagramChannel) key.channel();
                        ExecutorService senderThreadPool = Executors.newFixedThreadPool(1);
                        senderThreadPool.execute(() -> {
                            if(!Lab5.output.isEmpty()) {
                                try {
                                    send(Lab5.output);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Lab5.output = "";
                            }
                        });
                        senderThreadPool.shutdown();
                        serverChannel.configureBlocking(false);
                        key.interestOps(OP_READ);
                    }
                    keyIterator.remove();
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

    public String receive() throws IOException { //принимает данные с клиента и превращает их в стрроку
        String c = "";
        remoteAddress = serverChannel.receive(buffer); //принимает данные
        buffer.flip(); //переводит буфер в режим чтения
        bytes = new byte[buffer.limit()]; //пустой массив байтов
        buffer.get(bytes, 0, buffer.limit()); //все данные из буфера идут в bytes
        c = new String(bytes);
        return c;
    }

    public void send(String message) throws IOException { //отправлят данные с сервера на клиент
        buffer = ByteBuffer.allocate(16384); //создание буффера
        bytes = message.getBytes(); //то, что хотим передать становится байтами
        buffer = ByteBuffer.wrap(bytes); //buffer принимает значения из bytes
        serverChannel.send(buffer, remoteAddress); //сервер отправляет данные из buffer по адресу клиента remoteAddress
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