package linux.base;

public class JavaProcessKill {

    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print("shutdown hook execution");
        }));
        while (true) {

        }
    }
}
