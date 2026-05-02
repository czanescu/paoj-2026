package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        Scanner scanner = new Scanner(System.in);
        int N, id;
        double suma;
        String data, contSursa, contDestinatie, tip, input;
        N = scanner.nextInt();
        scanner.nextLine();
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < N; ++i)
        {
            input = scanner.nextLine();
            String[] inputs = input.split(" ");
            id = Integer.parseInt(inputs[0]);
            suma = Double.parseDouble(inputs[1]);
            data = inputs[2];
            tip = inputs[3];
            try{
                Tranzactie tranzactie = new Tranzactie(id, suma, data, "contSursa", "contDestinatie", tip);
                tranzactii.add(tranzactie);
            }catch(IllegalArgumentException e){
                System.out.println(e);
                return;
            }
        }
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE));
        for (Tranzactie tranzactie : tranzactii)
        {
            ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(tranzactie.getId());
            buffer.putDouble(tranzactie.getSuma());
            String paddedData = String.format("%-10s", tranzactie.getData()).substring(0, 10);
            buffer.put(paddedData.getBytes(StandardCharsets.US_ASCII));
            if (tranzactie.getTip() == TipTranzactie.CREDIT)
                buffer.put((byte) 0);
            else buffer.put((byte) 1);
            buffer.put((byte) 0);
            buffer.put(new byte[8]);
            dos.write(buffer.array(), 0, RECORD_SIZE);
        }
        dos.close();
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>
        byte[] bufferArr, dataBytes;
        ByteBuffer bb;
        String dataStr, tipStr, statusStr;
        byte tipByte, statusByte;
        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String command = scanner.next();
                switch (command) {
                    case "READ":
                        int readIdx = scanner.nextInt();
                        if (readIdx * RECORD_SIZE >= raf.length()) return; // index invalid

                        bufferArr = new byte[RECORD_SIZE];
                        raf.seek((long) readIdx * RECORD_SIZE);
                        raf.readFully(bufferArr);

                        bb = ByteBuffer.wrap(bufferArr).order(ByteOrder.LITTLE_ENDIAN);

                        id = bb.getInt();           // 0-3
                        suma = bb.getDouble();   // 4-11

                        dataBytes = new byte[10];
                        bb.get(dataBytes);              // 12-21
                        dataStr = new String(dataBytes, StandardCharsets.US_ASCII).trim();

                        tipByte = bb.get();        // 22
                        statusByte = bb.get();     // 23

                        tipStr = (tipByte == 0) ? "CREDIT" : "DEBIT";
                        statusStr = getStatusName(statusByte);

                        // [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>
                        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                                readIdx, id, dataStr, tipStr, suma, statusStr);
                        break;

                    case "UPDATE":
                        int updateIdx = scanner.nextInt();
                        String StatusNou = scanner.next();
                        byte newStatus = 0;
                        if (StatusNou.equals("PENDING"))
                            newStatus = (byte) 0;
                        else if (StatusNou.equals("PROCESSED"))
                            newStatus = (byte) 1;
                        else if (StatusNou.equals("REJECTED"))
                            newStatus = (byte) 2;
                        if (updateIdx * RECORD_SIZE + 23 >= raf.length()) return; // index invalid

                        raf.seek((long) updateIdx * RECORD_SIZE + 23);
                        raf.write(newStatus);

                        System.out.printf("Updated [%d]: %s%n", updateIdx, getStatusName(newStatus));
                        break;

                    case "PRINT_ALL":
                        long totalRecords = raf.length() / RECORD_SIZE;
                        for (int i = 0; i < totalRecords; i++) {
                            if (i * RECORD_SIZE >= raf.length()) return; // index invalid

                            bufferArr = new byte[RECORD_SIZE];
                            raf.seek((long) i * RECORD_SIZE);
                            raf.readFully(bufferArr);

                            bb = ByteBuffer.wrap(bufferArr).order(ByteOrder.LITTLE_ENDIAN);

                            id = bb.getInt();           // 0-3
                            suma = bb.getDouble();   // 4-11

                            dataBytes = new byte[10];
                            bb.get(dataBytes);              // 12-21
                            dataStr = new String(dataBytes, StandardCharsets.US_ASCII).trim();

                            tipByte = bb.get();        // 22
                            statusByte = bb.get();     // 23

                            tipStr = (tipByte == 0) ? "CREDIT" : "DEBIT";
                            statusStr = getStatusName(statusByte);

                            // [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>
                            System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                                    i, id, dataStr, tipStr, suma, statusStr);
                        }
                        break;
                }
            }
        }
        scanner.close();
    }

    private static String getStatusName(byte status) {
        return switch (status) {
            case 0 -> "PENDING";
            case 1 -> "PROCESSED";
            case 2 -> "REJECTED";
            default -> "UNKNOWN";
        };
    }
}