package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Validador {

  private static final String FORMATO_DATA = "dd/MM/yyyy";

  private Validador() {}

  public static boolean vazio(String valor) {
    return valor == null || valor.trim().length() == 0;
  }

  public static boolean emailValido(String email) {
    if (vazio(email)) return true; // email e opcional em alguns cadastros
    int arroba = email.indexOf('@');
    int ponto = email.lastIndexOf('.');
    return arroba > 0 && ponto > arroba + 1 && ponto < email.length() - 1;
  }
//Aqui ele faz um formatador de data, interessante
  public static Date converterData(String texto) throws ParseException {
	  //Aqui ele instancia o SimpleDateFormat, que é uma lib externa, e passa pra ela o novo formato, que é o da variavel ali
    SimpleDateFormat formato = new SimpleDateFormat(FORMATO_DATA);
    formato.setLenient(false);
    //Converteu a string para Date
    return formato.parse(texto.trim());
  }

  public static String formatarData(Date data) {
    if (data == null) return "";
    return new SimpleDateFormat(FORMATO_DATA).format(data);
  }
}
