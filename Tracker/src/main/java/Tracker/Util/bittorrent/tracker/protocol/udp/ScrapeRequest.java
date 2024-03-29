package Tracker.Util.bittorrent.tracker.protocol.udp;

import Tracker.Util.bittorrent.util.StringUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Offset          Size            	Name            	Value
 * 0               64-bit integer  	connection_id
 * 8               32-bit integer  	action          	2 // scrape
 * 12              32-bit integer  	transaction_id
 * 16 + 20 * n     20-byte string  	info_hash
 * 16 + 20 * N
 */

public class ScrapeRequest extends BitTorrentUDPRequestMessage {

    private List<String> infoHashes;

    public ScrapeRequest() {
        super(Action.SCRAPE);
        this.infoHashes = new ArrayList<>();
    }


    @Override
    public byte[] getBytes() {
        int hashSize = 20;
        int startSize = 16;
        int size = startSize + hashSize * infoHashes.size();

        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putLong(0, getConnectionId());
        byteBuffer.putInt(8, getAction().value());
        byteBuffer.putInt(12, getTransactionId());
        int start = hashSize;

        for (String infoHash : this.infoHashes) {
            byteBuffer.position(start);
            byteBuffer.put(new BigInteger(infoHash,16).toByteArray());
            start += hashSize;
        }
        return byteBuffer.array();
    }

    public static ScrapeRequest parse(byte[] byteArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        ScrapeRequest scrapeRequest = new ScrapeRequest();
        scrapeRequest.setConnectionId(byteBuffer.getLong(0));
        scrapeRequest.setAction(Action.valueOf(byteBuffer.getInt(8)));
        scrapeRequest.setTransactionId(byteBuffer.getInt(12));

        boolean error = false;

        for (int index = 16; index < byteArray.length && !error; index += 20) {
            byte[] infoBytes = new byte[20];
            byteBuffer.position(index);
            byteBuffer.get(infoBytes);
            String infoHash = StringUtils.toHexString(infoBytes);
            if (!infoHash.matches("[0]+")) {
                scrapeRequest.addInfoHash(infoHash);
            } else {
                error = true;
            }
        }
        return scrapeRequest;
    }

    public List<String> getInfoHashes() {
        return infoHashes;
    }

    public void addInfoHash(String infoHash) {
        if (infoHash != null && !infoHash.trim().isEmpty() && !this.infoHashes.contains(infoHash)) {
            this.infoHashes.add(infoHash);
        }
    }
}
