package proxy;

public class Client {
    public static void main(String[] args) {
//        Subject subject = new RealSubject();
//        subject.request();

        Subject proxy = new Proxy();
        proxy.request();
    }
}
