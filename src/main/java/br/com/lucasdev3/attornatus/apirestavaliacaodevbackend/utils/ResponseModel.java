package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

  private String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS").format(new Date());

  private String message;

  private Object objectResponse;

  public ResponseModel(String message) {
    this.message = message;
  }

  public ResponseModel(String message, Object objectResponse) {
    this.message = message;
    this.objectResponse = objectResponse;
  }
}
