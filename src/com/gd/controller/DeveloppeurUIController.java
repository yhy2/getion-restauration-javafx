package com.gd.controller;

import java.awt.Image;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.gd.db.UMSDBException;
import com.gd.model.Chef;
import com.gd.model.Commande;
import com.gd.model.Developpeur;
import com.gd.model.Produit;
import com.gd.run.GDApplication;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DeveloppeurUIController {
	@FXML
	private TableView<Commande> commandeTable;
	@FXML
	private TableColumn<Commande, String> produitCommandeColumn;
	@FXML
	private TableColumn<Commande, String> nomClientColumn;
	@FXML
	private TableColumn<Commande, Integer> quantiteColumn;
	@FXML
	private TableColumn<Commande, String> dateCommandeColumn;
	@FXML
	private TableColumn<Commande, Double> montantTotalColumn;
	@FXML
	private TableColumn<Commande, String> etatPaiementColumn;
	@FXML
	private TableColumn<Commande, String>ProduitColu;
	@FXML
	private TableColumn<Commande, String> quantitesProduitsColumn;

	@FXML
	private TableView<Produit> ProduitTable;
	@FXML
	private TableColumn<Produit, Image> ImageColumn;
	@FXML
	private TableColumn<Produit, String> EtatColumn;
	@FXML
	private TableColumn<Produit, String> DescriptionColumn;
	@FXML
	private TableColumn<Produit, Date> DateColumn;
	@FXML
	private TableColumn<Produit, Long> AssigneColumn;
	@FXML
	private TableColumn<Produit, String> mise_ajourColumn;
	@FXML
	private TableColumn<Produit, String> AppColumn;
	@FXML
	private TableColumn<Produit, Integer> IdColumn;
	@FXML
	private TableColumn<Produit, Double> PrixColumn;
	
	@FXML
	private TableColumn<Produit, String>EtatProduiColumn;
	
	

	@FXML
	private MenuButton MenuButtonField;
	
	@FXML
	private Button btnproduit;
	@FXML
	private Button btncommande;

	@FXML
	private TextField rechercherField;


	private Developpeur user;
	@SuppressWarnings("unused")
	private Stage dialogStage;
	
	//private ObservableList<Produit> incidentsDev = FXCollections.observableArrayList();


	public Developpeur getUser() {
		return user;
	}

	public void setUser(Developpeur user) {
		this.user = user;
		MenuButtonField.setText(user.getLogin());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	@FXML
	private void initialize() {
		 
		ProduitColu.setCellValueFactory(cellData -> {
		        List<Produit> produits = cellData.getValue().getProduits(); // Utilisez la m�thode getProduits() pour obtenir la liste de produits pour une commande
		        StringBuilder produitNames = new StringBuilder();

		        for (Produit produit : produits) {
		            produitNames.append(produit.getIntitule()).append(", ");
		        }

		        return new SimpleStringProperty(produitNames.toString());
		    });

		    // Le reste de votre initialisation...
		
		
		quantitesProduitsColumn.setCellValueFactory(cellData -> {
	        List<Integer> quantitesProduits = cellData.getValue().getQuantitesProduits();
	        StringBuilder quantites = new StringBuilder();

	        for (int quantite : quantitesProduits) {
	            quantites.append(quantite).append(", ");
	        }

	        return new SimpleStringProperty(quantites.toString());
	    });
	    // Initialise the Commande table
		//quantiteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
	    dateCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCommande().toString()));
	    etatPaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPayee()));
	    //nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()));
	    nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()));
	    montantTotalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMontantTotal()).asObject());

	    // Assuming commandeTable is properly associated with the corresponding TableView in FXML
	    commandeTable.setItems(GDApplication.getInstance().getDataSource().getCommandes());
	    
	   // commandeTable.getColumns().addAll(produitCommandeColumn, quantiteColumn, quantitesProduitsColumn, dateCommandeColumn, montantTotalColumn, etatPaiementColumn);


	    // Initialise the Produit table
	    
	    addChangeListener();

	    rechercher();
	}

	

	
