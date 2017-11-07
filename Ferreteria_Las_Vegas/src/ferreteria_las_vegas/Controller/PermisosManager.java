/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.entities.Permiso;
import ferreteria_las_vegas.model.entities.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author Usuario
 */
public class PermisosManager {

    private static PermisosManager INSTANCE = null;
    private static Scene scene;
    private static Usuario usuario;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (PermisosManager.class) {

                if (INSTANCE == null) {
                    INSTANCE = new PermisosManager();
                }
            }
        }
    }

    public static PermisosManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setScene(Scene pscene) {
        scene = pscene;
    }

    public void setUsario(Usuario pusuario) {
        usuario = pusuario;
    }

    void Menu() {
        Button btnFacturacion = (Button) scene.lookup("#btnFacturacion");
        Button btnAbonos = (Button) scene.lookup("#btnAbonos");
        Button btnNotaCredito = (Button) scene.lookup("#btnNotaCredito");
        Button btnInventario = (Button) scene.lookup("#btnInventario");
        Button btnClientes = (Button) scene.lookup("#btnClientes");
        Button btnEmpleados = (Button) scene.lookup("#btnEmpleados");
        Button btnProveedores = (Button) scene.lookup("#btnProveedores");
        Button btnAnulacion = (Button) scene.lookup("#btnAnulacion");
        Button btnEstadisticas = (Button) scene.lookup("#btnEstadisticas");
        Button btnConfiguraciones = (Button) scene.lookup("#btnConfiguraciones");
        
        btnFacturacion.setDisable(true);
        btnAbonos.setDisable(true);
        btnNotaCredito.setDisable(true);
        btnInventario.setDisable(true);
        btnClientes.setDisable(true);
        btnEmpleados.setDisable(true);
        btnProveedores.setDisable(true);
        btnAnulacion.setDisable(true);
        btnEstadisticas.setDisable(true);
        btnConfiguraciones.setDisable(true);                
        
        for (Permiso permiso : usuario.getPermisoList()) {                                 
            
            if(permiso.getPerNombre().equalsIgnoreCase("VER_FACTURACION")){
                btnFacturacion.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_ABONOS")){
                btnAbonos.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_NOTACREDITO")){
                btnNotaCredito.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_INVENTARIO")){
                btnInventario.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_CLIENTES")){
                btnClientes.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_EMPLEADOS")){
                btnEmpleados.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_PROVEEDORES")){
                btnProveedores.setDisable(false);
            }
            else if(permiso.getPerNombre().equalsIgnoreCase("VER_ANULACIONES")){
                btnAnulacion.setDisable(false);
            }
        }
    }
}
