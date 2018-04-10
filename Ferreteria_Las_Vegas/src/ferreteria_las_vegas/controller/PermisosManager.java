/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Permiso;
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.LoggerManager;
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
        try {
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
                } else if (permiso.getPerNombre().equalsIgnoreCase("VER_PARAMETROS")) {
                    btnConfiguraciones.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("VER_ESTADISTICAS")) {
                    btnEstadisticas.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Clientes() {
        try {
            Button btnAgregar = (Button) scene.lookup("#btnAgregarCliente");
            Button btnBuscar = (Button) scene.lookup("#btnBuscarCliente");
            Button btnEditar = (Button) scene.lookup("#btnEditarClientes");
            Button btnEliminar = (Button) scene.lookup("#btnEliminarCliente");

            btnAgregar.setDisable(true);
            btnBuscar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("AGR_CLIENTE")) {
                    btnAgregar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_CLIENTE")) {
                    btnBuscar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_CLIENTE")) {
                    btnEditar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_CLIENTE")) {
                    btnEliminar.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Empleados() {
        try {
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
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Invetario() {
        try {
            Button btnNuevoProducto = (Button) scene.lookup("#btnNuevoProductos");
            Button btnBuscarProducto = (Button) scene.lookup("#btnBuscarProductos");
            Button btnEliminarProducto = (Button) scene.lookup("#btnEliminarProductos");

            Button btnAgregarFactura = (Button) scene.lookup("#btnAgregarFacturas");
            Button btnBuscarFactura = (Button) scene.lookup("#btnBuscarFacturas");
            Button btnEditarFactura = (Button) scene.lookup("#btnEditarFacturas");
            Button btnEliminarFactura = (Button) scene.lookup("#btnEliminarFacturas");

            Button btnProveedores = (Button) scene.lookup("#btnProveedores");

            btnNuevoProducto.setDisable(true);
            btnBuscarProducto.setDisable(true);
            btnEliminarProducto.setDisable(true);

            btnAgregarFactura.setDisable(true);
            btnBuscarFactura.setDisable(true);
            btnEditarFactura.setDisable(true);
            btnEliminarFactura.setDisable(true);

            btnProveedores.setDisable(true);

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
                } else if (permiso.getPerNombre().equalsIgnoreCase("VER_PROVEEDORES")) {
                    btnProveedores.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Proveedores() {
        try {
            Button btnAgregar = (Button) scene.lookup("#btnAgregar");
            Button btnBuscar = (Button) scene.lookup("#btnBuscar");
            Button btnEditar = (Button) scene.lookup("#btnEditar");
            Button btnEliminar = (Button) scene.lookup("#btnEliminar");

            TabPane tabPane = (TabPane) scene.lookup("#tabPanel");

            Tab tab1 = tabPane.getTabs().get(1);
            Tab tab2 = tabPane.getTabs().get(2);

            btnAgregar.setDisable(true);
            btnBuscar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
            tab1.setDisable(true);
            tab2.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("AGR_PROVEEDOR")) {
                    btnAgregar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_PROVEEDOR")) {
                    btnBuscar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_PROVEEDOR")) {
                    btnEditar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_PROVEEDOR")) {
                    btnEliminar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("CUE_PROVEEDOR")) {
                    tab1.setDisable(false);
                    tab2.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Anulaciones() {
        try {
            Button btnAnular = (Button) scene.lookup("#btnAnularFactura");
            Button btnBuscarFactura = (Button) scene.lookup("#btnBuscarFactura");
            Button btnReimprimirFacutra = (Button) scene.lookup("#btnReimprimirFactura");

            btnAnular.setDisable(true);
            btnBuscarFactura.setDisable(true);
            btnReimprimirFacutra.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("ANU_FACTURA")) {
                    btnAnular.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_FACTURA")) {
                    btnBuscarFactura.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("REI_FACTURA")) {
                    btnReimprimirFacutra.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Abonos() {
        try {
            Button btnAgregar = (Button) scene.lookup("#btnAgregarAbono");
            Button btnEditar = (Button) scene.lookup("#btnEditarAbono");
            Button btnEliminar = (Button) scene.lookup("#btnEliminarAbono");

            Button btnFiltrar = (Button) scene.lookup("#btnFiltrar");

            btnAgregar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);

            btnFiltrar.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("AGR_ABONO")) {
                    btnAgregar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("EDI_ABONO")) {
                    btnEditar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_ABONO")) {
                    btnEliminar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_CUENTACOBRAR")) {
                    btnFiltrar.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Vales() {
        try {
            Button btnAgregar = (Button) scene.lookup("#btnAgregarVales");
            Button btnBuscar = (Button) scene.lookup("#btnBuscarVales");
            Button btnEliminar = (Button) scene.lookup("#btnEliminarVales");
            TabPane tabPane = (TabPane) scene.lookup("#tabPane");

            Tab tab = tabPane.getTabs().get(1);

            btnAgregar.setDisable(true);
            btnBuscar.setDisable(true);
            btnEliminar.setDisable(true);
            tab.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("AGR_VALE")) {
                    btnAgregar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("BUS_VALE")) {
                    btnBuscar.setDisable(false);
                } else if (permiso.getPerNombre().equalsIgnoreCase("ELI_VALE")) {
                    btnEliminar.setDisable(false);
                    tab.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Parametros() {
        try {
            Button btnEditar = (Button) scene.lookup("#btnEditar");

            btnEditar.setDisable(true);

            for (Permiso permiso : usuario.getPermisoList()) {
                if (permiso.getPerNombre().equalsIgnoreCase("EDI_PARAMETROS")) {
                    btnEditar.setDisable(false);
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void Facturacion() {

    }

    void Estadistica() {

    }
}
