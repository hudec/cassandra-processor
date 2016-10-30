package org.casp.simple.cassandra.model;

import java.time.Instant;
import org.sqlproc.engine.annotation.Pojo;

@Pojo
@SuppressWarnings("all")
public class ExchangeMetadata {
  private String exchange;
  
  public String getExchange() {
    return this.exchange;
  }
  
  public void setExchange(final String exchange) {
    this.exchange = exchange;
  }
  
  public ExchangeMetadata _setExchange(final String exchange) {
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
  
  public ExchangeMetadata _setSymbol(final String symbol) {
    this.symbol = symbol;
    return this;
  }
  
  private Instant lastUpdatedDate;
  
  public Instant getLastUpdatedDate() {
    return this.lastUpdatedDate;
  }
  
  public void setLastUpdatedDate(final Instant lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }
  
  public ExchangeMetadata _setLastUpdatedDate(final Instant lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
    return this;
  }
  
  public String toStringFull() {
    return "ExchangeMetadata [exchange=" + exchange + ", symbol=" + symbol + ", lastUpdatedDate=" + lastUpdatedDate + "]";
  }
}
