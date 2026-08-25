package com.template.validator;

public class TextoValidador implements Validador<String> {

    private static final String REGEX_LETRAS = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$";
    private final String nomeCampo;
    private final String valor;

    public TextoValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
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
        return "Digite um(a) " + nomeCampo.toLowerCase() + " válido(a) (apenas letras).";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}