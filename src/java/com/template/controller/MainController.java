package com.template.controller;

import com.template.model.dto.GatosDTO;
import com.template.service.GatosService;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML private Button btnSalvar, btnAlterar, btnExcluir, btnLimpar;
    @FXML private TextField txtID, txtIdade, txtPelagem, txtRaca, txtSexo;
    @FXML private Label lblMensagem;
    @FXML private TableView<GatosDTO> tblGatos;
    @FXML private TableColumn<GatosDTO, Integer> colID, colIdade;
    @FXML private TableColumn<GatosDTO, String> colRaca, colPelagem, colSexo;

    private final GatosService gatoService = new GatosService();

    @FXML
    private void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colPelagem.setCellValueFactory(new PropertyValueFactory<>("pelagem"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        txtID.setEditable(false);
        btnLimparAction();
        carregarGatos();
    }

    @FXML
    private void btnSalvarAction() {
        try {
            boolean sucesso = gatoService.salvarGato(txtIdade.getText(), txtRaca.getText(), txtPelagem.getText(), txtSexo.getText());
            if (sucesso) {
                DialogUtil.showInformation("Gato cadastrado com sucesso!");
                carregarGatos();
                btnLimparAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void btnAlterarAction() {
        try {
            GatosDTO selecionado = tblGatos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                DialogUtil.showWarning("Selecione um gato na tabela para alterar!");
                return;
            }

            boolean sucesso = gatoService.atualizarGato(selecionado, txtIdade.getText(), txtRaca.getText(), txtPelagem.getText(), txtSexo.getText());
            if (sucesso) {
                DialogUtil.showInformation("Cadastro atualizado com sucesso!");
                carregarGatos();
                btnLimparAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Erro ao alterar no banco: " + e.getMessage());
        }
    }

    @FXML
    private void btnExcluirAction() {
        try {
            GatosDTO selecionado = tblGatos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                DialogUtil.showWarning("Selecione um gato na tabela para excluir!");
                return;
            }

            if (DialogUtil.showConfirmation("Deseja excluir o gato ID " + selecionado.getId() + "?")) {
                gatoService.excluirGato(selecionado);
                DialogUtil.showInformation("Gato removido com sucesso!");
                carregarGatos();
                btnLimparAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Erro ao excluir do banco: " + e.getMessage());
        }
    }

    @FXML
    private void btnLimparAction() {
        txtID.clear();
        txtIdade.clear();
        txtRaca.clear();
        txtPelagem.clear();
        txtSexo.clear();
        lblMensagem.setText("");

        atualizarEstadoBotoes(false);
        tblGatos.getSelectionModel().clearSelection();
        txtIdade.requestFocus();
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        GatosDTO dto = tblGatos.getSelectionModel().getSelectedItem();
        if (dto != null) {
            txtID.setText(String.valueOf(dto.getId()));
            txtIdade.setText(String.valueOf(dto.getIdade()));
            txtRaca.setText(dto.getRaca());
            txtPelagem.setText(dto.getPelagem());
            txtSexo.setText(dto.getSexo());
            lblMensagem.setText("");
            atualizarEstadoBotoes(true);
        }
    }

    private void carregarGatos() {
        try {
            tblGatos.setItems(FXCollections.observableArrayList(gatoService.listarGatos()));
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Erro ao carregar os dados do banco.");
        }
    }

    private void atualizarEstadoBotoes(boolean selecionado) {
        btnAlterar.setDisable(!selecionado);
        btnExcluir.setDisable(!selecionado);
        btnSalvar.setDisable(selecionado);
    }
}