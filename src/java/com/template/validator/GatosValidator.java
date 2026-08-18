package com.template.validator;

public class GatosValidator {

    private static final String TEXTO_VALIDO_REGEX = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$";

    public static void validarCampos(String idadeStr, String raca, String pelagem, String sexo) {
        if (estaVazio(idadeStr) || estaVazio(raca) || estaVazio(pelagem) || estaVazio(sexo)) {
            throw new IllegalArgumentException("Preencha todos os campos antes de prosseguir.");
        }

        validarIdade(idadeStr);
        validarTexto(raca, "raça");
        validarTexto(pelagem, "pelagem");
        validarTexto(sexo, "sexo");
    }

    public static void validarRaca(String raca) {
        validarTexto(raca, "raça");
    }

    private static void validarTexto(String valor, String nomeCampo) {
        if (!valor.trim().matches(TEXTO_VALIDO_REGEX)) {
            throw new IllegalArgumentException("Digite uma " + nomeCampo + " válida (apenas letras).");
        }
    }

    private static void validarIdade(String idadeStr) {
        try {
            int idade = Integer.parseInt(idadeStr.trim());
            if (idade < 0 || idade > 30) {
                throw new IllegalArgumentException("A idade do gato deve estar entre 0 e 30 anos.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A idade deve ser um número inteiro válido.");
        }
    }

    private static boolean estaVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}