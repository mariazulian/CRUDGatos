package com.template.service;

import com.template.model.dao.GatosDAO;
import com.template.model.dto.GatosDTO;
import com.template.validator.GatosValidator;
import com.template.util.DialogUtil;
import java.util.List;

public class GatosService {

    private final GatosDAO gatosDAO = new GatosDAO();

    public boolean salvarGato(String idadeStr, String raca, String pelagem, String sexo) {
        if (!GatosValidator.validarCampos(idadeStr, raca, pelagem, sexo)) {
            return false;
        }

        GatosDTO dto = new GatosDTO();
        dto.setIdade(Integer.parseInt(idadeStr.trim()));
        dto.setRaca(raca.trim());
        dto.setPelagem(pelagem.trim());
        dto.setSexo(sexo.trim());

        gatosDAO.cadastrarGatos(dto);
        return true;
    }

    public boolean atualizarGato(GatosDTO selecionado, String idadeStr, String raca, String pelagem, String sexo) {
        if (selecionado == null) {
            DialogUtil.showError("Nenhum gato selecionado para atualização.");
            return false;
        }

        if (!GatosValidator.validarCampos(idadeStr, raca, pelagem, sexo)) {
            return false;
        }

        selecionado.setIdade(Integer.parseInt(idadeStr.trim()));
        selecionado.setRaca(raca.trim());
        selecionado.setPelagem(pelagem.trim());
        selecionado.setSexo(sexo.trim());

        gatosDAO.atualizarGatos(selecionado);
        return true;
    }

    public void excluirGato(GatosDTO selecionado) {
        if (selecionado == null) {
            DialogUtil.showError("Nenhum gato selecionado para exclusão.");
            return;
        }
        gatosDAO.deletarGatos(selecionado.getId());
    }

    public List<GatosDTO> listarGatos() {
        return gatosDAO.selecionarGatos();
    }
}