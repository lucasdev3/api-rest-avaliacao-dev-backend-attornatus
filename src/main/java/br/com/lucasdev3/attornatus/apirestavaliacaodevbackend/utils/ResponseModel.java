package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

  private String date;

  private String message;

  public ResponseModel(String message) {
    this.date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS").format(new Date());
    this.message = message;
  }

}
