package com.template.validator;

public interface IGatosValidator {
    boolean validarCampos(String idadeStr, String raca, String pelagem, String sexo);
    boolean validarIdade(String idadeStr);
    boolean validarRaca(String raca);
    boolean validarPelagem(String pelagem);
    boolean validarSexo(String sexo);
}