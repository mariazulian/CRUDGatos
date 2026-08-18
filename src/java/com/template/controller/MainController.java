package com.template.controller;

import com.template.model.dto.GatosDTO;
import com.template.service.GatosService;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
            gatoService.salvarGato(txtIdade.getText(), txtRaca.getText(), txtPelagem.getText(), txtSexo.getText());
            exibirMensagem("Gato cadastrado com sucesso!", Color.GREEN);
            carregarGatos();
            btnLimparAction();
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage(), Color.RED);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console do IntelliJ
            exibirMensagem("Erro ao salvar no banco: " + e.getMessage(), Color.RED);
        }
    }

    @FXML
    private void btnAlterarAction() {
        try {
            GatosDTO selecionado = tblGatos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                exibirMensagem("Selecione um gato na tabela para alterar!", Color.RED);
                return;
            }
            gatoService.atualizarGato(selecionado, txtIdade.getText(), txtRaca.getText(), txtPelagem.getText(), txtSexo.getText());
            exibirMensagem("Cadastro atualizado com sucesso!", Color.GREEN);
            carregarGatos();
            btnLimparAction();
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage(), Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem("Erro ao alterar no banco: " + e.getMessage(), Color.RED);
        }
    }

    @FXML
    private void btnExcluirAction() {
        try {
            GatosDTO selecionado = tblGatos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                exibirMensagem("Selecione um gato na tabela para excluir!", Color.RED);
                return;
            }

            if (DialogUtil.showConfirmation("Deseja excluir o gato ID " + selecionado.getId() + "?")) {
                gatoService.excluirGato(selecionado);
                exibirMensagem("Gato removido com sucesso!", Color.GREEN);
                carregarGatos();
                btnLimparAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem("Erro ao excluir do banco: " + e.getMessage(), Color.RED);
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
            exibirMensagem("Erro ao carregar os dados do banco.", Color.RED);
        }
    }

    private void atualizarEstadoBotoes(boolean selecionado) {
        btnAlterar.setDisable(!selecionado);
        btnExcluir.setDisable(!selecionado);
        btnSalvar.setDisable(selecionado);
    }

    private void exibirMensagem(String mensagem, Color cor) {
        lblMensagem.setText(mensagem);
        lblMensagem.setTextFill(cor);
    }
}