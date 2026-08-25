package com.template.validator;

public class IdadeValidador implements Validador<String> {

    private final String valor;

    public IdadeValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        try {
            int idade = Integer.parseInt(this.valor.trim());
            return idade >= 0 && idade <= 30;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return "O campo Idade deve ser preenchido.";
        }
        try {
            Integer.parseInt(this.valor.trim());
        } catch (NumberFormatException e) {
            return "A idade deve ser um número inteiro válido.";
        }
        return "A idade do gato deve estar entre 0 e 30 anos.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}