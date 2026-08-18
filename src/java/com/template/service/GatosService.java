package com.template.service;

import com.template.model.dao.GatosDAO;
import com.template.model.dto.GatosDTO;
import com.template.validator.GatosValidator;
import java.util.List;

public class GatosService {

    private final GatosDAO gatosDAO = new GatosDAO();

    public void salvarGato(String idadeStr, String raca, String pelagem, String sexo) {
        GatosValidator.validarCampos(idadeStr, raca, pelagem, sexo);

        GatosDTO dto = new GatosDTO();
        dto.setIdade(Integer.parseInt(idadeStr.trim()));
        dto.setRaca(raca.trim());
        dto.setPelagem(pelagem.trim());
        dto.setSexo(sexo.trim());

        gatosDAO.cadastrarGatos(dto);
    }

    //implementação do atualizarGato
    public void atualizarGato(GatosDTO selecionado, String idadeStr, String raca, String pelagem, String sexo) {
        if (selecionado == null) {
            throw new IllegalArgumentException("Nenhum gato selecionado para atualização.");
        }

        GatosValidator.validarCampos(idadeStr, raca, pelagem, sexo);

        selecionado.setIdade(Integer.parseInt(idadeStr.trim()));
        selecionado.setRaca(raca.trim());
        selecionado.setPelagem(pelagem.trim());
        selecionado.setSexo(sexo.trim());

        gatosDAO.atualizarGatos(selecionado);
    }

    //implementação do excluirGato
    public void excluirGato(GatosDTO selecionado) {
        if (selecionado == null) {
            throw new IllegalArgumentException("Nenhum gato selecionado para exclusão.");
        }

        gatosDAO.deletarGatos(selecionado.getId());
    }

    public List<GatosDTO> listarGatos() {
        return gatosDAO.selecionarGatos();
    }
}