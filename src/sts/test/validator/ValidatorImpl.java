package sts.test.validator;

import java.util.ArrayList;

/** 
*Implementation of {@link Validator} wirtten by Alexander Burry
* 
*/
public class ValidatorImpl implements Validator {
    private final byte STX = 0x02;
    private final byte DLE = 0x10;
    private final byte ETX = 0x03;

    public boolean isValid() {
        return false;
    }

    /**
    * Indicate if the given message is valid.
    * @param message The message to check.
    * @return {@code true} if the message starts with STX, ends with ETX and the
    *         correct LRC, and has correctly-escaped data; {@code false}
    *         otherwise.
    */
    public boolean isValid(byte[] message) {
        if (message[0] != STX) {
            return false;
        }

        if (message[message.length - 2] != ETX) {
            return false;
        }

        byte[] data = delimitData(message);

        byte correctlrc = message[message.length - 1];
        boolean islrcValid = validateLRC(data, correctlrc);
        
        return islrcValid;
    }

    /**
     * Escapes data from initial message.
     * @param message
     * @return A byte array of correctly escaped data.
     */
    private byte[] delimitData(byte[] message) {
        ArrayList<Byte> dataList = new ArrayList<>();

        for (int i = 1; i < message.length - 1; i++) {
            if (message[i] != DLE && message[i] != STX && message[i] != ETX) {
                dataList.add(message[i]);
            } else if (message[i-1] == DLE) {
                dataList.add(message[i]);
            }
        }

        dataList.add(ETX);

        byte[] data = new byte[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        return data;
    }

    /**
     * Calculates LRC from escaped data and compares it to the value given in the initial 
     * message to checek if it correct.
     * @param data Data escaped from initial message.
     * @param correctlrc Correct LRC value located at the final index of the initial message
     * @return {@code true} if the calculated LRC value matches the gievn one, {@code false}
     * if the values do not match.
     */
    private boolean validateLRC(byte[] data, byte correctlrc) {
        byte lrc = 0;
        for (byte value : data) {
            lrc ^= (byte) value;
        }
        return (correctlrc == lrc);
    }

}
