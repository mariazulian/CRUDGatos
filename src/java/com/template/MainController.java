package com.template;

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
        if (txtIdade.getText().isEmpty() || txtRaca.getText().isEmpty() || txtPelagem.getText().isEmpty() || txtSexo.getText().isEmpty()) {
            lblMensagem.setText("Por favor, preencha todos os campos!");
            lblMensagem.setTextFill(Color.RED);
            return;
        }

        lblMensagem.setText("");

        GatosDTO objGatosDTO = new GatosDTO();
        objGatosDTO.setIdade(Integer.parseInt(txtIdade.getText()));
        objGatosDTO.setRaca(txtRaca.getText());
        objGatosDTO.setPelagem(txtPelagem.getText());
        objGatosDTO.setSexo(txtSexo.getText());

        GatosDAO objgatosdao = new GatosDAO();
        objgatosdao.cadastrarGatos(objGatosDTO);

        carregarGatos();
        btnLimparAction(null);
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        if (txtIdade.getText().isEmpty() || txtRaca.getText().isEmpty() || txtPelagem.getText().isEmpty() || txtSexo.getText().isEmpty()) {
            lblMensagem.setText("Por favor, preencha todos os campos!");
            lblMensagem.setTextFill(Color.RED);
            return;
        }

        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();
        if (gatoSelecionado != null) {
            lblMensagem.setText("");
            gatoSelecionado.setIdade(Integer.parseInt(txtIdade.getText()));
            gatoSelecionado.setRaca(txtRaca.getText());
            gatoSelecionado.setPelagem(txtPelagem.getText());
            gatoSelecionado.setSexo(txtSexo.getText());

            GatosDAO objGatosDAO = new GatosDAO();
            objGatosDAO.atualizarGatos(gatoSelecionado);

            carregarGatos();
            btnLimparAction(null);
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();
        if (gatoSelecionado != null) {
            GatosDAO objGatosDAO = new GatosDAO();
            objGatosDAO.deletarGatos(gatoSelecionado.getId());

            lblMensagem.setText("");
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
}