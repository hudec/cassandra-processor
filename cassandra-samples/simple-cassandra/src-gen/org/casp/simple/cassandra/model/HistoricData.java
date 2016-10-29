package org.casp.simple.cassandra.model;

import java.util.Date;
import org.sqlproc.engine.annotation.Pojo;

@Pojo
@SuppressWarnings("all")
public class HistoricData {
  private String exchange;
  
  public String getExchange() {
    return this.exchange;
  }
  
  public void setExchange(final String exchange) {
    this.exchange = exchange;
  }
  
  public HistoricData _setExchange(final String exchange) {
    this.exchange = exchange;
    return this;
  }
  
  private String symbol;
  
  public String getSymbol() {
    return this.symbol;
  }
  
  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }
  
  public HistoricData _setSymbol(final String symbol) {
    this.symbol = symbol;
    return this;
  }
  
  private Date date;
  
  public Date getDate() {
    return this.date;
  }
  
  public void setDate(final Date date) {
    this.date = date;
  }
  
  public HistoricData _setDate(final Date date) {
    this.date = date;
    return this;
  }
  
  private Double open;
  
  public Double getOpen() {
    return this.open;
  }
  
  public void setOpen(final Double open) {
    this.open = open;
  }
  
  public HistoricData _setOpen(final Double open) {
    this.open = open;
    return this;
  }
  
  private Double high;
  
  public Double getHigh() {
    return this.high;
  }
  
  public void setHigh(final Double high) {
    this.high = high;
  }
  
  public HistoricData _setHigh(final Double high) {
    this.high = high;
    return this;
  }
  
  private Double low;
  
  public Double getLow() {
    return this.low;
  }
  
  public void setLow(final Double low) {
    this.low = low;
  }
  
  public HistoricData _setLow(final Double low) {
    this.low = low;
    return this;
  }
  
  private Double close;
  
  public Double getClose() {
    return this.close;
  }
  
  public void setClose(final Double close) {
    this.close = close;
  }
  
  public HistoricData _setClose(final Double close) {
    this.close = close;
    return this;
  }
  
  private Integer volume;
  
  public Integer getVolume() {
    return this.volume;
  }
  
  public void setVolume(final Integer volume) {
    this.volume = volume;
  }
  
  public HistoricData _setVolume(final Integer volume) {
    this.volume = volume;
    return this;
  }
  
  private Double adjClose;
  
  public Double getAdjClose() {
    return this.adjClose;
  }
  
  public void setAdjClose(final Double adjClose) {
    this.adjClose = adjClose;
  }
  
  public HistoricData _setAdjClose(final Double adjClose) {
    this.adjClose = adjClose;
    return this;
  }
  
  public String toStringFull() {
    return "HistoricData [exchange=" + exchange + ", symbol=" + symbol + ", date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", adjClose=" + adjClose + "]";
  }
}
