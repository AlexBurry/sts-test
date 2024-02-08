package sts.test.validator;

public class RunTests {
    public static void main(String[] args) {
        Validator validator = new ValidatorImpl();

        // Valid Message - should return true
        byte[] test1 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
        System.out.println("Test 1 message is valid: " + validator.isValid(test1));

        // Missing STX - should return false
        byte[] test2 = { 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
        System.out.println("Test 2 message is valid: " + validator.isValid(test2));

        // Missing LRC - should return false
        byte[] test3 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03 };
        System.out.println("Test 3 message is valid: " + validator.isValid(test3));

        // Missing ETX - should return false
        byte[] test4 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x14 };
        System.out.println("Test 4 message is valid: " + validator.isValid(test4));

        // Invalid Delimiting - should return false
        byte[] test5 = { 0x02, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
        System.out.println("Test 5 message is valid: " + validator.isValid(test5));

        // Invalid Delimiting 2 - should return false
        byte[] test6 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x07, 0x08, 0x03, 0x14 };
        System.out.println("Test 6 message is valid: " + validator.isValid(test6));

        // Invalid LRC - should return false
        byte[] test7 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x15 };
        System.out.println("Test 7 message is valid: " + validator.isValid(test7));

        // No delimiting with two ETX values - should return true
        byte[] test8 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x03, 0x14 };
        System.out.println("Test 8 message is valid: " + validator.isValid(test8));

        // Valid delimiting with two etx values and a correct LRC- should return true
        byte[] test9 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x10, 0x03, 0x03, 0x17 };
        System.out.println("Test 9 message is valid: " + validator.isValid(test9));

        // Valid delimiting with two etx values and an incorrect LRC- should return false
        byte[] test10 = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x10, 0x03, 0x03, 0x14 };
        System.out.println("Test 10 message is valid: " + validator.isValid(test10));
    }
}
