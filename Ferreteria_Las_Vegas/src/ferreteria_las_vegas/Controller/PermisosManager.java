/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.entities.Permiso;
import ferreteria_las_vegas.model.entities.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

            if (permiso.getPerNombre().equalsIgnoreCase("VER_FACTURACION")) {
                btnFacturacion.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_ABONOS")) {
                btnAbonos.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_NOTACREDITO")) {
                btnNotaCredito.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_INVENTARIO")) {
                btnInventario.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_CLIENTES")) {
                btnClientes.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_EMPLEADOS")) {
                btnEmpleados.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_PROVEEDORES")) {
                btnProveedores.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("VER_ANULACIONES")) {
                btnAnulacion.setDisable(false);
            }
        }
    }

    void Clientes() {
        Button btnAgregarCliente = (Button) scene.lookup("#btnAgregarCliente");
        Button btnBuscarCliente = (Button) scene.lookup("#btnBuscarCliente");
        Button btnEditarClientes = (Button) scene.lookup("#btnEditarClientes");
        Button btnEliminarCliente = (Button) scene.lookup("#btnEliminarCliente");

        btnAgregarCliente.setDisable(true);
        btnBuscarCliente.setDisable(true);
        btnEditarClientes.setDisable(true);
        btnEliminarCliente.setDisable(true);

        for (Permiso permiso : usuario.getPermisoList()) {

            if (permiso.getPerNombre().equalsIgnoreCase("AGR_CLIENTE")) {
                btnAgregarCliente.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_CLIENTE")) {
                btnBuscarCliente.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_CLIENTE")) {
                btnEditarClientes.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_CLIENTE")) {
                btnEliminarCliente.setDisable(false);
            }
        }
    }

    void Empleados() {
        Button btnAgregar = (Button) scene.lookup("#btnAgregar");
        Button btnBuscar = (Button) scene.lookup("#btnBuscar");                            
        Button btnEditar = (Button) scene.lookup("#btnEditar");            
        Button btnEliminar = (Button) scene.lookup("#btnEliminar");        
        TabPane tabPane = (TabPane) scene.lookup("#tabPane");
        Tab tab = tabPane.getTabs().get(1);
        
        btnAgregar.setDisable(true);
        btnBuscar.setDisable(true);        
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        tab.setDisable(true);
        
        for (Permiso permiso : usuario.getPermisoList()) {
            if (permiso.getPerNombre().equalsIgnoreCase("AGR_EMPLEADO")) {
                btnAgregar.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_EMPLEADO")) {
                btnBuscar.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_EMPLEADO")) {
                btnEditar.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_EMPLEADO")) {
                btnEliminar.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("PER_EMPLEADO")) {
                tab.setDisable(false);
            }
        }
    }
    
    void Invetario() {
        Button btnNuevoProducto = (Button) scene.lookup("#btnNuevoProducto");        
        Button btnBuscarProducto = (Button) scene.lookup("#btnBuscarProducto");        
        Button btnEliminarProducto = (Button) scene.lookup("#btnEliminarProducto");                  
        
        Button btnAgregarFactura = (Button) scene.lookup("#btnAgregarFactura");
        Button btnBuscarFactura = (Button) scene.lookup("#btnBuscarFactura");
        Button btnEditarFactura = (Button) scene.lookup("#btnEditarFactura");    
        Button btnEliminarFactura = (Button) scene.lookup("#btnEliminarFactura");                        
        
        btnNuevoProducto.setDisable(true);
        btnBuscarProducto.setDisable(true);        
        btnEliminarProducto.setDisable(true);
        
        btnAgregarFactura.setDisable(true);
        btnBuscarFactura.setDisable(true);
        btnEditarFactura.setDisable(true);
        btnEliminarFactura.setDisable(true);                
        
        for (Permiso permiso : usuario.getPermisoList()) {
            if (permiso.getPerNombre().equalsIgnoreCase("AGR_PRODUCTO")) {
                btnNuevoProducto.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_PRODUCTO")) {
                btnBuscarProducto.setDisable(false);            
            } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_PRODUCTO")) {
                btnEliminarProducto.setDisable(false);                                
            } else if (permiso.getPerNombre().equalsIgnoreCase("AGR_INVENTARIO")) {
                btnAgregarFactura.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_INVENTARIO")) {
                btnBuscarFactura.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_INVENTARIO")) {
                btnEditarFactura.setDisable(false);
            } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_INVENTARIO")) {
                btnEliminarFactura.setDisable(false);
            }            
        }
    }    
}