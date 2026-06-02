package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.util.ArrayList;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;

    @FXML private TextField txtID;
    @FXML private TextField txtIdade;
    @FXML private TextField txtPelagem;
    @FXML private TextField txtRaca;
    @FXML private TextField txtSexo;

    @FXML private TableView<GatosDTO> tblGatos;
    @FXML private TableColumn<GatosDTO, Integer> colID;
    @FXML private TableColumn<GatosDTO, Integer> colIdade;
    @FXML private TableColumn<GatosDTO, String> colPelagem;
    @FXML private TableColumn<GatosDTO, String> colRaca;
    @FXML private TableColumn<GatosDTO, String> colSexo;

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String idade = txtIdade.getText();
        String raca = txtRaca.getText();
        String pelagem = txtPelagem.getText();
        String sexo = txtSexo.getText();

        GatosDTO objgatosdto = new GatosDTO();
        objgatosdto.setIdade(Integer.parseInt(idade));
        objgatosdto.setRaca(raca);
        objgatosdto.setPelagem(pelagem);
        objgatosdto.setSexo(sexo);

        GatosDAO objgatosdao = new GatosDAO();
        objgatosdao.cadastrarGatos(objgatosdto);

        carregarGatos();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();

        if (gatoSelecionado != null) {
            gatoSelecionado.setIdade(Integer.parseInt(txtIdade.getText()));
            gatoSelecionado.setRaca(txtRaca.getText());
            gatoSelecionado.setPelagem(txtPelagem.getText());
            gatoSelecionado.setSexo(txtSexo.getText());

            GatosDAO objgatosdao = new GatosDAO();
            objgatosdao.atualizarGatos(gatoSelecionado);

            carregarGatos();
        }
    }

    @FXML
    private void btnExcluirAction (ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();

        if (gatoSelecionado != null) {
            GatosDAO objgatosdao = new GatosDAO();
            objgatosdao.deletarGatos(gatoSelecionado.getId());

            carregarGatos();
        }
    }

    @FXML
    private void carregarGatos() {
        GatosDAO objgatosdao = new GatosDAO();
        ArrayList<GatosDTO> listaGatos = objgatosdao.selecionarGatos();
        tblGatos.setItems(FXCollections.observableArrayList(listaGatos));
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtID.clear();
        txtIdade.clear();
        txtRaca.clear();
        txtPelagem.clear();
        txtSexo.clear();
    }

    @FXML
    private void initialize()
    {
        colID.setCellValueFactory(new PropertyValueFactory<>( "id"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>( "Idade"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>( "Raca"));
        colPelagem.setCellValueFactory(new PropertyValueFactory<>( "Pelagem"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>( "Sexo"));
        carregarGatos();
    }

    @FXML
    private void carregarCampos(){
        GatosDTO gatosDTO = tblGatos.getSelectionModel().getSelectedItem();

        if(gatosDTO!=null){
            txtID.setText(String.valueOf(gatosDTO.getId()));
            txtIdade.setText(String.valueOf(gatosDTO.getIdade()));
            txtPelagem.setText(gatosDTO.getPelagem());
            txtSexo.setText(gatosDTO.getSexo());
        }
    }
}