package com.stonedt.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SnowFlake {
  private long workerId;
  
  private long datacenterId;
  
  private long sequence;
  
  public SnowFlake() {
    this.workerId = 1L;
    this.datacenterId = 2L;
    this.sequence = 1000L;
  }
  
  public SnowFlake(long workerId, long datacenterId, long sequence) {
    if (workerId > this.maxWorkerId || workerId < 0L)
      throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(this.maxWorkerId) })); 
    if (datacenterId > this.maxDatacenterId || datacenterId < 0L)
      throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(this.maxDatacenterId) })); 
    System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", new Object[] { Long.valueOf(this.timestampLeftShift), Long.valueOf(this.datacenterIdBits), Long.valueOf(this.workerIdBits), Long.valueOf(this.sequenceBits), Long.valueOf(workerId) });
    this.workerId = workerId;
    this.datacenterId = datacenterId;
    this.sequence = sequence;
  }
  
  private long twepoch = 1288834974657L;
  
  private long workerIdBits = 5L;
  
  private long datacenterIdBits = 5L;
  
  private long maxWorkerId = 0xFFFFFFFFFFFFFFFFL ^ -1L << (int)this.workerIdBits;
  
  private long maxDatacenterId = 0xFFFFFFFFFFFFFFFFL ^ -1L << (int)this.datacenterIdBits;
  
  private long sequenceBits = 12L;
  
  private long workerIdShift = this.sequenceBits;
  
  private long datacenterIdShift = this.sequenceBits + this.workerIdBits;
  
  private long timestampLeftShift = this.sequenceBits + this.workerIdBits + this.datacenterIdBits;
  
  private long sequenceMask = 0xFFFFFFFFFFFFFFFFL ^ -1L << (int)this.sequenceBits;
  
  private long lastTimestamp = -1L;
  
  public long getWorkerId() {
    return this.workerId;
  }
  
  public long getDatacenterId() {
    return this.datacenterId;
  }
  
  public long getTimestamp() {
    return System.currentTimeMillis();
  }
  
  public synchronized long getId() {
    long timestamp = timeGen();
    if (timestamp < this.lastTimestamp) {
      System.err.printf("clock is moving backwards.  Rejecting requests until %d.", new Object[] { Long.valueOf(this.lastTimestamp) });
      throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[] { Long.valueOf(this.lastTimestamp - timestamp) }));
    } 
    if (this.lastTimestamp == timestamp) {
      this.sequence = this.sequence + 1L & this.sequenceMask;
      if (this.sequence == 0L)
        timestamp = tilNextMillis(this.lastTimestamp); 
    } else {
      this.sequence = 0L;
    } 
    this.lastTimestamp = timestamp;
    return timestamp - this.twepoch << (int)this.timestampLeftShift | this.datacenterId << (int)this.datacenterIdShift | this.workerId << (int)this.workerIdShift | this.sequence;
  }
  
  private long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp)
      timestamp = timeGen(); 
    return timestamp;
  }
  
  private long timeGen() {
    return System.currentTimeMillis();
  }
  
  public List<Long> getId(int num) {
    List<Long> list = new ArrayList<>();
    Set<Long> set = new HashSet<>();
    for (int i = 0; i < num + num / 100; i++)
      set.add(Long.valueOf(getId())); 
    Iterator<Long> value = set.iterator();
    for (int j = 0; j < num; j++)
      list.add(value.next()); 
    return list;
  }
  
  public static void main(String[] args) {
    System.out.println((new SnowFlake()).getId());
  }
}
