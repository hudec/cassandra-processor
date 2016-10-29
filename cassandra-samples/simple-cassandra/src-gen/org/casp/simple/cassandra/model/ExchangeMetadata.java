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
  
  private Instant last_updated_date;
  
  public Instant getLast_updated_date() {
    return this.last_updated_date;
  }
  
  public void setLast_updated_date(final Instant last_updated_date) {
    this.last_updated_date = last_updated_date;
  }
  
  public ExchangeMetadata _setLast_updated_date(final Instant last_updated_date) {
    this.last_updated_date = last_updated_date;
    return this;
  }
  
  public String toStringFull() {
    return "ExchangeMetadata [exchange=" + exchange + ", symbol=" + symbol + ", last_updated_date=" + last_updated_date + "]";
  }
}
