/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.api.bd2;


//import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import models.Hora;
import daos.horaDAO;
import java.sql.Timestamp;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;






import models.*;



public class LancamentoColaboradorController {
    
    @FXML
    private VBox node;
    @FXML
    private ImageView iconeUsuario;
    @FXML
    private Label label_usuario;
    @FXML
    private Label FUNCAO;
    @FXML
    private Label CR;
    @FXML
    private Button mudarCR;
    @FXML
    private Button botaoSair;
    @FXML
    private TableView<Hora> tabelaLancamento;
    @FXML
    private TableColumn<Hora, Integer> tabelaId;
    @FXML
    private TableColumn<Hora, String> tabelaNome;
    @FXML
    private TableColumn<Hora, String> tabelaTipo;
    @FXML
    private TableColumn<Hora, String> tabelaStatus;
    @FXML
    private TableColumn<Hora, Timestamp > tabelaDHInicio;
    @FXML
    private TableColumn<Hora, Timestamp > tabelaDHFim;
    @FXML
    private TableColumn<Hora, String> tabelaCR;
    @FXML
    private TableColumn<Hora, String> tabelaCliente;
    @FXML
    private TableColumn<Hora, String> tabelaProjeto;
    @FXML
    private TableColumn<Hora, String> tabelaJustificativa;
    @FXML
    private TableColumn<Hora, String  > tabelaResp;
    @FXML
    private DatePicker dataInicio;
    @FXML
    private Spinner<Integer> horaInicio;
    @FXML
    private Spinner<Integer> minutoInicio;
 
    private ComboBox<String> selecaoJustificativa;
    @FXML
    private ComboBox<String> tipo_funcao;
    @FXML
    private TextField stringProjeto;
    @FXML
    private ComboBox<String> selecaoCliente;
    @FXML
    private DatePicker dataFim;
    @FXML
    private Spinner<Integer> horaFim;
    @FXML
    private Spinner<Integer> minutoFim;
    @FXML
    private Button botaoAdicionar;
    @FXML
    private Button botaoLimpar;

    private List<String> obs = new ArrayList<>();
    private ObservableList<String> opcoes = FXCollections.observableArrayList();
    
    private List<String> cli = new ArrayList<>();
    private ObservableList<String> opCli = FXCollections.observableArrayList();
    
    private List<Hora> lishoras = new ArrayList<>();
    private ObservableList<Hora> observablelisthoras = FXCollections.observableArrayList();
    
    public void initialize() {
        label_usuario.setText("*nome do usuário*");
        minutoInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        horaInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24));
        minutoFim.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        horaFim.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24));
        carregaTabela();
    }

    @FXML
    public void BotaoAdicionar() {
        if(dataInicio.getValue()==null||horaInicio.getValue()==null||minutoInicio.getValue()==null||dataFim.getValue()==null||horaFim.getValue()==null||minutoFim.getValue()==null){
        System.out.println("Preencha todos os campos - tela de lançamento");}
        else{
        try{
            LocalDate data_inicio = dataInicio.getValue();
            int hora_inicio = horaInicio.getValue();
            int min_inicio = minutoInicio.getValue();
            String data_hora_inicio = data_inicio.getYear() + "-" + data_inicio.getMonthValue() + "-" + data_inicio.getDayOfMonth() + " " + hora_inicio + ":" + min_inicio + ":00";
            Timestamp timestamp_inicio = Timestamp.valueOf(data_hora_inicio);
            
            LocalDate data_fim = dataFim.getValue();            
            int hora_fim = horaFim.getValue();
            int min_fim = minutoFim.getValue();
            String data_hora_fim = data_fim.getYear() + "-" + data_fim.getMonthValue() + "-" + data_fim.getDayOfMonth() + " " + hora_fim + ":" + min_fim + ":00";
            Timestamp timestamp_fim = Timestamp.valueOf(data_hora_fim);

            

            Hora hora = new Hora();
            hora.setCod_cr("Cr");
            hora.setData_hora_inicio(timestamp_inicio.toString());
            hora.setData_hora_fim(timestamp_fim.toString());
            hora.setUsername_lancador("Joazinho");
            

            horaDAO daoH = new horaDAO();
            daoH.save(hora);
            
        }catch (Exception e){
            System.out.println("Houve um erro");
        }
        }
    }
    
    @FXML
    public void tipoFuncao() throws ParseException{
    obs.add("Hora "+ TipoHora.EXTRA.name().toLowerCase());
    obs.add(TipoHora.SOBREAVISO.name().toLowerCase());
    opcoes.setAll(obs);
    tipo_funcao.setItems(opcoes);
    }
    
    @FXML
    public void forneceCliente(){
        cli.add("EMBRAER");
        cli.add("ITAU");
        cli.add("SAMSUNG");
        opCli.setAll(cli);
        selecaoCliente.setItems(opCli); 
    }
    
    @FXML
    public void limpaCampos(){
        dataInicio.setValue(null);
        dataFim.setValue(null);
        selecaoCliente.getSelectionModel().clearSelection();
        tipo_funcao.getSelectionModel().clearSelection();
        minutoInicio.getValueFactory().setValue(null);
        horaInicio.getValueFactory().setValue(null);
        minutoFim.getValueFactory().setValue(null);
        horaFim.getValueFactory().setValue(null);
    }
    
    @FXML
    public void carregaTabela(){
        

        Hora hora = new Hora(); //removi daqui também
        hora.setId(2);
        lishoras.add(hora);
        observablelisthoras.setAll(lishoras);
        tabelaLancamento.setItems(observablelisthoras);
        
        tabelaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabelaNome.setCellValueFactory(new PropertyValueFactory<>("username_lancador")); 
        tabelaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo")); 
        tabelaStatus.setCellValueFactory(new PropertyValueFactory<>("status")); 
        tabelaDHInicio.setCellValueFactory(new PropertyValueFactory<>("data_hora_inicio")); 
        tabelaDHFim.setCellValueFactory(new PropertyValueFactory<>("data_hora_fim")); 
        tabelaCR.setCellValueFactory(new PropertyValueFactory<>("centro_resultado")); 
        tabelaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); 
        tabelaProjeto.setCellValueFactory(new PropertyValueFactory<>("projeto")); 
        tabelaJustificativa.setCellValueFactory(new PropertyValueFactory<>("justificativa")); 
        tabelaResp.setCellValueFactory(new PropertyValueFactory<>("status")); 


        
        
    }
}