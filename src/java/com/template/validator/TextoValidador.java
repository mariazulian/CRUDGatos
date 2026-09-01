package com.template.validator;

public class TextoValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;

    public TextoValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return true; // A obrigatoriedade é validada por CampoObrigatorioValidador
        }
        return this.valor.trim().matches("^[a-zA-ZÀ-ÿ\\s]{2,}$");
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter apenas letras e ter no mínimo 2 caracteres.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}