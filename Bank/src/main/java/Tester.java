import controller.Controller;

import java.util.ArrayList;

public class Tester {
    public void test(){
        Controller controller = new Controller(6666);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("1" );
        arr.add("2222222222222221");
        arr.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        arr.add("123");
        arr.add("2222222222222222");
        controller.doQuery(arr);
        ArrayList<String> arr1 = new ArrayList<>();
        arr1.add("1" );
        arr1.add("2222222222222221");
        arr1.add("2156");
        arr1.add("123");
        arr1.add("2222222222222222");
        controller.doQuery(arr);
        ArrayList<String> arr2 = new ArrayList<>();
        arr2.add("0" );
        arr2.add("2222222222222221");
        arr2.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr);

    }
}
