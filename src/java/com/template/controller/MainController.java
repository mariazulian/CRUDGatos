package com.template.controller;

import com.template.model.dao.GatosDAO;
import com.template.model.dto.GatosDTO;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    @FXML private TextField txtID;
    @FXML private TextField txtIdade;
    @FXML private TextField txtPelagem;
    @FXML private TextField txtRaca;
    @FXML private TextField txtSexo;

    @FXML private Label lblMensagem;

    @FXML private TableView<GatosDTO> tblGatos;
    @FXML private TableColumn<GatosDTO, Integer> colID;
    @FXML private TableColumn<GatosDTO, Integer> colIdade;
    @FXML private TableColumn<GatosDTO, String> colRaca;
    @FXML private TableColumn<GatosDTO, String> colPelagem;
    @FXML private TableColumn<GatosDTO, String> colSexo;

    @FXML
    private void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colPelagem.setCellValueFactory(new PropertyValueFactory<>("pelagem"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        txtID.setEditable(false);
        lblMensagem.setText("");

        atualizarEstadoBotoes(false);
        carregarGatos();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (txtIdade.getText().trim().isEmpty() || txtRaca.getText().trim().isEmpty() ||
                txtPelagem.getText().trim().isEmpty() || txtSexo.getText().trim().isEmpty()) {
            exibirMensagemErro("Por favor, preencha todos os campos!");
            return;
        }

        try {
            int idade = Integer.parseInt(txtIdade.getText().trim());

            GatosDTO objGatosDTO = new GatosDTO();
            objGatosDTO.setIdade(idade);
            objGatosDTO.setRaca(txtRaca.getText().trim());
            objGatosDTO.setPelagem(txtPelagem.getText().trim());
            objGatosDTO.setSexo(txtSexo.getText().trim());

            GatosDAO objgatosdao = new GatosDAO();
            objgatosdao.cadastrarGatos(objGatosDTO);

            exibirMensagemSucesso("Gato cadastrado com sucesso!");
            carregarGatos();
            btnLimparAction(null);
        } catch (NumberFormatException e) {
            exibirMensagemErro("A idade deve ser um número inteiro válido!");
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();
        if (gatoSelecionado == null) {
            exibirMensagemErro("Selecione um gato na tabela para alterar!");
            return;
        }

        if (txtIdade.getText().trim().isEmpty() || txtRaca.getText().trim().isEmpty() ||
                txtPelagem.getText().trim().isEmpty() || txtSexo.getText().trim().isEmpty()) {
            exibirMensagemErro("Preencha todos os campos para atualizar!");
            return;
        }

        try {
            int idade = Integer.parseInt(txtIdade.getText().trim());

            gatoSelecionado.setIdade(idade);
            gatoSelecionado.setRaca(txtRaca.getText().trim());
            gatoSelecionado.setPelagem(txtPelagem.getText().trim());
            gatoSelecionado.setSexo(txtSexo.getText().trim());

            GatosDAO objGatosDAO = new GatosDAO();
            objGatosDAO.atualizarGatos(gatoSelecionado);

            exibirMensagemSucesso("Cadastro atualizado com sucesso!");
            carregarGatos();
            btnLimparAction(null);
        } catch (NumberFormatException e) {
            exibirMensagemErro("A idade deve ser um número inteiro válido!");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();
        if (gatoSelecionado == null) {
            exibirMensagemErro("Selecione um gato na tabela para excluir!");
            return;
        }

        // DIALOGUTIL  Ação critica exige confirmação do usuário!
        boolean confirmou = DialogUtil.showConfirmation("Tem certeza de que deseja excluir o gato ID " + gatoSelecionado.getId() + "?");

        if (confirmou) {
            GatosDAO objGatosDAO = new GatosDAO();
            objGatosDAO.deletarGatos(gatoSelecionado.getId());

            exibirMensagemSucesso("Gato removido com sucesso!");
            carregarGatos();
            btnLimparAction(null);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
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
        GatosDTO gatosDTO = tblGatos.getSelectionModel().getSelectedItem();
        if (gatosDTO != null) {
            txtID.setText(String.valueOf(gatosDTO.getId()));
            txtIdade.setText(String.valueOf(gatosDTO.getIdade()));
            txtRaca.setText(gatosDTO.getRaca());
            txtPelagem.setText(gatosDTO.getPelagem());
            txtSexo.setText(gatosDTO.getSexo());
            lblMensagem.setText("");
            atualizarEstadoBotoes(true);
        }
    }

    private void carregarGatos() {
        GatosDAO objGatosDAO = new GatosDAO();
        ArrayList<GatosDTO> listaGatos = objGatosDAO.selecionarGatos();
        tblGatos.setItems(FXCollections.observableArrayList(listaGatos));
    }

    private void atualizarEstadoBotoes(boolean itemSelecionado) {
        btnAlterar.setDisable(!itemSelecionado);
        btnExcluir.setDisable(!itemSelecionado);
        btnSalvar.setDisable(itemSelecionado);
    }

    private void exibirMensagemErro(String mensagem) {
        lblMensagem.setText(mensagem);
        lblMensagem.setTextFill(Color.RED);
    }

    private void exibirMensagemSucesso(String mensagem) {
        lblMensagem.setText(mensagem);
        lblMensagem.setTextFill(Color.GREEN);
    }
}