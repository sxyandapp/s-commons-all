import com.simon.commonsall.utils.PcapUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

/**
 * Created by ShiXiaoyong on 2020/5/11 13:24.
 */
@Slf4j
public class PcapUtilsTest {

    @Test
    public void testRead() {
        Iterator<PcapUtils.PcapRecord> read = PcapUtils.read(FileUtils.getFile("src/test/resources/20200510-128.pcap"));
        while (read.hasNext()) {
            PcapUtils.PcapRecord next = read.next();
            if (null!=next){
                log.info("seq:{},len:{},date:{}", next.getSequence(), next.getLen(), DateFormatUtils.format(new Date(next.getSec() * 1000L + next.getMsec()/1000), "yyyy-MM-dd HH:mm:ss"));
            }
        }
        log.info("end");
    }

    @Test
    public void testWrite() {
        OutputStream write = PcapUtils.write(FileUtils.getFile("d:/desktop/t.pcap"));
        for (int i=0;i<10;i++){
            try {
                write.write(Hex.decodeHex("01020304"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IOUtils.closeQuietly(write);
        log.info("end");
    }
}