//	@FXML
//	private void initialize() {
//	    // Initialise la table des commandes
//	    idCommandeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//	    produitCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getIntitule()));
//	    quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
//	    dateCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCommande().toString()));
//	    montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
//	    etatPaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPayee() ? "Pay�e" : "Non pay�e"));
//	    
//	    // Assurez-vous que la source de donn�es renvoie une liste observ�e d'objets Commande
//	    ObservableList<Commande> commandes = GDApplication.getInstance().getDataSource().getCommandes();
//	    
//	    commandeTable.setItems(commandes);
//	}

	
	
	@SuppressWarnings("static-access")
	@FXML
	private void handleNouveaucommande() {
		Commande commande = new Commande();
		commande.setDeveloppeur(this.getUser());

		boolean validerClicked = GDApplication.getInstance().showCommandeEditUI(commande);
		if (validerClicked) {
			Long developpeur =user.getId();
			Long chef = (long) 0;			
			try {
				GDApplication.getInstance().getDataSource().AddCommande(commande);
				GDApplication.getInstance().getUtilitaire().displayNotification("Commande cr�� avec succ�s !");
				int id = idCommande();
			} catch (UMSDBException e) {
				// TODO Auto-generated catch block
				GDApplication.getInstance().getUtilitaire().displayErrorMessage("Error : " + e.getMessage());
			}
		}
	}
	
	

	
	/**
	* Surveille les changements sur la table et affiche les informations dans le formulaire
	*/
	
	private void addChangeListener() {
	//	IncidentTable.getSelectionModel().selectedItemProperty().addListener(

		      //  (observable, oldValue, newValue) -> displayNoteMsDetails(newValue));

		}
	




	@FXML
	private void handleNouvelleCommande() {
	    try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(GDApplication.class.getResource("../ui/SaisieCommandeUI.fxml"));
	        Parent commandeUI = loader.load();
	        
//	        FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(GDApplication.class.getResource("../ui/CreerCommandeUI.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
	        
	        Stage stage = new Stage();
	        stage.setTitle("Ajouter Commande");
	        stage.setScene(new Scene(commandeUI));
	        stage.setOnHiding(event -> {
	            try {
	                // Rechargez les commandes depuis la base de donn�es apr�s la fermeture de la fen�tre de saisie
	                 loadCommandes();
	            } catch (Exception e) {
	                e.printStackTrace();
	                // G�rez l'erreur ici selon vos besoins
	            }
	        });

	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace(); // Ajoutez un point-virgule ici
	        // G�rez l'erreur ici selon vos besoins
	    }
	}


	private void loadCommandes() {
        // Charger les commandes depuis la source de donn�es
        ObservableList<Commande> commandes = GDApplication.getInstance().getDataSource().getCommandes();
        commandeTable.setItems(commandes);
    }
	private void rechercher() {

		FilteredList<Commande> filteredData = new FilteredList<>(
				GDApplication.getInstance().getDataSource().getCommandes(), b -> true);

		rechercherField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(incident -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (incident.getProduit().getIntitule().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				if (Long.valueOf(user.getId()).toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false;

			});
		});

		SortedList<Commande> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(commandeTable.comparatorProperty());
		commandeTable.setItems(sortedData);

	}
	
	@FXML
	private void ChangePassword() {

		GDApplication.getInstance().initChangePassword();

	}


	@FXML
    private void openCommandeUI(ActionEvent event) {
		Developpeur repp = new Developpeur();
		GDApplication.getInstance().initDevelopeurLayout(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();
	}
	
    @FXML
    private void openDeveloppeur2UI(ActionEvent event) {
    	Chef repp = new Chef();
		GDApplication.getInstance().initRapporteurLayout1(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();
//        try {
//        	
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("../ui/DeveloppeurProduitUI.fxml"));
//            BorderPane page = (BorderPane) loader.load();
//            // Cr�er une nouvelle sc�ne et afficher la nouvelle interface utilisateur
//            Scene scene = new Scene(page);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//
//            // Vous pouvez �galement fermer la sc�ne actuelle si n�cessaire
//            Stage currentStage = (Stage) btnproduit.getScene().getWindow();
//            currentStage.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    public int idCommande() {
		 ObservableList<Commande> commandes = FXCollections.observableArrayList();
		 Commande commande = null;
		 commandes= GDApplication.getInstance().getDataSource().getCommandes();
		 commande = commandes.get(commandes.size()-1);
		int id_commande = commande.getId();	
		return id_commande;
	}
	

}
