package test;

/**
 * Created by Mickey on 2016/11/2.
 */
class MainClassA {

    public static void main(String[] args){
        System.out.println("Class A" + args[0]);
    }
}

class MainClassB{

    public static void main(String[] args) {
        System.out.println("Class B");
        MainClassA.main(args);
    }

}
