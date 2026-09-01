package com.template.validator;

import com.template.util.DialogUtil;
import java.util.List;

public class GatosValidator implements IGatosValidator {

    @Override
    public boolean validarCampos(String idadeStr, String raca, String pelagem, String sexo) {
        return validarIdade(idadeStr)
                && validarRaca(raca)
                && validarPelagem(pelagem)
                && validarSexo(sexo);
    }

    @Override
    public boolean validarIdade(String idadeStr) {
        List<Validador<String>> validadores = List.of(
                new CampoObrigatorioValidador("Idade", idadeStr),
                new IdadeValidador(idadeStr)
        );
        return executarValidacoes(validadores);
    }

    @Override
    public boolean validarRaca(String raca) {
        List<Validador<String>> validadores = List.of(
                new CampoObrigatorioValidador("Raça", raca),
                new TextoValidador("Raça", raca)
        );
        return executarValidacoes(validadores);
    }

    @Override
    public boolean validarPelagem(String pelagem) {
        List<Validador<String>> validadores = List.of(
                new CampoObrigatorioValidador("Pelagem", pelagem),
                new TextoValidador("Pelagem", pelagem)
        );
        return executarValidacoes(validadores);
    }

    @Override
    public boolean validarSexo(String sexo) {
        List<Validador<String>> validadores = List.of(
                new CampoObrigatorioValidador("Sexo", sexo),
                new TextoValidador("Sexo", sexo)
        );
        return executarValidacoes(validadores);
    }

    private boolean executarValidacoes(List<Validador<String>> validadores) {
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                DialogUtil.showWarning(validador.getMensagemErro());
                return false;
            }
        }
        return true;
    }
}