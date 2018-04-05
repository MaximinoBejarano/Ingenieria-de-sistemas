/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class ScenesManager {

    private static ScenesManager INSTANCE = null;
    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    private ScenesManager() {
        stage = new Stage();
    }

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ScenesManager.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ScenesManager();
                }
            }
        }
    }

    public static ScenesManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void LoadStage(Stage pstage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Login.fxml"));
        stage = pstage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(1100);
        stage.setMinHeight(650);
        //stage.getIcons().add(new Image("src/pictures/logo.png"));
        stage.setTitle("Sistema de Facturación Ferretería las Vegas");
        stage.show();
        PermisosManager.getInstance().setScene(scene);
    }

    public void LoadSceneLogin() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Login.fxml"));
        scene.setRoot(root);
    }

    public void LoadSceneMenu() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
        scene.setRoot(root);
        PermisosManager.getInstance().Menu();
    }

    public void LoadSceneEmpleados() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Empleados.fxml"));
        scene.setRoot(root);
        PermisosManager.getInstance().Empleados();
    }

    public void LoadSceneClientes() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Clientes.fxml"));
        scene.setRoot(root);
        PermisosManager.getInstance().Clientes();
    }

    public void LoadSceneInventario() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Inventario.fxml"));
        scene.setRoot(root);
        PermisosManager.getInstance().Invetario();
    }

    public void LoadSceneProveedores() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Proveedores.fxml"));
        scene.setRoot(root);
    }

    public void LoadSceneAnulacion() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Anulación.fxml"));
        scene.setRoot(root);
    }

    public void LoadSceneAbonos() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Abonos.fxml"));
        scene.setRoot(root);
    }

    public void LoadSceneVales() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Vales.fxml"));
        scene.setRoot(root);
    }

    public void LoadSceneFacturacion() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Facturación.fxml"));
        scene.setRoot(root);
    }
}
