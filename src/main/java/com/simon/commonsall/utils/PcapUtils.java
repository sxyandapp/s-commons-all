package com.simon.commonsall.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Created by ShiXiaoyong on 2020/5/11 1:50.
 */
public class PcapUtils {

<<<<<<< HEAD

=======
    public static Iterator<PcapRecord> read(File file) {
        try {
            FileInputStream fis = FileUtils.openInputStream(file);
            PcapFileHeader pcapFileHeader = PcapFileHeader.create(fis);
            if (null == pcapFileHeader) {
                IOUtils.closeQuietly(fis);
                return null;
            }
            return new PcapIterator(fis, pcapFileHeader);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return null;
    }

    public static OutputStream write(File file) {
        return new PcapOutputStream(file);
    }

    public static class PcapOutputStream extends OutputStream {

        FileOutputStream fos;

        public PcapOutputStream(File file) {
            try {
                fos = FileUtils.openOutputStream(file);
                // 写文件头
                IOUtils.write(PcapFileHeader.HEADER, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void write(int b) throws IOException {
            throw new NotImplementedException("");
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            IOUtils.write(PcapRecord.create(b, off, len), fos);
        }

        @Override
        public void flush() throws IOException {
            fos.flush();
        }

        @Override
        public void close() throws IOException {
            fos.close();
        }
    }


    @RequiredArgsConstructor
    public static class PcapIterator implements Iterator<PcapRecord>, Closeable {

        @NonNull
        FileInputStream fis;
        @NonNull
        PcapFileHeader header;
        int sequence = 0;

        @Override
        public boolean hasNext() {
            try {
                return fis.available() > 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public PcapRecord next() {
            try {
                return PcapRecord.create(fis, header, sequence);
            } finally {
                sequence++;
            }
        }

        @Override
        public void close() throws IOException {
            IOUtils.closeQuietly(fis);
        }
    }

    @Slf4j
    @Getter
    @Setter
    public static class PcapFileHeader {
        public static final int SIZE = 24; // bytes
        public static byte[] HEADER;

        static {
            try {
                HEADER = Hex.decodeHex("D4C3B2A1020004000000000000000000FFFF000001000000");
            } catch (DecoderException e) {
                e.printStackTrace();
            }
        }

        int uMagicNumber;        //Magic number (0xa1b2c3d4)
        char uVerMajor;           //Major version number
        char uVerMinor;           //Minor version number
        int uThisZone;           //GMT to local correction
        int uSigfigs;            //Accuracy of timestamps
        int uSnaplen;            //Max length of captured packets (byte)
        int uNetwork;            //Data link type

        boolean littleEndian;

        public static PcapFileHeader create(FileInputStream fis) {
            // 读取头
            ByteBuffer headerBuffer = ByteBuffer.allocate(PcapFileHeader.SIZE);
            try {
                int read = IOUtils.read(fis, headerBuffer.array());
                if (read <= 0) {
                    IOUtils.closeQuietly(fis);
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            headerBuffer.rewind();
            PcapFileHeader pcapFileHeader = new PcapFileHeader();
            pcapFileHeader.uMagicNumber = headerBuffer.getInt();
            if (Integer.parseUnsignedInt("d4c3b2a1", 16) == pcapFileHeader.uMagicNumber) {
                pcapFileHeader.littleEndian = true;
            } else if (Integer.parseUnsignedInt("a1b2c3d4", 16) == pcapFileHeader.uMagicNumber) {
                pcapFileHeader.littleEndian = false;
            } else {
                return null;
            }
            return pcapFileHeader;
        }
    }

    @Getter
    @Setter
    public static class PcapRecord {
        public static final int HEADER_SIZE = 16; // bytes

        int sec;             /* timestamp seconds */                         //This corresponds to timeSecs in RTSM header
        int msec;            /* timestamp microseconds */                    //This corresponds to timeNSecs/1000 in RTSM header
        int len;           /* number of octets of packet saved in file */  //This corresponds to storedLength in RTSM DATA header only
        int origLen;           /* actual length of packet */                   //This corresponds to rcvLength in RTSM DATA header only

        int sequence;
        byte[] data;

        public static PcapRecord create(FileInputStream fis, PcapFileHeader header, int sequence) {
            PcapRecord pcapRecord = new PcapRecord();
            pcapRecord.sequence = sequence;
            try {
                ByteBuffer allocate = ByteBuffer.allocate(HEADER_SIZE);
                if (header.littleEndian) {
                    allocate.order(ByteOrder.LITTLE_ENDIAN);
                } else {
                    allocate.order(ByteOrder.BIG_ENDIAN);
                }
                int read = IOUtils.read(fis, allocate.array());
                if (read < HEADER_SIZE) {
                    return null;
                }
                pcapRecord.sec = allocate.getInt();
                pcapRecord.msec = allocate.getInt();
                pcapRecord.len = allocate.getInt();
                pcapRecord.origLen = allocate.getInt();
                allocate = ByteBuffer.allocate(pcapRecord.len);

                read = IOUtils.read(fis, allocate.array());
                if (read != pcapRecord.len) {
                    return null;
                }
                pcapRecord.data = allocate.array();
                return pcapRecord;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static byte[] create(byte[] body, int off, int len) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ThreadUtils.sleep(10);
            try {
                // 先写头
                long now = new Date().getTime();
                ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                byteBuffer.putInt((int) (now / 1000));
                byteBuffer.putInt((int) ((now % 1000)*1000));
                byteBuffer.putInt(len);
                byteBuffer.putInt(len);
                baos.write(byteBuffer.array());
                // 写消息体
                baos.write(body, off, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
>>>>>>> c693b8cebef1dbb14a8484318c910912d0208a09

            return baos.toByteArray();
        }
    }
}
