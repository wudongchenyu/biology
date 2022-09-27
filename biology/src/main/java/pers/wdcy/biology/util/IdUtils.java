package pers.wdcy.biology.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class IdUtils {

	private static long lastTimeStamp = -1L;

	private volatile static AtomicInteger sq = new AtomicInteger(0);

	private final static int MAX_SDEQUENCE = 999999;

	private final static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private final static ZoneId ZONE_ID = ZoneId.systemDefault();

	private volatile static long currentTimeStamp;

	private static IntUnaryOperator IUO = (operand) -> {
		if (operand >= MAX_SDEQUENCE) {
			currentTimeStamp = getNextMill();
			return 0;
		}
		return operand + 1;
	};

	private static long getNextMill() {
		long mill = getNewStamp();
		while (mill <= lastTimeStamp) {
			mill = getNewStamp();
		}
		return 0;
	}

	private static long getNewStamp() {
		return System.currentTimeMillis();
	}

	private static long getNextSequence() {
		return sq.getAndUpdate(IUO);
	}

	public static String id() {
		currentTimeStamp = getNewStamp();
		if (currentTimeStamp < lastTimeStamp) {
			throw new RuntimeException("时钟异常");
		}
		lastTimeStamp = currentTimeStamp;
		return String.format("%s%s", Long.toString(
				Long.valueOf(df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeStamp), ZONE_ID))), 36),
				completion(Long.toString(getNextSequence(), 36), 6)).toUpperCase();
	}

	public static String completion(String hexstr, int num) {
		StringBuffer b = new StringBuffer(hexstr);
		while (b.length() < num) {
			b.insert(0, '0');
		}
		return b.toString();
	}
}
