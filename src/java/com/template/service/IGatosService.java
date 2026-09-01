package com.template.service;

import com.template.model.dto.GatosDTO;
import java.util.List;

public interface IGatosService {
    boolean salvarGato(String idadeStr, String raca, String pelagem, String sexo);
    boolean atualizarGato(GatosDTO selecionado, String idadeStr, String raca, String pelagem, String sexo);
    void excluirGato(GatosDTO selecionado);
    List<GatosDTO> listarGatos();
}