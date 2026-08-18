package com.template.validator;

public class RacaValidador implements Validador<String> {

    private static final String REGEX_LETRAS = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$";
    private final String valor;

    public RacaValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        return this.valor.trim().matches(REGEX_LETRAS);
    }

    @Override
    public String getMensagemErro() {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return "O campo Raça deve ser preenchido.";
        }
        return "Digite uma Raça válida (apenas letras).";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}