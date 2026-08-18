package com.template.validator;

public class IdadeValidador implements Validador<Integer> {

    private final Integer valor;

    public IdadeValidador(Integer valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(Integer valorAtual) {
        if (this.valor == null) {
            return false;
        }
        return this.valor >= 0 && this.valor <= 30;
    }

    @Override
    public String getMensagemErro() {
        if (this.valor == null) {
            return "O campo Idade deve ser preenchido.";
        }
        return "A idade do gato deve estar entre 0 e 30 anos.";
    }

    @Override
    public Integer getValor() {
        return this.valor;
    }
}