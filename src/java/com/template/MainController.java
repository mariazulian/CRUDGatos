package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MainController
{
    @FXML private Button btnSalvar; //conecta com os elementos visuais
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;

    @FXML private TextField txtID; //declara os campos de texto que o usuario irá digitar
    @FXML private TextField txtIdade;
    @FXML private TextField txtPelagem;
    @FXML private TextField txtRaca;
    @FXML private TextField txtSexo;

    @FXML private TableView<GatosDTO> tblGatos; //declara a tabela que usará o modelo GatosDTO
    @FXML private TableColumn<GatosDTO, Integer> colID;
    @FXML private TableColumn<GatosDTO, Integer> colIdade;
    @FXML private TableColumn<GatosDTO, String> colPelagem;
    @FXML private TableColumn<GatosDTO, String> colRaca;
    @FXML private TableColumn<GatosDTO, String> colSexo;

    @FXML
    private void btnSalvarAction(ActionEvent event) { //executado quando o usuario clica no botão Salvar
        String idade = txtIdade.getText(); //captura os textos e os guarda em variáveis
        String raca = txtRaca.getText();
        String pelagem = txtPelagem.getText();
        String sexo = txtSexo.getText();

        GatosDTO objGatosDTO = new GatosDTO(); //novo objeto de transferência de dados e preenche as propriedades dele
        objGatosDTO.setIdade(Integer.parseInt(idade));
        objGatosDTO.setRaca(raca);
        objGatosDTO.setPelagem(pelagem);
        objGatosDTO.setSexo(sexo);

        GatosDAO objgatosdao = new GatosDAO(); //envia o objeto criado para ser salvo
        objgatosdao.cadastrarGatos(objGatosDTO);

        carregarGatos(); //chama o metodo para atualizar a tabela na tela
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem(); //verifica qual gato está selecionado

        if (gatoSelecionado != null) { //só continua se realmente tiver selecionado uma linha
            gatoSelecionado.setIdade(Integer.parseInt(txtIdade.getText())); //atualiza as propriedades do gato
            gatoSelecionado.setRaca(txtRaca.getText());
            gatoSelecionado.setPelagem(txtPelagem.getText());
            gatoSelecionado.setSexo(txtSexo.getText());

            GatosDAO objGatosDAO = new GatosDAO(); //envia o objeto atualizado para o banco de dados realizar o UPDATE
            objGatosDAO.atualizarGatos(gatoSelecionado);

            carregarGatos();
        }
    }

    @FXML
    private void btnExcluirAction (ActionEvent event) {
        GatosDTO gatoSelecionado = tblGatos.getSelectionModel().getSelectedItem();

        if (gatoSelecionado != null) {
            GatosDAO objGatosDAO = new GatosDAO();
            objGatosDAO.deletarGatos(gatoSelecionado.getId()); //envia o ID do gato selecionado para o metodo de exclusão

            carregarGatos();
        }
    }

    @FXML
    private void carregarGatos() { //busca os dados do banco e mostra na tela
        GatosDAO objGatosDAO = new GatosDAO();
        ArrayList<GatosDTO> listaGatos = objGatosDAO.selecionarGatos();
        tblGatos.setItems(FXCollections.observableArrayList(listaGatos)); //converte a lista comum em uma lista observável
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
        colID.setCellValueFactory(new PropertyValueFactory<>( "id")); //vincula coluna da tabela a um atributo específico da classe GatosDTO
        colIdade.setCellValueFactory(new PropertyValueFactory<>( "Idade"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>( "Raca"));
        colPelagem.setCellValueFactory(new PropertyValueFactory<>( "Pelagem"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>( "Sexo"));
        carregarGatos();
    }

    @FXML
    private void carregarCampos(MouseEvent event){  // joga os dados dele de volta para os campos de texto
        GatosDTO gatosDTO = tblGatos.getSelectionModel().getSelectedItem();

        if(gatosDTO!=null){
            txtID.setText(String.valueOf(gatosDTO.getId())); //preenche os campos de texto com as informações do gato
            txtIdade.setText(String.valueOf(gatosDTO.getIdade()));
            txtRaca.setText(gatosDTO.getRaca());
            txtPelagem.setText(gatosDTO.getPelagem());
            txtSexo.setText(gatosDTO.getSexo());
        }
    }
}