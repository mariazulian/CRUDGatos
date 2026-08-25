package com.template.validator;

import com.template.util.DialogUtil;
import java.util.ArrayList;
import java.util.List;

public class GatosValidator {

    public static boolean validarCampos(String idadeStr, String raca, String pelagem, String sexo) {
        List<Validador<String>> validadores = new ArrayList<>();

        // Adicionando os validadores de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Idade", idadeStr));
        validadores.add(new CampoObrigatorioValidador("Raça", raca));
        validadores.add(new CampoObrigatorioValidador("Pelagem", pelagem));
        validadores.add(new CampoObrigatorioValidador("Sexo", sexo));

        // Adicionando validadores com regras específicas
        validadores.add(new IdadeValidador(idadeStr));
        validadores.add(new TextoValidador("Raça", raca));
        validadores.add(new TextoValidador("Pelagem", pelagem));
        validadores.add(new TextoValidador("Sexo", sexo));

        // Itera sobre a lista sequencialmente
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                // Exibe o Dialog e interrompe a validação (Slide 18)
                DialogUtil.showWarning(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }
}