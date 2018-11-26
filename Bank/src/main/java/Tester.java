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
        controller.doQuery(arr1);

        ArrayList<String> arr2 = new ArrayList<>();
        arr2.add("0" );
        arr2.add("2222222222222221");
        arr2.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr2);

        ArrayList<String> arr3 = new ArrayList<>();
        arr3.add("0" );
        arr3.add("2222222222222221");
        arr3.add("71530d928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr3);

        ArrayList<String> arr4 = new ArrayList<>();
        arr4.add("0" );
        arr4.add("2222222222222222");
        arr4.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr4);

        ArrayList<String> arr5 = new ArrayList<>();
        arr5.add("2" );
        arr5.add("2222222222222221");
        arr5.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        arr5.add("123");
        controller.doQuery(arr5);

        ArrayList<String> arr6 = new ArrayList<>();
        arr6.add("4" );
        arr6.add("2222222222222221");
        controller.doQuery(arr6);

        ArrayList<String> arr7 = new ArrayList<>();
        arr7.add("4" );
        arr7.add("2222222222222333");
        controller.doQuery(arr7);

        ArrayList<String> arr8 = new ArrayList<>();
        arr8.add("3" );
        arr8.add("2222222222222222");
        arr8.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr8);

        ArrayList<String> arr9 = new ArrayList<>();
        arr9.add("3" );
        arr9.add("2222222222222229");
        arr9.add("71530d9c51bd35f0592a1df87c793f837f6786f4d2812d8e0eac58ba0e3f141e17729b8d06f53aaaae1bf05073a1898af24e0928b0b143a6a145e9f55c99cb16");
        controller.doQuery(arr9);
    }
}
